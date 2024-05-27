package tech.razymov.restfull.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.razymov.restfull.service.requests.PaymentRequest;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConfirmationResponse{
        private String type;
        private String confirmation_url;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Recipient{
        private String account_id;
        private String gateway_id;
    }

    private String id;
    private String status;
    private PaymentRequest.Amount amount;
    private ConfirmationResponse confirmation;
    private String created_at;
    private String description;
    private Recipient recipient;
    private Boolean refundable;
    private Boolean test;
}
