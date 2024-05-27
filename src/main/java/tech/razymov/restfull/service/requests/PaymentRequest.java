package tech.razymov.restfull.service.requests;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {
    @Getter
    @Setter
    @Builder
    public static class Amount{
        private Double value;
        private String currency;
    }

    @Getter
    @Setter
    public static class Confirmation{
        private String type = "redirect";
        private String return_url = "http://localhost:3000";
    }

    private Amount amount;
    private Boolean capture;
    private Confirmation confirmation;
    private String description;
}
