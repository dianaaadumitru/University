package Model;

public class Par extends Entity{
    public Par(int age) {
        super(age);
    }

    @Override
    public String toString() {
        return "Par " + this.getAge();
    }
}
