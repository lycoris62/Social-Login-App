package lycoris62.socialLoginApp.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SocketUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static SocketMessage getObject(final String message) throws Exception {
        return objectMapper.readValue(message, SocketMessage.class);
    }

    public static String getString(final SocketMessage message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }
}
