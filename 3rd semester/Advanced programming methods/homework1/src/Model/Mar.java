package Model;

public class Mar extends Entity{
    public Mar(int age) {
        super(age);
    }

    @Override
    public String toString() {
        return "Mar " + this.getAge();
    }
}
