import java.util.Queue;

public class ResponseListener implements Runnable {
    private final Queue<String> appQueue;

    public ResponseListener(Queue<String> appQueue) {
        this.appQueue = appQueue;
    }

    public synchronized void run() {
        while (true) {
            if (!appQueue.isEmpty()) {
                String response = appQueue.poll();
                System.out.println("Response from API: ");
                System.out.println(response);
                System.out.println();
            }
        }
    }
}
