
import java.util.Objects;


public class Drugstore implements Comparable<Drugstore> {

    private final int id;
    private final String name;
    private int dailyRequirement;

    public Drugstore(int id, String name, int dailyRequirement) {
        this.id = id;
        this.name = name;
        this.dailyRequirement = dailyRequirement;

    }

    public int getId() {
        return id;
    }
    
    public int getDailyRequirement() {
        return dailyRequirement;
    }
    
    public void setDailyRequirement(int i) {
         dailyRequirement=i;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Drugstore t) {
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
        if (obj instanceof Drugstore) {
            Drugstore otherDrugstore = (Drugstore) obj;
            return id == otherDrugstore.getId()
                    &&  name.equals(otherDrugstore.getName())
                    && dailyRequirement == otherDrugstore.getDailyRequirement();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + this.dailyRequirement;
        return hash;
    }
    
}
