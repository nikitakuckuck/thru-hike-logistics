package learn.thruhike.data.mappers;

import learn.thruhike.models.TownContact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TownContactMapper implements RowMapper<TownContact> {
    @Override
    public TownContact mapRow(ResultSet rs, int rowNum) throws SQLException {
        TownContact contact = new TownContact();
        contact.setTownContactId(rs.getInt("town_contact_id"));
        contact.setAppUserId(rs.getInt("app_user_id"));
        contact.setCategory(rs.getString("contact_category"));
        contact.setContent(rs.getString("town_contact_content"));
        contact.setOtherNotes(rs.getString("town_contact_other_notes"));
        contact.setTrailSectionId(rs.getInt("trail_section_id"));
        return contact;
    }
}
