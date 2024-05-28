package tech.razymov.restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DonationDto {
    private Long id;
    private String message;
    private Long total;
    private String senderName;
}
