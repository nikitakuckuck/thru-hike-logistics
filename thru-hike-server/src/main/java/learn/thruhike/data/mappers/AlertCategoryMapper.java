package learn.thruhike.data.mappers;

import learn.thruhike.models.AlertCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlertCategoryMapper implements RowMapper<AlertCategory> {
    @Override
    public AlertCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        AlertCategory alertCategory = new AlertCategory();
        alertCategory.setAlertCategoryId(rs.getInt("alert_category_id"));
        alertCategory.setAlertCategoryName(rs.getString("alert_category_name"));

        return alertCategory;
    }
}
