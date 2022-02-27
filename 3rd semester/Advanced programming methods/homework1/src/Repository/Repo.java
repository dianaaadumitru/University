package Repository;

import Model.Entity;

public class Repo implements RepoI{
    private Entity[] elements;
    private int currentIndex, size;

    public Repo() {
        this.currentIndex = 0;
        this.elements = new Entity[100];
        this.size = 100;
    }

    public Entity[] getElements() {

        return elements;
    }

    @Override
    public void addRepo(Entity e) throws Exception{
        if (this.currentIndex >= size)
            throw new Exception("array is full");
        if (e.getAge() < 0)
            throw new Exception("Age cannot be negative!");
        elements[currentIndex++] = e;
    }

    @Override
    public void removeRepo(Entity e) throws Exception{
        boolean found = false;
        for (int i = 0; i < currentIndex; i++){
            if (elements[i].getAge() == e.getAge() && elements[i].getClass() == e.getClass()){
                found = true;
                if (i == currentIndex){
                    elements[i] = null;
                } else {
                   elements[i] = elements[currentIndex - 1];
                }
                currentIndex--;
            }
        }
        if (!found) {
            throw new Exception("Fruit not found");
        }
    }

    @Override
    public Entity[] getEntitiesOlderThanRepo(int age) {
       int  i = 0, resIndex = 0;
       Entity[] res = new Entity[this.elements.length];
       while (i < this.currentIndex){
           if (elements[i].getAge() > age){
               res[resIndex++] = elements[i];
           }
           i++;
       }
        return res;
    }
}
