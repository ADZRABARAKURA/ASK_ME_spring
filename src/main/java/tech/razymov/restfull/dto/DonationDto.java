package tech.razymov.restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.razymov.restfull.entity.Donation;

@Getter
@Setter
@AllArgsConstructor
public class DonationDto {
    private Long id;
    private String message;
    private Long total;
    private String senderName;

    public DonationDto(Donation donation){
        this.id = donation.getId();
        this.message = donation.getMessage();
        this.total = donation.getTotal();
        this.senderName = donation.getSenderName();
    }
}
