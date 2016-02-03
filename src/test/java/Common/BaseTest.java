package Common;

import org.junit.After;
import org.junit.Before;

public class BaseTest {
    @Before
    public void preconditions() {
        Teh.runDriverFullscreen("firefox");
        Teh.get("http://www.oanda.com/currency/converter/");
    }
    @After
    public void postconitions() {
        Teh.quit();
    }

}
