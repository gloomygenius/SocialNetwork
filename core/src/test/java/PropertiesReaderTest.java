import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PropertiesReaderTest {
    PropertiesReader propertiesReader;
    @Test
    public void getPropertyTest() throws Exception {
        assertThat(PropertiesReader.getProperty("src\\test\\resources\\propertiesReaderTest.properties", "id"), is("1"));
        assertThat(PropertiesReader.getProperty("src\\test\\resources\\propertiesReaderTest.properties", "name"), is("Павел"));
        assertNull(PropertiesReader.getProperty("src\\test\\resources\\propertiesReaderTest.properties", "blabla"));
    }

    @Test(expected = RuntimeException.class)
    public void getPropertyTestExceptions() {
        assertNull(PropertiesReader.getProperty("src\\test\\resources\\wrongName.properties", "blabla"));
    }

}