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

import java.util.*;

import static java.lang.StringTemplate.STR;

@Service
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final YookassaService yookassaService;
    private final SimpMessagingTemplate template;
    private final PaymentRepository paymentRepository;
    private final Map<String, Queue<DonationDto>> queueMap = new HashMap<>();

    public DonationDto getAndDelete(String uniq_url){
        var url = STR."/topic/donations/\{uniq_url}";
        if(queueMap.containsKey(url))
            if (!queueMap.get(url).isEmpty()) {
               return queueMap.get(url).poll();
            }
        return null;
    }
    public DonationDto deleteAndGet(String uniq_url){
        var url = STR."/topic/donations/\{uniq_url}";
        if(queueMap.containsKey(url))
            if (!queueMap.get(url).isEmpty()) {
                queueMap.get(url).poll();
                if(!queueMap.get(url).isEmpty())
                    return queueMap.get(url).peek();
            }
        return null;
    }

    public void UpdatePayment(PaymentResult paymentResult){
        var paymentO = paymentRepository.findById(paymentResult.getObject().getId( ));
        if(paymentO.isPresent()) {
            var payment = paymentO.get();
            payment.setStatus(paymentResult.getObject().getStatus());
            payment = paymentRepository.save(payment);
            if (Objects.equals(payment.getStatus(), "succeeded")) {
                var url = STR."/topic/donations/\{payment.getDonation().getUser().getUniqUrl()}";
                final DonationDto dto = payment.getDonation().toDto();
                if (queueMap.containsKey(url) && queueMap.get(url).isEmpty()) {
                    this.queueMap.get(url).add(payment.getDonation().toDto());
                    this.template.convertAndSend(url, dto);
                } else if (queueMap.containsKey(url)) {
                    this.queueMap.get(url).add(payment.getDonation().toDto());
                }
                else{
                    this.queueMap.put(url, new ArrayDeque<>(){{push(dto);}});
                    this.template.convertAndSend(url, dto);
                }
            }
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
                    .confirmation(PaymentRequest.Confirmation.builder()
                            .return_url(STR."https://askme-donation.ru/\{userId}")
                            .type("redirect")
                            .build())
                    .amount(PaymentRequest.Amount.builder()
                            .value(donation.getTotal().doubleValue())
                            .currency("RUB")
                            .build())
                    .capture(true)
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
