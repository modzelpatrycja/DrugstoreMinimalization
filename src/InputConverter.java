
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputConverter {

    private final List<Producer> producers = new ArrayList<>();
    private final List<Drugstore> drugstores = new ArrayList<>();
    private final List<Connection> connections = new ArrayList<>();
    private int production = 0;
    private int requirement = 0;
    private final File data;
    private int lineNumber = 0;

    public InputConverter(File file) {
        data = file;
    }

    public void split() throws FileNotFoundException {
        try (Scanner reader = new Scanner(data)) {
            String s;
            if (reader.hasNext() == false) {
                throw new IllegalArgumentException("File is empty!");
            }
            reader.nextLine();
            lineNumber++;
            String[] lines;
            while (reader.hasNextLine()) {
                s = reader.nextLine();
                lineNumber++;
                while (!s.contains("#")) {

                    lines = s.split(" \\| ");
                    splitProducers(lines);
                    s = reader.nextLine();
                    lineNumber++;
                }
                s = reader.nextLine();
                lineNumber++;
                while (!s.contains("#")) {

                    lines = s.split(" \\| ");
                    splitDrugstore(lines);
                    s = reader.nextLine();
                    lineNumber++;
                }

                s = reader.nextLine();
                lineNumber++;
                while (reader.hasNextLine()) {
                    lines = s.split(" \\| ");
                    splitConnection(lines);
                    s = reader.nextLine();
                    lineNumber++;
                }
                lines = s.split(" \\| ");
                splitConnection(lines);
            }

        }
        if (production == 0) {
            throw new IllegalArgumentException("None producer was given");
        }

        if (requirement == 0) {
            throw new IllegalArgumentException("None drugstore was given");
        }

        if (producers.size() > 1000) {
            throw new IllegalArgumentException("To many producers!");
        }
        if (drugstores.size() > 1000) {
            throw new IllegalArgumentException("To many drugstores!");
        }

        if (requirement > production) {
            throw new IllegalArgumentException("Requirement is greater than production!");
        }
        if (!(connections.size() == producers.size() * drugstores.size())) {
            throw new IllegalArgumentException("Inappropriate number of connections between drugstores and producers");
        }

    }

    private void splitDrugstore(String[] line) {

        int id = -1;
        try {
            id = Integer.parseInt(line[0]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot put ID of different type than Integer " + "in line " + lineNumber);
        }
        checkIfEqualOrGreaterThanZero(id);

        for (int i = 0; i < drugstores.size(); i++) {
            if (id == drugstores.get(i).getId()) {
                throw new IllegalArgumentException("Cannot put drugstore with the same ID twice " + "in line " + lineNumber);
            }
        }

        String name = line[1];
        int dailyRequirement;
        try {
            dailyRequirement = Integer.parseInt(line[2]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot put dailyRequirement of different type than Integer " + "in line " + lineNumber);
        }
        checkIfEqualOrGreaterThanZero(dailyRequirement);

        drugstores.add(new Drugstore(id, name, dailyRequirement));
        requirement += dailyRequirement;
    }

    private void splitProducers(String[] line) {

        int id = -1;
        try {
            id = Integer.parseInt(line[0]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot put ID of different type than Integer " + "in line " + lineNumber);
        }
        checkIfEqualOrGreaterThanZero(id);

        for (int i = 0; i < producers.size(); i++) {
            if (id == producers.get(i).getId()) {
                throw new IllegalArgumentException("Cannot put producer with the same ID twice " + "in line " + lineNumber);

            }
        }

        String name = line[1];

        int dailyProduction;
        try {
            dailyProduction = Integer.parseInt(line[2]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot put dailyProduction of different type than Integer " + "in line " + lineNumber);
        }
        checkIfEqualOrGreaterThanZero(dailyProduction);

        producers.add(new Producer(id, name, dailyProduction));

        production += dailyProduction;
    }

    private void splitConnection(String[] line) {
        int idProd;
        int idDrug;
        int dailyMax;
        double vaccCost;
        try {
            idProd = Integer.parseInt(line[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot put ID of different type than Integer " + "in line " + lineNumber);
        }

        try {
            idDrug = Integer.parseInt(line[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot put ID of different type than Integer " + "in line " + lineNumber);
        }

        try {
            dailyMax = Integer.parseInt(line[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot put dailyMax of different type than Integer " + "in line " + lineNumber);
        }

        try {
            vaccCost = Double.parseDouble(line[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot put vaccCost of different type than Double " + "in line " + lineNumber);
        }

        for (int i = 0; i < connections.size(); i++) {
            if (idDrug == connections.get(i).getIdDrug() && idProd == connections.get(i).getIdProd()) {
                throw new IllegalArgumentException("Cannot put the same connections second time " + "in line " + lineNumber);

            }
        }

        connections.add(new Connection(idProd, idDrug, dailyMax, vaccCost));

        boolean isCorrectConnection = false;
        for (int i = 0; i < producers.size(); i++) {
            for (int j = 0; j < drugstores.size(); j++) {
                if (producers.get(i).getId() == idProd && drugstores.get(j).getId() == idDrug) {
                    isCorrectConnection = true;
                    break;
                }
            }
        }

        if (!isCorrectConnection) {
            throw new IllegalArgumentException("Incorrect indexing of connections! " + "in line " + lineNumber);
        }
    }

    private void checkIfEqualOrGreaterThanZero(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be less than zero " + "in line " + lineNumber);
        }

    }

    public List<Producer> getProducers() {
        return producers;
    }

    public List<Drugstore> getDrugstores() {
        return drugstores;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public int getRequirement() {
        return requirement;
    }

    public int getProduction() {
        return production;
    }

}
