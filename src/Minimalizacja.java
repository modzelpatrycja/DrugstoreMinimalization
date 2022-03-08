
import java.io.File;
import java.io.FileNotFoundException;

public class Minimalizacja {

    public static void main(String[] args) throws FileNotFoundException {

        File file = null;
        if (args == null) {
            throw new IllegalArgumentException("File cannot be null");
        } else if (args.length < 1) {
            throw new FileNotFoundException("File was not specified");
        } else {

            file = new File(args[0]);

            if (!file.canRead()) {
                throw new FileNotFoundException("Invalid file!");
            }
        }

        VogelsMethod vog = new VogelsMethod(file);
        vog.minimalise();

    }
    
}
