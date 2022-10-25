package learn.thruhike.data.mappers;

import learn.thruhike.models.ExitItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExitItemMapper implements RowMapper<ExitItem> {
    @Override
    public ExitItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExitItem townExit = new ExitItem();
        townExit.setExitItemId(rs.getInt("exit_item_id"));
        townExit.setAppUserId(rs.getInt("app_user_id"));
        townExit.setExitItemName(rs.getString("exit_item_name"));
        townExit.setGoodToGo(rs.getBoolean("good_to_go"));
        return townExit;
    }
}
