package tech.razymov.restfull.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import tech.razymov.restfull.service.requests.PaymentRequest;
import tech.razymov.restfull.service.response.PaymentResponse;

@FeignClient(name="yookassa-remote-service", url="https://api.yookassa.ru/v3")
public interface YookassaClient {
    @PostMapping("/payments")
    PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest,
                                  @RequestHeader("Authorization") String authorizationHeader,
                                  @RequestHeader("Idempotence-Key") String idempotenceKey);
}
