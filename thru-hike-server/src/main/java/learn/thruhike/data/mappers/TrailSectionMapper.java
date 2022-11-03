package learn.thruhike.data.mappers;

import learn.thruhike.models.TrailSection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrailSectionMapper implements RowMapper<TrailSection> {
    @Override
    public TrailSection mapRow(ResultSet rs, int rowNum) throws SQLException {
        TrailSection section = new TrailSection();
        section.setTrailSectionId(rs.getInt("trail_section_id"));
        section.setAppUserId(rs.getInt("app_user_id"));
        section.setTrailId(rs.getInt("trail_id"));
        section.setSectionStart(rs.getString("section_start"));
        section.setSectionEnd(rs.getString("section_end"));
        section.setStartLatitude(rs.getDouble("start_latitude"));
        section.setStartLongitude(rs.getDouble("start_longitude"));
        section.setEndLatitude(rs.getDouble("end_latitude"));
        section.setEndLongitude(rs.getDouble("end_longitude"));
        section.setSectionLength(rs.getInt("section_length"));
        section.setSectionDays(rs.getInt("section_days"));
        section.setUpcoming(rs.getBoolean("upcoming"));
        section.setActive(rs.getBoolean("active"));

        TrailMapper trailMapper = new TrailMapper();
        section.setTrail(trailMapper.mapRow(rs,rowNum));

        return section;
    }
}
