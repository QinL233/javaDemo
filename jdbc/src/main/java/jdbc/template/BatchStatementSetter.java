package jdbc.template;

import java.sql.SQLException;
import java.sql.Statement;

public interface BatchStatementSetter {
	Statement setValues(Statement stmt) throws SQLException ;
}
