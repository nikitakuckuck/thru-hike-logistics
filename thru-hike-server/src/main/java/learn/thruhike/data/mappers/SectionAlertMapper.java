package learn.thruhike.data.mappers;

import learn.thruhike.models.SectionAlert;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionAlertMapper implements RowMapper<SectionAlert> {
    @Override
    public SectionAlert mapRow(ResultSet rs, int rowNum) throws SQLException {
        SectionAlert sectionAlert = new SectionAlert();
        sectionAlert.setAlertId(rs.getInt("alert_id"));
        sectionAlert.setAppUserId(rs.getInt("app_user_id"));
        sectionAlert.setAlertContent(rs.getString("alert_content"));
        sectionAlert.setFutureSections(rs.getBoolean("future_sections"));

        AlertCategoryMapper alertMapper = new AlertCategoryMapper();
        sectionAlert.setAlertCategory(alertMapper.mapRow(rs, rowNum));

        TrailSectionMapper sectionMapper = new TrailSectionMapper();
        sectionAlert.setTrailSection(sectionMapper.mapRow(rs, rowNum));

        return sectionAlert;
    }
}
