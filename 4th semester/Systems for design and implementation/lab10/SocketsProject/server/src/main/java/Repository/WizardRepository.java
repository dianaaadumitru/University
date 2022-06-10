package Repository;

import Model.ValidatorException;
import Model.Wizard;
import Validators.WizardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

public class WizardRepository implements Repository<Long, Wizard> {
    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private WizardValidator wizardValidator;

    @Override
    public Wizard findOne(Long aLong) {
        String sql = "select * from wizard where id=?";
        return jdbcOperations.query(sql, resultSet -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String pet = resultSet.getString("pet");

            return new Wizard(id, name, age, pet);
        }, aLong);
    }

    @Override
    public List<Wizard> findAll() {
        String sql = "select * from wizard";
        return jdbcOperations.query(sql, (resultSet, i) -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String pet = resultSet.getString("pet");

            return new Wizard(id, name, age, pet);
        });
    }

    @Override
    public Wizard update(Wizard wizard) throws ValidatorException {
        wizardValidator.validate(wizard);
        String sql = "UPDATE wizard SET name = ?, age = ?, pet = ? WHERE id = ? RETURNING *";
        return jdbcOperations.queryForObject(sql, (resultSet, i) -> {
                Long id=resultSet.getLong("id");
                String name=resultSet.getString("name");
                Integer age=resultSet.getInt("age");
                String pet=resultSet.getString("pet");
                return new Wizard(id,name,age,pet);
        }, wizard.getName(), wizard.getAge(), wizard.getPet(), wizard.getId());
    }

    @Override
    public Wizard delete(Long aLong) {
        String sql = "delete from wizard where id =? returning *";
        return jdbcOperations.queryForObject(sql, (resultSet, i) -> {
            Long id=resultSet.getLong("id");
            String name=resultSet.getString("name");
            Integer age=resultSet.getInt("age");
            String pet=resultSet.getString("pet");
            return new Wizard(id,name,age,pet);
        }, aLong);
    }

    @Override
    public Wizard save(Wizard wizard) throws ValidatorException {
        wizardValidator.validate(wizard);
        String sql = "INSERT INTO wizard (id, name, age,pet) VALUES (?, ?, ?, ?) RETURNING *;";
        return jdbcOperations.queryForObject(sql, (resultSet, i) -> {
            Long id=resultSet.getLong("id");
            String name=resultSet.getString("name");
            Integer age=resultSet.getInt("age");
            String pet=resultSet.getString("pet");
            return new Wizard(id,name,age,pet);
        }, wizard.getId(), wizard.getName(), wizard.getAge(), wizard.getPet());
    }

}
