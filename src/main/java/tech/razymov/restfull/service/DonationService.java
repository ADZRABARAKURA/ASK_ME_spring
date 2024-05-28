package tech.razymov.restfull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tech.razymov.restfull.dto.DonationDto;
import tech.razymov.restfull.dto.PaymentResult;
import tech.razymov.restfull.dto.PaymentUrlDto;
import tech.razymov.restfull.entity.Donation;
import tech.razymov.restfull.entity.Payment;
import tech.razymov.restfull.exception.UserNotFoundException;
import tech.razymov.restfull.repository.DonationRepository;
import tech.razymov.restfull.repository.PaymentRepository;
import tech.razymov.restfull.repository.UserRepository;
import tech.razymov.restfull.service.requests.PaymentRequest;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final YookassaService yookassaService;
    private final SimpMessagingTemplate template;
    private final PaymentRepository paymentRepository;

    public void UpdatePayment(PaymentResult paymentResult){
        var paymentO = paymentRepository.findById(paymentResult.getObject().getId( ));
        if(paymentO.isPresent()) {
            var payment = paymentO.get();
            payment.setStatus(paymentResult.getObject().getStatus());
            payment = paymentRepository.save(payment);
            if(Objects.equals(payment.getStatus(), "succeeded"))
                this.template.convertAndSend("/topic/donations/"+payment.getDonation().getUser().getUniqUrl(), payment.getDonation().toDto());
        }
        else
            throw new IllegalStateException("Payment with such id not found!");
    }

    public PaymentUrlDto sendDonation(Long userId, DonationDto donationDto){
        var user = userRepository.findById(userId);
        if(user.isPresent()) {
            var donation = Donation.builder()
                    .total(donationDto.getTotal())
                    .message(donationDto.getMessage())
                    .senderName(donationDto.getSenderName())
                    .user(user.get())
                    .id(null)
                    .build();

            var payment = PaymentRequest.builder()
                    .amount(PaymentRequest.Amount.builder()
                            .value(donation.getTotal().doubleValue())
                            .currency("RUB")
                            .build())
                    .capture(true)
                    .confirmation(new PaymentRequest.Confirmation())
                    .description("Заказ на оплату \"Доната\" для стримера " + user.get().getName())
                    .build();
            var createdPayment = yookassaService.createPayment(payment);
            var donationSaved = donationRepository.save(donation);
            var savedPayment = paymentRepository.save(Payment.builder()
                            .id(createdPayment.getId())
                            .status(createdPayment.getStatus())
                            .donation(donationSaved)
                            .value(createdPayment.getAmount().getValue())
                    .build());
            return new PaymentUrlDto(createdPayment.getConfirmation().getConfirmation_url());
        }
        throw new UserNotFoundException(userId);
    }
}
