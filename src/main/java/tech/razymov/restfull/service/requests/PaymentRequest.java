package tech.razymov.restfull.service.requests;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

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
    @Builder
    public static class Confirmation{
        private String type;
        private String return_url;
    }

    private Amount amount;
    private Boolean capture;
    private Confirmation confirmation;
    private String description;
}
