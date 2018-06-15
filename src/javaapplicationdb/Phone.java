package javaapplicationdb;

/**
 *
 * @author Avk
 */
public class Phone {
    private int id=-1;
    private int number=-1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Phone other = (Phone) obj;
        return this.id == other.id;
    }

    @Override
    public String toString(){
        return "Phone: id:"+id+", number:"+number;
    }
}
