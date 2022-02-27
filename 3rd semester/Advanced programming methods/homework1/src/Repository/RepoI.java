package Repository;

import Model.Entity;

public interface RepoI {
    void addRepo(Entity e) throws Exception;
    void removeRepo(Entity e) throws Exception;
    Entity[] getEntitiesOlderThanRepo(int age);
}
