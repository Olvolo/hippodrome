import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.*;


public class MainTest {
    @Disabled("Disabled for automatic test runs")
    @Test
       @Timeout(value = 22, unit = SECONDS)
        public void testMain_DoesNotExceed22Seconds() throws Exception {
        Main.main(null);
    }
}