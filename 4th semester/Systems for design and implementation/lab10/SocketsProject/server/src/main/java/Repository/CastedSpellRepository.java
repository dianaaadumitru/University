package Repository;

import Model.CastedSpell;
import Model.Pair;
import Model.ValidatorException;
import Validators.CastedSpellValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

public class CastedSpellRepository implements Repository<Pair<Long, Long>, CastedSpell> {
    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private CastedSpellValidator castedSpellValidator;

    @Override
    public CastedSpell findOne(Pair<Long, Long> val) {
        if(val.getLeftPart()==null){
            throw new IllegalArgumentException("wid must not be null");
        }
        if(val.getRightPart()==null){
            throw new IllegalArgumentException("sid must not be null");
        }
        String sql = "SELECT * FROM casted_spell WHERE wid =? AND sid =?";
        return jdbcOperations.query(sql, resultSet -> {
            Long wid = resultSet.getLong("wid");
            Long sid = resultSet.getLong("sid");
            String description = resultSet.getString("description");

            return new CastedSpell(wid, sid, description);
        }, val.getLeftPart(), val.getRightPart());
    }

    @Override
    public List<CastedSpell> findAll() {
        String sql = "select * from casted_spell";
        return jdbcOperations.query(sql, (resultSet, i) -> {
            Long id = resultSet.getLong("wid");
            Long id2 = resultSet.getLong("sid");
            String description = resultSet.getString("description");

            return new CastedSpell(id, id2, description);
        });
    }

    @Override
    public CastedSpell save(CastedSpell entity) throws ValidatorException {

        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        castedSpellValidator.validate(entity);
        String sql = "INSERT INTO casted_spell (wid, sid, description) VALUES (?, ?, ?)";
        try {
            int result = this.jdbcOperations.update(sql, entity.getId().getLeftPart(), entity.getId().getRightPart(), entity.getDetails());
            return result == 0 ? null : entity;
        }
        catch(Exception e){
            return null;
        }
        /*
        String sql = "INSERT INTO casted_spell (wid, sid, description) VALUES (?, ?, ?) RETURNING *";
        return jdbcOperations.queryForObject(sql, (resultSet, i) -> {
            Long wid = resultSet.getLong("wid");
            Long sid = resultSet.getLong("sid");
            String description = resultSet.getString("description");

            return new CastedSpell(wid, sid, description);
        }, entity.getId().getLeftPart(), entity.getId().getRightPart(), entity.getDetails());
        */
    }

    @Override
    public CastedSpell delete(Pair<Long, Long> longLongPair) {
        if (longLongPair.getLeftPart() == null) {
            throw new IllegalArgumentException("wid must not be null");
        }
        if(longLongPair.getRightPart()==null){
            throw new IllegalArgumentException("sid must not be null");
        }
        String sql = "DELETE FROM casted_spell WHERE wid = ? AND sid = ? returning *";
        try {
            return jdbcOperations.queryForObject(sql, (resultSet, i) -> {
                Long wid = resultSet.getLong("wid");
                Long sid = resultSet.getLong("sid");
                String description = resultSet.getString("description");

                return new CastedSpell(wid, sid, description);
            }, longLongPair.getLeftPart(), longLongPair.getRightPart());
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public CastedSpell update(CastedSpell entity) throws ValidatorException {
        //entity must not be null ->illegalArgumentException
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        //entity must be valid ->validatorException
        castedSpellValidator.validate(entity);
        String sql = "UPDATE casted_spell SET description = ? WHERE wid = ? AND sid = ?";
        try {
            int result = this.jdbcOperations.update(sql, entity.getDetails(), entity.getId().getLeftPart(), entity.getId().getRightPart());
            return result == 0 ? null : entity;
        }
        catch(Exception e) {
            return null;
        }
        /*
        String sql = "UPDATE casted_spell SET description = ? WHERE wid = ? AND sid = ? RETURNING *";
        return jdbcOperations.queryForObject(sql, (resultSet, i) -> {
            Long wid = resultSet.getLong("wid");
            Long sid = resultSet.getLong("sid");
            String description = resultSet.getString("description");

            return new CastedSpell(wid, sid, description);
        }, entity.getDetails(), entity.getId().getLeftPart(), entity.getId().getRightPart());
         */

    }

}
