
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VogelsMethod {

    private List<Connection> connections = new ArrayList<>();
    private List<Producer> producers = new ArrayList<>();
    private List<Drugstore> drugstores = new ArrayList<>();
    private final double[][] matrixOfVaccCost = new double[1000][1000];
    private int drugReq;
    private int producerProd;
    private final List<Double> costs = new ArrayList<>();
    private int drugstoreIndex;
    private int producerIndex;
    private final File file;
    private PrintWriter result;
    private double finalCost;

    public VogelsMethod(File file) {
        this.file = file;
    }

    public void minimalise() throws FileNotFoundException {
        result = new PrintWriter("result.txt");
        addData();
        matrixMaker();
        findMinInMatrix();
        for (int i = 0; i < costs.size(); i++) {
            finalCost += costs.get(i);

        }
        result.println();
        result.format("Opłaty całkowite = %.2f zł", finalCost);
        result.close();
    }

    private void addData() throws FileNotFoundException {
        InputConverter converter = new InputConverter(file);
        converter.split();

        connections = converter.getConnections();
        drugstores = converter.getDrugstores();
        producers = converter.getProducers();

        Collections.sort(connections);
        Collections.sort(drugstores);
        Collections.sort(producers);
    }

    private void matrixMaker() {
        int n = 0;

        Collections.sort(connections);
        Collections.sort(drugstores);
        for (int j = 0; j < producers.size(); j++) {
            for (int i = 0; i < drugstores.size(); i++) {
                matrixOfVaccCost[j][i] = connections.get(n).getVaccCost();
                n++;
            }
        }
        sortProd();
        sortDrug();
    }

    private void findMinInMatrix() {
        double maxValInCol = -1;
        int maxInColumnIndex = -1;

        for (int i = 0; i < producers.size() + 1; i++) {
            if (matrixOfVaccCost[i][drugstores.size()] > maxValInCol) {
                maxValInCol = matrixOfVaccCost[i][drugstores.size()];
                maxInColumnIndex = i;
            }
        }

        double maxValInRow = -1;
        int maxInRowIndex = -1;
        for (int i = 0; i < drugstores.size() + 1; i++) {
            if (matrixOfVaccCost[producers.size()][i] > maxValInRow) {
                maxValInRow = matrixOfVaccCost[producers.size()][i];
                maxInRowIndex = i;
            }
        }

        double minimal = 100000;
        int minimalInd = -2;
        if (maxValInCol > maxValInRow) {
            for (int i = 0; i < drugstores.size(); i++) {
                if (matrixOfVaccCost[maxInColumnIndex][i] < minimal && matrixOfVaccCost[maxInColumnIndex][i] > -1) {
                    minimal = matrixOfVaccCost[maxInColumnIndex][i];
                    minimalInd = i;
                }
            }
        } else {
            for (int i = 0; i < producers.size(); i++) {
                if (matrixOfVaccCost[i][maxInRowIndex] < minimal && matrixOfVaccCost[i][maxInRowIndex] > -1) {
                    minimal = matrixOfVaccCost[i][maxInRowIndex];
                    minimalInd = i;
                }
            }
        }

        int indexOfConnection = -1;
        int drugstoreInd;
        boolean done = false;
        for (int j = 0; j < producers.size(); j++) {
            for (int i = 0; i < drugstores.size(); i++) {
                indexOfConnection++;
                drugstoreInd = i;
                if (maxValInCol > maxValInRow) {
                    if (j == maxInColumnIndex && drugstoreInd == minimalInd) {
                        done = true;
                        break;
                    }
                } else {
                    if (i == maxInRowIndex && j == minimalInd) {
                        done = true;
                        break;
                    }
                }
            }

            if (done == true) {
                break;
            }
        }

        for (int i = 0; i < drugstores.size(); i++) {
            if (connections.get(indexOfConnection).getIdDrug() == drugstores.get(i).getId()) {
                drugReq = drugstores.get(i).getDailyRequirement();
                drugstoreIndex = i;
                break;
            }
        }

        producerIndex = 0;
        producerProd = 0;
        for (int i = 0; i < producers.size(); i++) {
            if (connections.get(indexOfConnection).getIdProd() == producers.get(i).getId()) {
                producerProd = producers.get(i).getDailyProduction();
                producerIndex = i;
                break;
            }
        }
        countCosts(indexOfConnection);

        for (int j = 0; j < producers.size() + 1; j++) {
            for (int i = 0; i < drugstores.size() + 1; i++) {
                matrixOfVaccCost[j][i] = 0;
            }
        }

        if (!areAllDrugstoresDone()) {
            matrixMaker();
            findMinInMatrix();
        }
    }

    private void countCosts(int indexOfConnection) {

        String s = "";
        double koszty = 0;
        if (drugReq < connections.get(indexOfConnection).getDailyMax()) {
            koszty = drugReq * connections.get(indexOfConnection).getVaccCost();
            costs.add(koszty);
            s = "koszt = " + Integer.toString(drugReq) + " * " + Double.toString(connections.get(indexOfConnection).getVaccCost()) + " = ";
            changeTheValues(drugReq, indexOfConnection);

        } else if (producerProd < connections.get(indexOfConnection).getDailyMax()) {
            koszty = producerProd * connections.get(indexOfConnection).getVaccCost();
            costs.add(koszty);
            s = "koszt = " + Integer.toString(producerProd) + " * " + Double.toString(connections.get(indexOfConnection).getVaccCost()) + " = ";
            changeTheValues(producerProd, indexOfConnection);

        } else if (producerProd > connections.get(indexOfConnection).getDailyMax()) {
            koszty = connections.get(indexOfConnection).getDailyMax() * connections.get(indexOfConnection).getVaccCost();
            costs.add(koszty);
            s = "koszt = " + Integer.toString(connections.get(indexOfConnection).getDailyMax()) + " * " + Double.toString(connections.get(indexOfConnection).getVaccCost()) + " = ";
            changeTheValues(connections.get(indexOfConnection).getDailyMax(), indexOfConnection);
        }
        result.println();
        result.format("%1$-30s %2$s %3$s", producers.get(producerIndex).getName(), "->", drugstores.get(drugstoreIndex).getName());
        result.format("(%s%.2f zł)", s, koszty);

        if (producers.get(producerIndex).getDailyProduction() == 0) {
            for (int i = 0; i < connections.size(); i++) {
                if (connections.get(i).getIdProd() == producers.get(producerIndex).getId()) {
                    connections.get(i).setVaccCost(-1);
                }
            }
        }

        if (!connections.isEmpty()) {
            if (connections.get(indexOfConnection).getDailyMax() == 0) {
                connections.get(indexOfConnection).setVaccCost(-1);
            }
        }

        if (drugstores.get(drugstoreIndex).getDailyRequirement() == 0) {
            for (int j = 0; j < connections.size(); j++) {
                if (connections.get(j).getIdDrug() == drugstores.get(drugstoreIndex).getId()) {
                    connections.get(j).setVaccCost(-1);
                }
            }
        }
    }

    private boolean areAllDrugstoresDone() {
        for (int i = 0; i < connections.size(); i++) {

            if (connections.get(i).getVaccCost() != 0) {
                if (connections.get(i).getVaccCost() != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void sortProd() {
        List<Double> sorted = new ArrayList<>();
        for (int j = 0; j < producers.size(); j++) {
            for (int i = 0; i < drugstores.size(); i++) {
                if (matrixOfVaccCost[j][i] > -1) {
                    sorted.add(matrixOfVaccCost[j][i]);
                }
            }
            switch (sorted.size()) {
                case 1:
                    matrixOfVaccCost[j][drugstores.size()] = 0;
                    sorted.clear();
                    break;
                case 0:
                    matrixOfVaccCost[j][drugstores.size()] = 0;
                    sorted.clear();
                    break;
                default:
                    java.util.Collections.sort(sorted);
                    double min = sorted.get(0);
                    double min2 = sorted.get(1);
                    sorted.clear();
                    matrixOfVaccCost[j][drugstores.size()] = min2 - min;
                    break;
            }
        }
    }

    private void sortDrug() {
        List<Double> sorted = new ArrayList<>();
        for (int j = 0; j < drugstores.size(); j++) {
            for (int i = 0; i < producers.size(); i++) {
                if (matrixOfVaccCost[i][j] > -1) {
                    sorted.add(matrixOfVaccCost[i][j]);
                }
            }

            switch (sorted.size()) {
                case 1:
                    matrixOfVaccCost[producers.size()][j] = sorted.get(0);
                    sorted.clear();
                    break;
                case 0:
                    matrixOfVaccCost[producers.size()][j] = 0;
                    sorted.clear();
                    break;
                default:
                    java.util.Collections.sort(sorted);
                    double min = sorted.get(0);
                    double min2 = sorted.get(1);
                    sorted.clear();
                    matrixOfVaccCost[producers.size()][j] = min2 - min;
                    break;
            }
        }
    }

    private void changeTheValues(int value, int IndexOfConnection) {
        drugstores.get(drugstoreIndex).setDailyRequirement(drugstores.get(drugstoreIndex).getDailyRequirement() - value);
        producers.get(producerIndex).setDailyProduction(producers.get(producerIndex).getDailyProduction() - value);
        connections.get(IndexOfConnection).setDailyMax(connections.get(IndexOfConnection).getDailyMax() - value);
    }

    public double getCosts() {
        return finalCost;
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
}
