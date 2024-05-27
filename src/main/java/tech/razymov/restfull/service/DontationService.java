package tech.razymov.restfull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.razymov.restfull.dto.DonationDto;
import tech.razymov.restfull.dto.PaymentUrlDto;
import tech.razymov.restfull.entity.Donation;
import tech.razymov.restfull.exception.UserNotFoundException;
import tech.razymov.restfull.repository.DonationRepository;
import tech.razymov.restfull.repository.UserRepository;
import tech.razymov.restfull.service.requests.PaymentRequest;

@Service
@RequiredArgsConstructor
public class DontationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final YookassaService yookassaService;

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
            return new PaymentUrlDto(createdPayment.getConfirmation().getConfirmation_url());
        }
        throw new UserNotFoundException(userId);
    }
}
