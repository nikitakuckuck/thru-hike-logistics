package learn.thruhike.data;

import learn.thruhike.data.mappers.SectionAlertMapper;
import learn.thruhike.models.SectionAlert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SectionAlertJdbcTemplateRepository implements SectionAlertRepository {
    private final JdbcTemplate template;

    public SectionAlertJdbcTemplateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<SectionAlert> findAll(){
        final String sql = "select a.alert_id, a.app_user_id, a.alert_category_id, a.alert_content, a.trail_section_id, a.future_sections, " +
                "s.app_user_id, s.trail_id, s.section_start, s.section_end, s.latitude, s.longitude, s.section_length, s.section_days, s.upcoming, s.active , " +
                "t.trail_name, t.trail_abbreviation," +
                "c.alert_category_name " +
                "from section_alert a " +
                "join trail_section s on a.trail_section_id = s.trail_section_id " +
                "join trail t on s.trail_id = t.trail_id " +
                "join alert_category c on a.alert_category_id = c.alert_category_id " +
                "order by a.alert_category_id ;";
        return template.query(sql, new SectionAlertMapper());
    }

}
