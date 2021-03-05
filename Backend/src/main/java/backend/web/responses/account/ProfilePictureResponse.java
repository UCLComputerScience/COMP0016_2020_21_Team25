package backend.web.responses.account;

import backend.web.responses.StandardResponse;

import java.util.Map;

public class ProfilePictureResponse extends StandardResponse {
    private final Map<String, String> profilePictures;

    public ProfilePictureResponse(boolean success, String message, Map<String, String> profilePictures, int code) {
        super(success, message, code);
        this.profilePictures = profilePictures;
    }

    public Map<String, String> getProfilePictures() {
        return this.profilePictures;
    }
}
