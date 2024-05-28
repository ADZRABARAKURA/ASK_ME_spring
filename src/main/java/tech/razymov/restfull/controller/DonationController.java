package tech.razymov.restfull.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.razymov.restfull.dto.DonationDto;
import tech.razymov.restfull.dto.PaymentUrlDto;
import tech.razymov.restfull.service.DonationService;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;
    @PostMapping("/{id}")
    public PaymentUrlDto sendDonation(@PathVariable Long id, @RequestBody DonationDto donationDto){
        return donationService.sendDonation(id, donationDto);
    }
}
