package backend.web.responses;

import java.util.Map;

public class MemberDataResponse extends StandardResponse {
    private final Map<String, Map<String, String>> members;

    public MemberDataResponse(boolean success, String message, Map<String, Map<String, String>> members, int code) {
        super(success, message, code);
        this.members = members;
    }

    public Map<String, Map<String, String>> getMembers() {
        return this.members;
    }

}
