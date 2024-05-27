package tech.razymov.restfull.service;

import feign.FeignException;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.razymov.restfull.service.requests.PaymentRequest;
import tech.razymov.restfull.service.response.PaymentResponse;

import java.security.SecureRandom;
import java.util.Base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
@Service
@RequiredArgsConstructor
public class YookassaService {
    private final YookassaClient yookassaClient;
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

    private String generateIdempotenceKey(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom RANDOM = new SecureRandom();
        StringBuilder key = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            key.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return key.toString();
    }

    public PaymentResponse createPayment(PaymentRequest paymentRequest){
        String auth = 388229 + ":" + "test_FD8BkPl6DBHCaHLbwK86RlQYAKcMislMSYIjV48g9Vo"; //TODO
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        String authorizationHeader = "Basic " + encodedAuth;
        return responseHandler(()->yookassaClient.createPayment(paymentRequest, authorizationHeader, generateIdempotenceKey(32)));
    }
}
