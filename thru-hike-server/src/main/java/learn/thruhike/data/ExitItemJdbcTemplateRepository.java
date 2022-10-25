package learn.thruhike.data;

import learn.thruhike.data.mappers.ExitItemMapper;
import learn.thruhike.models.ExitItem;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ExitItemJdbcTemplateRepository implements ExitItemRepository {
    private final JdbcTemplate template;

    public ExitItemJdbcTemplateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<ExitItem> findAll(){
        final String sql = "select `exit_item_id`, `app_user_id`, `exit_item_name`, `good_to_go` from exit_item;";
        return template.query(sql, new ExitItemMapper());
    }

    @Override
    public List<ExitItem> findAllIncomplete(){
        final String sql = "select `exit_item_id`, `app_user_id`, `exit_item_name`, `good_to_go` from exit_item where good_to_go = 0;";
        return template.query(sql, new ExitItemMapper());
    }

    @Override
    public ExitItem findById(int id){
        final String sql = "select `exit_item_id`, `app_user_id`, `exit_item_name`, `good_to_go` from exit_item where exit_item_id = ?;";
        try {
            return template.queryForObject(sql,new ExitItemMapper(),id);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public ExitItem add(ExitItem townExit){
        final String sql = "insert into exit_item (`app_user_id`, `exit_item_name`, `good_to_go`) values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,townExit.getAppUserId());
            preparedStatement.setString(2,townExit.getExitItemName());
            preparedStatement.setBoolean(3, townExit.isGoodToGo());
            return preparedStatement;
        },keyHolder);
        if(rowsAffected<=0){
            return null;
        }
        townExit.setExitItemId(keyHolder.getKey().intValue());
        return townExit;
    }

    @Override
    public boolean update(ExitItem townExit){
        final String sql = "update exit_item set `exit_item_name` = ?, `good_to_go` = ? where exit_item_id = ?;";
        int rowsAffected = template.update(sql, townExit.getExitItemName(), townExit.isGoodToGo(), townExit.getExitItemId());
        return rowsAffected>0;
    }

    @Override
    public boolean deleteById(int id){
        final String sql = "delete from exit_item where exit_item_id = ?;";
        int rowsAffected = template.update(sql,id);
        return rowsAffected > 0;
    }

}
