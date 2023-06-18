package lycoris62.socialLoginApp.rps.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final ChatRoomRepository repository;

    @GetMapping("/rooms")
    public String rooms(Model model) {
        List<ChatRoomDto> list = repository.findAll();
        log.info(list.toString());
        model.addAttribute("list", list);
        return "chat/rooms";
    }

    @PostMapping("/room")
    public String create(@RequestParam String name) {
        repository.create(name);
        return "redirect:/chat/rooms";
    }

    @GetMapping("/room")
    public void getRoom(String roomId, Model model){
        log.info("roomdId={}", roomId);
        model.addAttribute("room", repository.findById(roomId));
    }
}
