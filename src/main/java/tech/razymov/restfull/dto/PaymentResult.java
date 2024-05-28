package tech.razymov.restfull.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResult {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PaymentObject{
        private String id;
        private String status;
    }

    private String type;
    private PaymentObject object;
}
