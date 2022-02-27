package Model;

public abstract class Entity implements EntityI{
    private int age;

    public Entity(int age) {

        this.age = age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getAge() {

        return this.age;
    }

}
