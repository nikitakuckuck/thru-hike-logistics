package learn.thruhike.data;

import learn.thruhike.data.mappers.TrailMapper;
import learn.thruhike.models.Trail;
import learn.thruhike.models.TrailSection;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TrailJdbcTemplateRepository implements TrailRepository {
    private final JdbcTemplate template;

    public TrailJdbcTemplateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Trail> findAll(){
        final String sql = "select trail_id, app_user_id, trail_name, trail_abbreviation from trail;";
        return template.query(sql, new TrailMapper());
    }

    @Override
    public Trail findById(int id){
        final String sql = "select trail_id, app_user_id, trail_name, trail_abbreviation from trail where trail_id = ?;";
        try {
            return template.queryForObject(sql, new TrailMapper(),id);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public Trail add(Trail trail){
        final String sql = "insert into trail (app_user_id, trail_name, trail_abbreviation) values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, trail.getAppUserId());
            preparedStatement.setString(2, trail.getTrailName());
            preparedStatement.setString(3,trail.getTrailAbbreviation());
            return preparedStatement;
        }, keyHolder);
        if(rowsAffected<=0){
            return null;
        }
        trail.setTrailId(keyHolder.getKey().intValue());
        return trail;
    }

    @Override
    public boolean update(Trail trail){
        final String sql = "update trail set trail_name = ?, trail_abbreviation = ? where trail_id = ?";
        int rowsAffected = template.update(sql, trail.getTrailName(), trail.getTrailAbbreviation(), trail.getTrailId());
        return rowsAffected>0;
    }

    @Override
    @Transactional
    public boolean deleteById(int id){
        final String trailSectionSql = "delete from trail_section where trail_id = ?;";
        template.update(trailSectionSql, id);
        final String sql = "delete from trail where trail_id = ?;";
        int rowsAffected = template.update(sql, id);
        return rowsAffected > 0;
    }



}
