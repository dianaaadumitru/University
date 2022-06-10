package Model;

/**
 * A spell has a name and description
 */
public class Spell extends BaseEntity<Long> {
    private String name;
    private String description;

    public Spell(Long id, String name, String description) {
        this.setId(id);
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the name of the spell
     *
     * @return spell name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the spell
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the spell
     *
     * @return spell description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the spell
     */
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Spell{" +
                "id= " + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
