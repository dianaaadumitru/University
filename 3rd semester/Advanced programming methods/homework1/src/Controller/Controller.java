package Controller;

import Model.Entity;
import Repository.Repo;

public class Controller{
    private final Repo repo;

    public Controller(Repo repo) {
        this.repo = repo;
    }

    public Repo getRepo() {
        return repo;
    }

    public void addController(Entity e) throws Exception{
        this.repo.addRepo(e);
    }

    public void removeController(Entity e) throws Exception{
        this.repo.removeRepo(e);
    }

    public Entity[] getEntitiesOlderThanController(int age) {
        return this.repo.getEntitiesOlderThanRepo(age);
    }
}
