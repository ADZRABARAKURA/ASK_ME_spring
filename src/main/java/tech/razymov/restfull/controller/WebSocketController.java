package tech.razymov.restfull.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import tech.razymov.restfull.dto.DonationDto;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/donations/{uniq_url}")
    public void send(@Payload DonationDto donationDto, @DestinationVariable String uniq_url) {
        messagingTemplate.convertAndSend("/topic/donations/" + uniq_url, donationDto);
    }
}