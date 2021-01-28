package jdbc.template;

import java.sql.CallableStatement;
import java.sql.SQLException;

public interface CallableStatementSetter {
	CallableStatement setValues(CallableStatement cstmt) throws SQLException ;
}
