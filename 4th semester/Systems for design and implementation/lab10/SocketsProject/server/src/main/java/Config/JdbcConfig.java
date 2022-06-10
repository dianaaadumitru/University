package Config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    @Bean
    JdbcOperations jdbcOperations(){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    private DataSource dataSource() {
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUsername("postgres");
        dataSource.setPassword("flourishing1234");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/HarryPotter");
        dataSource.setInitialSize(2);
        return dataSource;
    }
}