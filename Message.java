import java.util.Date;

public class Message {
    User user;
    String message;
    Date date;

    Message(User user, String message) {
        this.user = user;
        this.message = message;
        this.date = new Date();
    }
}
