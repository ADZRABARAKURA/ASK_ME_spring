package tech.razymov.restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentUrlDto {
    private String redirectUrl;
}
