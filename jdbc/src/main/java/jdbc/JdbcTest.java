package jdbc;

import jdbc.template.JdbcTemplate;
import jdbc.template.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/28 14:15
 */
public class JdbcTest {
    /**
     * 使用jdbc模板操作数据库
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from sys_user";
        List<User> list = (List<User>) template.query(sql, new RowMapper<List<User>>() {
            public List<User> mapRow(ResultSet rs) throws SQLException {
                List<User> l = new ArrayList<User>();
                while (rs.next()){
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    l.add(user);
                }
                return l;
            }
        });
        list.forEach(i->{
            System.out.println(i);
        });
    }

}

class User implements Serializable {
    private static final long serialVersionUID = -7457366021102735595L;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}