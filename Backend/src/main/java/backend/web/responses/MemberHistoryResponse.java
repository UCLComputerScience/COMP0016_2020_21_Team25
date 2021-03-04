package backend.web.responses;

import java.util.ArrayList;
import java.util.Map;

public class MemberHistoryResponse extends StandardResponse {
    private final ArrayList<Map<String, String>> history;

    public MemberHistoryResponse(boolean success, String message, ArrayList<Map<String, String>> history, int code) {
        super(success, message, code);
        this.history = history;
    }

    public ArrayList<Map<String, String>> getHistory() {
        return this.history;
    }

}
