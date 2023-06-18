package lycoris62.socialLoginApp.rps.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    @MessageMapping("/chat/enter/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")
    public ChatMessageDto enter(@DestinationVariable String roomId, ChatMessageDto message) {
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        return message;
    }

    @MessageMapping("/chat/message/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")
    public ChatMessageDto message(@DestinationVariable String roomId, ChatMessageDto message) {
        return message;
    }
}
