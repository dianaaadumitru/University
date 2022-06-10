package Repository;

import Model.Pet;
import Model.ValidatorException;
import Validators.PetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

public class PetRepository implements Repository<Long, Pet> {
    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private PetValidator petValidator;

    @Override
    public Pet findOne(Long aLong) {
        //id must not be null ->IllegalArgumentException
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "select * from pet where id=?";
        return jdbcOperations.query(sql, resultSet -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String breed = resultSet.getString("breed");
            Long wid = resultSet.getLong("wid");

            return new Pet(id, name, breed, wid);
        }, aLong);
    }

    @Override
    public List<Pet> findAll() {
        String sql = "select * from pet";
        return jdbcOperations.query(sql, (resultSet, i) -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String breed = resultSet.getString("breed");
            Long wid = resultSet.getLong("wid");

            return new Pet(id, name, breed, wid);
        });
    }

    @Override
    public Pet save(Pet entity) throws ValidatorException {
        //entity must not be null. ->IllegalArgumentException
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        petValidator.validate(entity);
        String sql = "INSERT INTO pet (id, name, breed, wid) VALUES (?, ?, ?, ?)";
        try {
            int result = this.jdbcOperations.update(sql, entity.getId(), entity.getName(), entity.getBreed(), entity.getWizardId());
            return result == 0 ? null : entity;
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Pet delete(Long aLong) {
        //id must not be null ->IllegalArgumentException
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "delete from pet where id =? returning *";
        try {
            return jdbcOperations.queryForObject(sql, (resultSet, i) -> {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String breed = resultSet.getString("breed");
                Long wid = resultSet.getLong("wid");

                return new Pet(id, name, breed, wid);
            }, aLong);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Pet update(Pet entity) throws ValidatorException {
        //entity must not be null ->illegalArgumentException
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        //entity must be valid ->validatorException
        petValidator.validate(entity);
        String sql = "UPDATE pet SET name = ?, breed = ?, wid = ? WHERE id = ?";
        try {
            int result = this.jdbcOperations.update(sql, entity.getName(), entity.getBreed(), entity.getWizardId(), entity.getId());
            return result == 0 ? null : entity;
        }
        catch(Exception e) {
            return null;
        }
    }
}

