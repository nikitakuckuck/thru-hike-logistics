package learn.thruhike.data.mappers;

import learn.thruhike.models.Trail;
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
        section.setSectionNickname(rs.getString("section_nickname"));
        section.setSectionStart(rs.getString("section_start"));
        section.setSectionEnd(rs.getString("section_end"));
        section.setLatitude(rs.getDouble("latitude"));
        section.setLongitude(rs.getDouble("longitude"));
        section.setSectionLength(rs.getInt("section_length"));
        section.setSectionDays(rs.getInt("section_days"));
        section.setUpcoming(rs.getBoolean("upcoming"));

        TrailMapper trailMapper = new TrailMapper();
        section.setTrail(trailMapper.mapRow(rs,rowNum));

        return section;
    }
}
