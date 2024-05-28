package tech.razymov.restfull.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.razymov.restfull.service.requests.PaymentRequest;
import tech.razymov.restfull.service.response.PaymentResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static tech.razymov.restfull.util.KeyGenerator.generateIdempotenceKey;

@Service
@RequiredArgsConstructor
public class YookassaService {
    private final YookassaClient yookassaClient;
    @Value("${payments.shopId}")
    private Long shopId;

    @Value("${payments.secretKey}")
    private String secretKey;
    @FunctionalInterface
    interface SendRequestFunctionalInterface <T>{
        T execute();
    }
    private <T> T responseHandler(SendRequestFunctionalInterface<T> request){
        T result;
        try {
            result = request.execute();
        }
        catch (FeignException feignException){
            switch (feignException.status()){
                case 400, 404-> throw feignException;
                default -> throw new FeignException.InternalServerError(feignException.getMessage(),
                        feignException.request(),
                        feignException.responseBody().isPresent()?feignException.responseBody().get().array():null,
                        feignException.responseHeaders());
            }
        }
        return result;
    }

    public PaymentResponse createPayment(PaymentRequest paymentRequest){
        String auth = shopId + ":" + secretKey; //TODO
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        String authorizationHeader = "Basic " + encodedAuth;
        return responseHandler(()->yookassaClient.createPayment(paymentRequest, authorizationHeader, generateIdempotenceKey(32)));
    }
}
