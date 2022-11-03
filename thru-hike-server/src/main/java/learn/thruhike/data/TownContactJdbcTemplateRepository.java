package learn.thruhike.data;

import learn.thruhike.data.mappers.TownContactMapper;
import learn.thruhike.models.TownContact;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TownContactJdbcTemplateRepository implements TownContactRepository {
    private final JdbcTemplate template;

    public TownContactJdbcTemplateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<TownContact> findAll(){
        final String sql = "select town_contact_id, app_user_id, contact_category, town_contact_content, town_contact_other_notes, trail_section_id from town_contact;";
        return template.query(sql, new TownContactMapper());
    }

    @Override
    public List<TownContact> findByTrailSection(int sectionId){
        final String sql = "select town_contact_id, app_user_id, contact_category, town_contact_content, town_contact_other_notes, trail_section_id from town_contact where trail_section_id = ?;";
        return template.query(sql, new TownContactMapper(), sectionId);
    }

    @Override
    public TownContact findById(int id){
        final String sql = "select town_contact_id, app_user_id, contact_category, town_contact_content, town_contact_other_notes, trail_section_id from town_contact where town_contact_id = ?;";
        try {
            return template.queryForObject(sql, new TownContactMapper(), id);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public TownContact add(TownContact contact){
        final String sql = "insert into town_contact (app_user_id, contact_category, town_contact_content, town_contact_other_notes, trail_section_id) values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, contact.getAppUserId());
            preparedStatement.setString(2,contact.getCategory());
            preparedStatement.setString(3, contact.getContent());
            preparedStatement.setString(4, contact.getOtherNotes());
            preparedStatement.setInt(5, contact.getTrailSectionId());
            return preparedStatement;
        }, keyHolder);
        if(rowsAffected<=0){
            return null;
        }
        contact.setTownContactId(keyHolder.getKey().intValue());
        return contact;
    }

    @Override
    public boolean update(TownContact contact){
        final String sql = "update town_contact set contact_category = ?, town_contact_content = ?, town_contact_other_notes = ? where town_contact_id = ?;";
        int rowsAffected = template.update(sql, contact.getCategory(), contact.getContent(), contact.getOtherNotes(), contact.getTownContactId());
        return rowsAffected>0;
    }

    @Override
    public boolean deleteById(int id){
        final String sql = "delete from town_contact where town_contact_id = ?;";
        int rowsAffected = template.update(sql,id);
        return rowsAffected>0;
    }



}
