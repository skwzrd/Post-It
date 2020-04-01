public class User {
    String name;
    String lastName;
    String initials;

    User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        this.initials = getInitials();
    }

    String getInitials() {
        String initials = "" + this.name.charAt(0) + this.lastName.charAt(0);
        return initials.toUpperCase();
    }
}
