package Model;

public class Pet extends BaseEntity<Long> {
    private String name;
    private String breed;
    private Long wid;

    public Pet(Long id, String name, String breed, Long wid) {
        this.setId(id);
        this.name = name;
        this.breed = breed;
        this.wid = wid;
    }

    /**
     * Gets the name of the pet
     *
     * @return spell name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    /**
     * Sets the breed of the pet
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public void setWizardId(Long wid) {
        this.wid = wid;
    }
    public Long getWizardId() {
        return wid;
    }


    @Override
    public String toString() {
        return "Pet{" +
                "id= " + this.getId() +
                ", name='" + this.name + '\'' +
                ", breed='" + this.breed + '\'' +
                ", wid=" + this.wid + '}';
    }
}
