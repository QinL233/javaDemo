package jdbc.template;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface BatchPreparedStatementSetter {
	PreparedStatement setValues(PreparedStatement pstmt) throws SQLException ;
}
