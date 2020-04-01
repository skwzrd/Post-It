import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Chat chat = new Chat();
        User user = new User("Mike", "H");
        new ChatClient(chat, user);
    }
}
