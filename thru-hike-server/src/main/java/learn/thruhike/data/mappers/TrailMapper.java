package learn.thruhike.data.mappers;

import learn.thruhike.models.Trail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrailMapper implements RowMapper<Trail> {
    @Override
    public Trail mapRow(ResultSet rs, int rowNum) throws SQLException {
        Trail trail = new Trail();
        trail.setTrailId(rs.getInt("trail_id"));
        trail.setAppUserId(rs.getInt("app_user_id"));
        trail.setTrailName(rs.getString("trail_name"));
        trail.setTrailAbbreviation(rs.getString("trail_abbreviation"));
        return trail;
    }
}
