package lycoris62.socialLoginApp.rps.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/enter")
    public void enter(ChatMessageDto message) {
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        log.info("입장={}, msg={}", message.getWriter(), message.getMessage());
        log.info("전송url={}", "/sub/chat/room/" + message.getRoomId());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message) {
        log.info("전송={}, msg={}", message.getWriter(), message.getMessage());
        log.info("전송url={}", "/sub/chat/room/" + message.getRoomId());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
