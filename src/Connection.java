
public class Connection implements Comparable<Connection> {

    private final int idProd;
    private final int idDrug;
    private int dailyMax;
    private double vaccCost;

    public Connection(int idProd, int idDrug, int dailyMax, double vaccCost) {
        this.idProd = idProd;
        this.idDrug = idDrug;
        this.dailyMax = dailyMax;
        this.vaccCost = vaccCost;
    }

    public int getIdProd() {
        return idProd;
    }

    public int getIdDrug() {
        return idDrug;
    }

    public int getDailyMax() {
        return dailyMax;
    }

    public double getVaccCost() {
        return vaccCost;
    }

    public void setDailyMax(int i) {
        dailyMax = i;
    }

    public void setVaccCost(double d) {
        vaccCost = d;
    }

    @Override
    public int compareTo(Connection t) {
        if (this.idProd < t.getIdProd()) {
            return -1;
        } else if (this.idProd > t.getIdProd()) {
            return 1;
        } else {
            if (this.idDrug < t.getIdDrug()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Connection) {
            Connection otherConnection = (Connection) obj;
            return idDrug == otherConnection.getIdDrug()
                    && idProd == otherConnection.getIdProd()
                    && dailyMax == otherConnection.getDailyMax()
                    && vaccCost == otherConnection.getVaccCost();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idProd;
        hash = 59 * hash + this.idDrug;
        hash = 59 * hash + this.dailyMax;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.vaccCost) ^ (Double.doubleToLongBits(this.vaccCost) >>> 32));
        return hash;
    }
}
