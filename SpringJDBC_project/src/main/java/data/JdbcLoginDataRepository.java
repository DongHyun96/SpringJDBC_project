package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jsonObj.LoginData;

@Repository
public class JdbcLoginDataRepository implements LoginDataRepository {

	private JdbcOperations jdbc;

	private static final String SQL_INSERT = "insert into login_table (username, email, password, userversion) values (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update login_table set password=?, userversion=? where email=?";
	private static final String SQL_FIND_ONE = "select * from login_table where id = ?";
	private static final String SQL_FIND_ALL = "select * from login_table order by username";
	private static final String SQL_DELETE_ONE = "delete from login_table where email = ?";
	
	@Autowired
	public JdbcLoginDataRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public LoginData findOne(long id) {
		try {
		return jdbc.queryForObject(SQL_FIND_ONE, new DataRowMapper(), id);
		}
		catch (Exception e) {
			// When there is no such id
			return null;
		}
	}

	@Override
	public LoginData save(final LoginData loginData) {
		// KeyHolder will hold the ID after insertion
		KeyHolder keyHolder = new GeneratedKeyHolder();

		// insert with PreparedStatement. This is required for KeyHolder to work.
		
		
		int rows = jdbc.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
				PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] { "id" });
				ps.setString(1, loginData.getUserName());
				ps.setString(2, loginData.getEmail());
				ps.setString(3, loginData.getPassword());
				ps.setString(4, loginData.getUserVersion());
				return ps;
			}
		}, keyHolder);
		
		// check if insert was OK
		if (rows == 1) {
			// update ID and return the object
			loginData.setId(keyHolder.getKey().longValue());
			return loginData;
		} else {
			return null;
		}
	}
	
	public List<LoginData> findAll() {
		return jdbc.query(SQL_FIND_ALL, new DataRowMapper());
	}

	public int update(LoginData ld) {
		// Can update password and userversion
		return jdbc.update(SQL_UPDATE, ld.getPassword(), ld.getUserVersion(), ld.getEmail());
	}

	public int delete(LoginData ld) {
		return jdbc.update(SQL_DELETE_ONE, ld.getEmail());
	}
	
	
	private static class DataRowMapper implements RowMapper<LoginData> {
		public LoginData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new LoginData(rs.getLong("id"), rs.getString("username"), rs.getString("email"),
					rs.getString("password"), rs.getString("userVersion"));
		}
	}
}
