package tech.razymov.restfull.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.razymov.restfull.dto.PaymentResult;
import tech.razymov.restfull.service.DonationService;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final DonationService donationService;
    @PostMapping
    public void updatePaymentStatus(@RequestBody PaymentResult paymentResult){
        donationService.UpdatePayment(paymentResult);
    }
}
