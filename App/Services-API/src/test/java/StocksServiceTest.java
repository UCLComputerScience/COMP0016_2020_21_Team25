import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class StocksServiceTest extends AbstractServiceTest {
    private HashMap<String, String> inputData;
    private final String URL = "https://www.alphavantage.co/query?function={FUNCTION}&symbol={SYMBOL}&interval={INTERVAL}min&apikey=6NR4MAZEVM3PIYFT";

    protected String defaultURL(String URL) {
        String testURL = URL;
        if (URL.contains("{INTERVAL}")) {
            testURL = URL.replace("{INTERVAL}", "60");
        }
        if (URL.contains("{SYMBOL}")) {
            testURL = testURL.replace("{SYMBOL}", "IBM");
        }
        if (URL.contains("{FUNCTION}")) {
            testURL = testURL.replace("{FUNCTION}", "TIME_SERIES_INTRADAY");
        }
        return testURL;
    }

    protected void runValid() {
        defaultTest();
        intervalTest();
        symbolTest();
        functionTest();
        intervalSymbolTest();
        intervalFunctionTest();
        symbolFunctionTest();
        fullTest();
    }

    @Before
    public void setupData() {
        inputData = new HashMap<>();
    }

    @Test
    public void defaultTest() {
        test("Stocks", inputData, URL);
    }

    // TODO - Finish test cases
    @Test
    public void intervalTest() {
        test("Stocks", inputData, URL);
    }

    @Test
    public void symbolTest() {
        test("Stocks", inputData, URL);
    }

    @Test
    public void functionTest() {
        test("Stocks", inputData, URL);
    }

    @Test
    public void intervalSymbolTest() {
        test("Stocks", inputData, URL);
    }

    @Test
    public void intervalFunctionTest() {
        test("Stocks", inputData, URL);
    }

    @Test
    public void symbolFunctionTest() {
        test("Stocks", inputData, URL);
    }

    @Test
    public void fullTest() {
        test("Stocks", inputData, URL);
    }

    protected void runErroneous() {

    }
}
