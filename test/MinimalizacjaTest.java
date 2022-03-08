import java.io.FileNotFoundException;
import org.junit.Test;

public class MinimalizacjaTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNullArgs_returnIllegalArgumentException() throws Exception {
        String[] args = null;
        Minimalizacja.main(args);
    }
    
    @Test(expected = FileNotFoundException.class)
    public void shouldFileNotSpecified_returnFileNotFoundException() throws Exception {
        String[] args = {};
        Minimalizacja.main(args);
    }
    
    @Test(expected = FileNotFoundException.class)
    public void shouldInvalidFile_returnFileNotFoundException() throws Exception {
        String[] args = {"thisFileDoesntExist.txt"};
        Minimalizacja.main(args);
    }
    
}
