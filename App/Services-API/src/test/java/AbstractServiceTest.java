import servicesAPI.serviceHandler.RequestHandler;
import servicesAPI.serviceHandler.ServiceFactory;
import servicesAPI.serviceHandler.ServiceModel;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public abstract class AbstractServiceTest {
    protected final ServiceModel serviceModel = ServiceFactory.instance();

    public AbstractServiceTest() {
    }

    // Adds default parameters to the URL
    protected abstract String defaultURL(String URL);

    // Perform service request and check its output against the expected
    protected void test(String serviceName, HashMap<String, String> data,
                        String URL) {
        HashMap<String, Object> output = serviceModel.testRequest(serviceName, data);
        assertEquals(output, makeRequest(defaultURL(URL), data));
    }

    //
    protected HashMap<String, Object> makeRequest(String URL, HashMap<String, String> parameters) {
        return RequestHandler.makeRequest(URL, parameters);
    }

    // Run test suite
    public void run() {
        try {
            runValid();
            runErroneous();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            for (StackTraceElement message : e.getStackTrace()) {
                System.err.println(message);
            }
        }
    }

    // Run tests with valid inputs
    protected abstract void runValid();

    // Run tests with invalid inputs that should give errors
    protected abstract void runErroneous();

}
