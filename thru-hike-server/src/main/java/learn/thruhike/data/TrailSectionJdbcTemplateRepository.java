package learn.thruhike.data;

import learn.thruhike.data.mappers.TrailMapper;
import learn.thruhike.data.mappers.TrailSectionMapper;
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
public class TrailSectionJdbcTemplateRepository implements TrailSectionRepository {
    private final JdbcTemplate template;

    public TrailSectionJdbcTemplateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<TrailSection> findAll() {
        final String sql = "select s.trail_section_id, s.app_user_id, s.trail_id, t.trail_name, t.trail_abbreviation, " +
                "s.section_start, s.section_end, s.latitude, s.longitude, s.section_length, s.section_days, s.upcoming, s.active " +
                "from trail_section s " +
                "join trail t on s.trail_id = t.trail_id order by t.trail_name;";
        return template.query(sql, new TrailSectionMapper());
    }

    @Override
    public List<TrailSection> findAllUpcoming(){
        final String sql = "select s.trail_section_id, s.app_user_id, s.trail_id, t.trail_name, t.trail_abbreviation, " +
                "s.section_start, s.section_end, s.latitude, s.longitude, s.section_length, s.section_days, s.upcoming, s.active " +
                "from trail_section s " +
                "join trail t on s.trail_id = t.trail_id where s.upcoming = 1;";
        return template.query(sql, new TrailSectionMapper());
    }

    @Override
    public List<TrailSection> findByTrailId(int id){
        final String sql = "select s.trail_section_id, s.app_user_id, s.trail_id, t.trail_name, t.trail_abbreviation, " +
                "s.section_start, s.section_end, s.latitude, s.longitude, s.section_length, s.section_days, s.upcoming, s.active " +
                "from trail_section s " +
                "join trail t on s.trail_id = t.trail_id where s.trail_id = ?;";
        return template.query(sql, new TrailSectionMapper(), id);
    }

    @Override
    public TrailSection findActive(){
        final String sql = "select s.trail_section_id, s.app_user_id, s.trail_id, t.trail_name, t.trail_abbreviation, " +
                "s.section_start, s.section_end, s.latitude, s.longitude, s.section_length, s.section_days, s.upcoming, s.active " +
                "from trail_section s " +
                "join trail t on s.trail_id = t.trail_id where s.active = 1;";
        try {
            return template.query(sql, new TrailSectionMapper()).stream().findFirst().orElse(null);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public TrailSection findById(int id) {
        final String sql = "select s.trail_section_id, s.app_user_id, s.trail_id, t.trail_name, t.trail_abbreviation, " +
                "s.section_start, s.section_end, s.latitude, s.longitude, s.section_length, s.section_days, s.upcoming, s.active " +
                "from trail_section s " +
                "join trail t on s.trail_id = t.trail_id where trail_section_id = ?;";
        try {
            return template.queryForObject(sql, new TrailSectionMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public TrailSection findBySectionNickname(String nickname){
        final String sql = "select s.trail_section_id, s.app_user_id, s.trail_id, t.trail_name, t.trail_abbreviation, " +
                "s.section_start, s.section_end, s.latitude, s.longitude, s.section_length, s.section_days, s.upcoming, s.active " +
                "from trail_section s " +
                "join trail t on s.trail_id = t.trail_id where s.section_nickname = ?;";
        try {
            return template.queryForObject(sql, new TrailSectionMapper(), nickname);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public TrailSection add(TrailSection section) {
        final String sql = "insert into trail_section (app_user_id, trail_id, " +
                "section_start, section_end, latitude, longitude, section_length, section_days, upcoming, active) " +
                "values(?,?,?,?,?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, section.getAppUserId());
            preparedStatement.setInt(2, section.getTrailId());
            preparedStatement.setString(3, section.getSectionStart());
            preparedStatement.setString(4, section.getSectionEnd());
            preparedStatement.setDouble(5, section.getLatitude());
            preparedStatement.setDouble(6, section.getLongitude());
            preparedStatement.setInt(7, section.getSectionLength());
            preparedStatement.setInt(8, section.getSectionDays());
            preparedStatement.setBoolean(9, section.isUpcoming());
            preparedStatement.setBoolean(10, section.isActive());
            return preparedStatement;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        setTrail(section);
        section.setTrailSectionId(keyHolder.getKey().intValue());
        return section;
    }

    @Override
    @Transactional
    public boolean update(TrailSection section) {

        //only one section may be active at any given time
        if(section.isActive() && findActive()!= null && section.getTrailSectionId()!= findActive().getTrailSectionId()){
            final String sql = "update trail_section set active = 0 where trail_section_id = ?;";
            template.update(sql, findActive().getTrailSectionId());
        }

        final String sql = "update trail_section set trail_id = ?, section_start = ?, " +
                "section_end = ?, latitude = ?, longitude = ?, section_length = ?, section_days = ?, " +
                "upcoming = ?, active = ? where trail_section_id = ?;";
        int rowsAffected = template.update(sql, section.getTrailId(),
                section.getSectionStart(), section.getSectionEnd(), section.getLatitude(), section.getLongitude(),
                section.getSectionLength(), section.getSectionDays(), section.isUpcoming(), section.isActive(), section.getTrailSectionId());
        setTrail(section);
        return rowsAffected > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        final String alertSql = "delete from section_alert where trail_section_id =?;";
        template.update(alertSql,id);
        final String sql = "delete from trail_section where trail_section_id = ?;";
        int rowsAffected = template.update(sql, id);
        return rowsAffected > 0;
    }

    private void setTrail(TrailSection section){
        final String sql = "select trail_id, app_user_id, trail_name, trail_abbreviation from trail where trail_id = ?;";
        Trail trail =  template.queryForObject(sql, new TrailMapper(), section.getTrailId());
        section.setTrail(trail);
    }



}
