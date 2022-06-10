package Model;


import java.io.Serializable;

public class BaseEntity<ID extends Serializable> implements Serializable {
    private ID id;

    /**
     * Gets the id of the Base Entity
     * @return id
     * */
    public ID getId() {
        return id;
    }

    /**
     * Sets the id of the base entity
     * */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}