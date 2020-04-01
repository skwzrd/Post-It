import java.text.MessageFormat;
import java.util.ArrayList;

public class Chat {
    public ArrayList<Message> messages;

    Chat() {
        messages = new ArrayList<>();
    }

    public String getChatMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message message : messages) {
            sb.append(MessageFormat.format("{0}\n{1}: {2}\n\n", message.date, message.user.initials, message.message));
        }
        return sb.toString();
    }

    public void addMessage(User user, String m) {
        messages.add(new Message(user, m));
    }
}
