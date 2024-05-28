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
    public static class Confirmation{
        private String type = "redirect";
        private String return_url = "https://askme-donation.ru";
    }

    private Amount amount;
    private Boolean capture;
    private Confirmation confirmation;
    private String description;
}
