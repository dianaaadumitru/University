package Model;

public class Cires extends Entity{
    public Cires(int age) {
        super(age);
    }

    @Override
    public String toString() {
        return "Cires " + this.getAge();
    }
}
