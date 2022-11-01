package learn.thruhike.data;

import learn.thruhike.data.mappers.AlertCategoryMapper;
import learn.thruhike.data.mappers.SectionAlertMapper;
import learn.thruhike.models.AlertCategory;
import learn.thruhike.models.SectionAlert;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SectionAlertJdbcTemplateRepository implements SectionAlertRepository {
    private final JdbcTemplate template;

    public SectionAlertJdbcTemplateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<SectionAlert> findAll(){
        final String sql = "select a.alert_id, a.app_user_id, a.alert_category_id, a.alert_content, a.trail_section_id, " +
                "c.alert_category_name " +
                "from section_alert a " +
                "join alert_category c on a.alert_category_id = c.alert_category_id " +
                "order by a.alert_category_id ;";
        return template.query(sql, new SectionAlertMapper());
    }

    @Override
    public List<SectionAlert> findByTrailSectionId(int id){
        final String sql = "select a.alert_id, a.app_user_id, a.alert_category_id, a.alert_content, a.trail_section_id, " +
                "c.alert_category_name " +
                "from section_alert a " +
                "join alert_category c on a.alert_category_id = c.alert_category_id " +
                "where a.trail_section_id = ?;";
        return template.query(sql, new SectionAlertMapper(),id);
    }

    @Override
    public SectionAlert findById(int id){
        final String sql = "select a.alert_id, a.app_user_id, a.alert_category_id, a.alert_content, a.trail_section_id, " +
                "c.alert_category_name " +
                "from section_alert a " +
                "join alert_category c on a.alert_category_id = c.alert_category_id " +
                "where a.alert_id = ?;";
        try {
            return template.queryForObject(sql, new SectionAlertMapper(),id);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public SectionAlert add(SectionAlert sectionAlert){
        final String sql = "insert into section_alert " +
                "(app_user_id, alert_content, trail_section_id, alert_category_id) " +
                "values (?,?,?,?); ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,sectionAlert.getAppUserId());
            preparedStatement.setString(2, sectionAlert.getAlertContent());
            preparedStatement.setInt(3,sectionAlert.getTrailSectionId());
            preparedStatement.setInt(4,sectionAlert.getAlertCategoryId());
            return preparedStatement;
        },keyHolder);
        if(rowsAffected<=0){
            return null;
        }
        setCategory(sectionAlert);
        sectionAlert.setAlertId(keyHolder.getKey().intValue());
        return sectionAlert;
    }
    @Override
    public boolean deleteById(int id){
        final String sql = "delete from section_alert where alert_id = ?;";
        int rowsAffected = template.update(sql, id);
        return rowsAffected > 0;
    }

    //add alert category to the section alert
    private void setCategory(SectionAlert alert){
        final String sql = "select alert_category_id, alert_category_name from alert_category where alert_category_id = ?;";
        AlertCategory category = template.queryForObject(sql,new AlertCategoryMapper(), alert.getAlertCategoryId());
        alert.setAlertCategory(category);
    }

}
