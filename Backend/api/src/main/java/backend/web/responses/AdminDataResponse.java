package backend.web.responses;

public class AdminDataResponse extends StandardResponse {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final String profilePicture;

    public AdminDataResponse(boolean success, String message, String firstName, String lastName, String email,
                             String phoneNumber, String password, String profilePicture, int code) {
        super(success, message, code);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getPassword() {
        return this.password;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }
}
