package training.gasmonitoring;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;


public class MainTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void saysHello(){

        String [] args = { "one", "two", "three" };

        Main.main(args);
        assertEquals("hello world", systemOutRule.getLog());

    }

}
