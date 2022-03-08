
import java.util.Objects;

public class Producer implements Comparable<Producer> {

    private final int id;
    private final String name;
    private int dailyProduction;

    public Producer(int id, String name, int dailyProduction) {
        this.id = id;
        this.name = name;
        this.dailyProduction = dailyProduction;

    }

    public int getId() {
        return id;
    }

    public int getDailyProduction() {
        return dailyProduction;
    }

    public void setDailyProduction(int i) {
        dailyProduction = i;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Producer t) {
        if (this.id < t.getId()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Producer) {
            Producer otherProducer = (Producer) obj;
            return id == otherProducer.getId()
                    && name.equals(otherProducer.getName())
                    && dailyProduction == otherProducer.getDailyProduction();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + this.dailyProduction;
        return hash;
    }

}
