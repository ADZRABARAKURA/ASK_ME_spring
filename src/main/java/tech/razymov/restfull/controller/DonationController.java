package tech.razymov.restfull.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.razymov.restfull.dto.DonationDto;
import tech.razymov.restfull.dto.PaymentUrlDto;
import tech.razymov.restfull.entity.Donation;
import tech.razymov.restfull.service.DontationService;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {
    private final DontationService dontationService;
    @PostMapping("/{id}")
    public PaymentUrlDto sendDonation(@PathVariable Long id, @RequestBody DonationDto donationDto){
        return dontationService.sendDonation(id, donationDto);
    }
}
