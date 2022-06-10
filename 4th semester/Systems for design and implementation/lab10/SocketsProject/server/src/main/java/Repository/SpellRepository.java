package Repository;

import Model.Spell;
import Model.ValidatorException;
import Validators.SpellValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

public class SpellRepository implements Repository<Long, Spell> {
    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private SpellValidator spellValidator;

    @Override
    public Spell findOne(Long aLong) {
        //id must not be null ->IllegalArgumentException
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "select * from spell where id=?";
        return jdbcOperations.query(sql, resultSet -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");

            return new Spell(id, name, description);
        }, aLong);
    }

    @Override
    public List<Spell> findAll() {
        String sql = "select * from spell";
        return jdbcOperations.query(sql, (resultSet, i) -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");

            return new Spell(id, name, description);
        });
    }

    @Override
    public Spell save(Spell entity) throws ValidatorException {
        //entity must not be null. ->IllegalArgumentException
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        spellValidator.validate(entity);
        String sql = "INSERT INTO spell (id, name, description) VALUES (?, ?, ?)";
        try {
            int result = this.jdbcOperations.update(sql, entity.getId(), entity.getName(), entity.getDescription());
            return result == 0 ? null : entity;
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Spell delete(Long aLong) {
        //id must not be null ->IllegalArgumentException
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "delete from spell where id =? returning *";
        try {
            return jdbcOperations.queryForObject(sql, (resultSet, i) -> {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                return new Spell(id, name, description);
            }, aLong);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Spell update(Spell entity) throws ValidatorException {
        //entity must not be null ->illegalArgumentException
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        //entity must be valid ->validatorException
        spellValidator.validate(entity);
        String sql = "UPDATE spell SET name = ?, description = ? WHERE id = ?";
        try {
            int result = this.jdbcOperations.update(sql, entity.getName(), entity.getDescription(), entity.getId());
            return result == 0 ? null : entity;
        }
        catch(Exception e) {
            return null;
        }
    }
}
