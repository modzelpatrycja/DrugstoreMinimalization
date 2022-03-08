
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class VogelsMethodTest {

    @Test
    public void should_return_CorrectValue() throws Exception {

        File file = new File("test/filesToTest/data16.txt");

        VogelsMethod vog = new VogelsMethod(file);
        vog.minimalise();
        double expectedCosts = 82425.00;
        assertEquals(expectedCosts, vog.getCosts(), 2);

    }

    @Test
    public void should_allDrugRequirement_beZero() throws Exception {

        File file = new File("test/filesToTest/data16.txt");

        VogelsMethod vog = new VogelsMethod(file);
        vog.minimalise();
        boolean allRequirementIsZero = true;

        for (int i = 0; i < vog.getDrugstores().size(); i++) {
            if (vog.getDrugstores().get(i).getDailyRequirement() != 0) {
               allRequirementIsZero = false;
            }
        }
        assertEquals(true, allRequirementIsZero);

    }

    @Test
    public void should_allDailyProduction_beGreaterThanOrEqualToZero() throws Exception {

        File file = new File("test/filesToTest/data16.txt");

        VogelsMethod vog = new VogelsMethod(file);
        vog.minimalise();
        boolean GreaterThanOrEqualToZero = true;

        for (int i = 0; i < vog.getProducers().size(); i++) {
            if (vog.getProducers().get(i).getDailyProduction() < 0) {
                GreaterThanOrEqualToZero = false;
            }
        }
        assertEquals(true, GreaterThanOrEqualToZero);

    }
    
    @Test
    public void should_allDailyMax_beGreaterThanOrEqualToZero() throws Exception {

        File file = new File("test/filesToTest/data16.txt");

        VogelsMethod vog = new VogelsMethod(file);
        vog.minimalise();
        boolean GreaterThanOrEqualToZero = true;

        for (int i = 0; i < vog.getConnections().size(); i++) {
            if (vog.getConnections().get(i).getDailyMax() < 0) {
                GreaterThanOrEqualToZero = false;
            }
        }
        assertEquals(true, GreaterThanOrEqualToZero);

    }
    
}
