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

@Repository
public class JdbcUserRepository implements UserRepository {

	private JdbcOperations jdbc;

	private static final String SQL_INSERT = "insert into user (username, email, password, userVersion) values (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update user set password=?, userVersion=? where email=?";
	private static final String SQL_FIND_ONE = "select * from user where id = ?";
	private static final String SQL_FIND_ALL = "select * from user order by username";
	private static final String SQL_DELETE_ONE = "delete from user where email = ?";
	
	@Autowired
	public JdbcUserRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public User findOne(long id) {
		return jdbc.queryForObject(SQL_FIND_ONE, new UserRowMapper(), id);
	}

	@Override
	public User save(final User user) {
		// KeyHolder will hold the ID after insertion
		KeyHolder keyHolder = new GeneratedKeyHolder();

		// insert with PreparedStatement. This is required for KeyHolder to work.
		
		
		int rows = jdbc.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
				PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] { "id" });
				ps.setString(1, user.getUserName());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getUserVersion());
				return ps;
			}
		}, keyHolder);
		
		// check if insert was OK
		if (rows == 1) {
			// update ID and return the object
			user.setId(keyHolder.getKey().longValue());
			return user;
		} else {
			return null;
		}
	}
	
	public List<User> findAll() {
		return jdbc.query(SQL_FIND_ALL, new UserRowMapper());
	}

	public int update(User u) {
		// Can update password and userversion
		return jdbc.update(SQL_UPDATE, u.getPassword(), u.getUserVersion(), u.getEmail());
	}

	public int delete(User u) {
		return jdbc.update(SQL_DELETE_ONE, u.getEmail());
	}
	
	
	private static class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getLong("id"), rs.getString("username"), rs.getString("email"),
					rs.getString("password"), rs.getString("userVersion"));
		}
	}
}
