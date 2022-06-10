package Model;

public class Wizard extends BaseEntity<Long> {
    private String name;
    private Integer age;
    private String pet;

    public Wizard(Long id, String name, Integer age, String pet) {
        this.setId(id);
        this.name = name;
        this.age = age;
        this.pet = pet;
    }

    /**
     * Gets the name of the wizard
     * @return wizard name
     * */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the wizard
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the wizard
     * @return wizard age
     * */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the wizard
     * */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets the wizard's pet
     * @return wizard pet
     * */
    public String getPet() {
        return pet;
    }

    /**
     * Sets the wizard's pet
     * */
    public void setPet(String pet) {
        this.pet = pet;
    }

    /**
     * Returns the head of the house
     * */
    public String getHeadOfHouse(){
        return "Dumbledore";
    }

    @Override
    public String toString() {
        return "Wizard{" +
                "id= " + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", age=" + this.getAge() +
                ", pet='" + this.getPet() + '\'' +
                '}';
    }
}