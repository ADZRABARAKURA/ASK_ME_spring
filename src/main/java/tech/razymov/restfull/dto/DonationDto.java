package tech.razymov.restfull.dto;

import lombok.Getter;

@Getter
public class DonationDto {
    private String message;
    private Long total;
    private String senderName;
}
