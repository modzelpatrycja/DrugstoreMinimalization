
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class InputCheckerTest {


    @Test(expected = IllegalArgumentException.class)
    public void split_emptyFile_should_returnIllegalArgumentException() throws Exception {
        PrintWriter writer = new PrintWriter("file.txt");
        writer.close();
        File file = new File("file.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }


    @Test(expected = NumberFormatException.class)
    public void should_doubleIndex_returnNumberFormatException() throws Exception {

        File file = new File("test/filesToTest/data3.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_replicatedIndex_returnIllegalArgumentException() throws Exception {

        File file = new File("test/filesToTest/data6.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }

    @Test(expected = Exception.class)
    public void should_doubleDailyMax_returnIllegalArgumentException() throws Exception {

        File file = new File("test/filesToTest/data8.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_stringVaccCost_returnIllegalArgumentException() throws Exception {

        File file = new File("test/filesToTest/data9.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_NumberLessThanZero_returnIllegalArgumentException() throws Exception {

        File file = new File("test/filesToTest/data19.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_RequirementGreaterThanProduction_returnIllegalArgumentException() throws Exception {

        File file = new File("test/filesToTest/data21.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_inappropriateNumberOfConnections_returnIllegalArgumentException() throws Exception {

        File file = new File("test/filesToTest/data21.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_doubleConnection_returnIllegalArgumentException() throws Exception {

        File file = new File("test/filesToTest/data12.txt");

        InputConverter converter = new InputConverter(file);
        converter.split();

    }
    
    

    @Test
    public void should_connectionsBeProperObjects() throws FileNotFoundException {

        File file = new File("test/filesToTest/data16.txt");

        InputConverter converter = new InputConverter(file);
        List<Connection> connections = new ArrayList<>();
        converter.split();
        connections = converter.getConnections();
        boolean isEqual=true;
        List<Connection> connectionsExpected = new ArrayList<>();

        
        
        connectionsExpected.add(new Connection(0, 0, 800, 70.5));
        connectionsExpected.add(new Connection(0, 1, 600, 70));
        connectionsExpected.add(new Connection(1, 0, 900, 100));
        connectionsExpected.add(new Connection(1, 1, 600, 80));
        
        for(int i=0; i<connections.size(); i++)
            if(!connectionsExpected.get(i).equals(connections.get(i)))
                isEqual=false;

        Assert.assertEquals(true, isEqual);

    }
    
    @Test
    public void should_drugstoresBeProperObjects() throws FileNotFoundException {

        File file = new File("test/filesToTest/data16.txt");

        InputConverter converter = new InputConverter(file);
        List<Drugstore> drugstores = new ArrayList<>();
        converter.split();
        drugstores = converter.getDrugstores();
        boolean isEqual=true;
        List<Drugstore> drugstoresExpected = new ArrayList<>();

        drugstoresExpected.add(new Drugstore(0, "CentMedEko Centrala", 450));
        drugstoresExpected.add(new Drugstore(1, "CentMedEko 24h", 690));
        
        
        for(int i=0; i<drugstores.size(); i++)
            if(!drugstoresExpected.get(i).equals(drugstores.get(i)))
                isEqual=false;

        Assert.assertEquals(true, isEqual);

    }
    
    @Test
    public void should_producersBeProperObjects() throws FileNotFoundException {

        File file = new File("test/filesToTest/data16.txt");

        InputConverter converter = new InputConverter(file);
        List<Producer> producers = new ArrayList<>();
        converter.split();
        producers = converter.getProducers();
        boolean isEqual=true;
        List<Producer> producersExpected = new ArrayList<>();

        producersExpected.add(new Producer(0, "BioTech 2.0", 900));
        producersExpected.add(new Producer(1, "Eko Polska 2020", 1300));

        
         for(int i=0; i<producers.size(); i++)
            if(!producersExpected.get(i).equals(producers.get(i)))
                isEqual=false;

        Assert.assertEquals(true, isEqual);
        
        
        Assert.assertEquals(producersExpected.get(1), producers.get(1));

    }
    

}
