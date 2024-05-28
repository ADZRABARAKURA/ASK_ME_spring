package tech.razymov.restfull.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import tech.razymov.restfull.dto.DonationDto;
import tech.razymov.restfull.dto.WebSocetSubscribeId;
import tech.razymov.restfull.service.DonationService;

@Controller
@RequiredArgsConstructor
public class WebSocketConroller {
    private final DonationService donationService;
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/subscribe")
    public void subscribeToDonations(WebSocetSubscribeId userUrl) {
        messagingTemplate.convertAndSend(STR."/topic/donations/\{userUrl.getUser_url()}", donationService.getAndDelete(userUrl.getUser_url()));
    }
}
