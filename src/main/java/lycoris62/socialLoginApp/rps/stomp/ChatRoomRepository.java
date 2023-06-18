package lycoris62.socialLoginApp.rps.stomp;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoomDto> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAll() {
        ArrayList<ChatRoomDto> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    public ChatRoomDto findById(String id) {
        return chatRooms.get(id);
    }

    public ChatRoomDto create(String name) {
        ChatRoomDto room = ChatRoomDto.create(name);
        chatRooms.put(room.getRoomId(), room);

        return room;
    }
}
