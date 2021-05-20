package data;

import java.math.BigInteger;

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
public class JdbcUserDataRepository implements UserDataRepository {

	private JdbcOperations jdbc;

	private static final String SQL_INSERT = "insert into player (username, score, coin, companion) values (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update player set score=?, coin=?, companion=? where username=?";
	private static final String SQL_FIND_ONE = "select * from player where username = ?";
	private static final String SQL_FIND_ALL = "select * from player order by username";
	private static final String SQL_DELETE_ONE = "delete from player where username = ?";
	
	@Autowired
	public JdbcUserDataRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public UserData findOne(String username) {
		return jdbc.queryForObject(SQL_FIND_ONE, new UserRowMapper(), username);
	}

	@Override
	public UserData save(final UserData u) {
		// KeyHolder will hold the ID after insertion
		KeyHolder keyHolder = new GeneratedKeyHolder();

		// insert with PreparedStatement. This is required for KeyHolder to work.
		
		
		int rows = jdbc.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
				PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] { "username" });
				ps.setString(1, u.getUserName());
				ps.setInt(2, u.getScore());
				ps.setInt(3, u.getCoin());
				ps.setString(4, u.getCompanion());
				return ps;
			}
		}, keyHolder);
		
		// check if insert was OK
		if (rows == 1) {
			return u;
		} else {
			return null;
		}
	}
	
	@Override
	public List<UserData> findAll() {
		return jdbc.query(SQL_FIND_ALL, new UserRowMapper());
	}
	
	@Override
	public int update(UserData u) {
		return jdbc.update(SQL_UPDATE, u.getScore(), u.getCoin(), u.getCompanion(),
				u.getUserName());
	}

	@Override
	public int delete(UserData u) {
		return jdbc.update(SQL_DELETE_ONE, u.getUserName());
	}
	
	
	private static class UserRowMapper implements RowMapper<UserData> {
		public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new UserData(rs.getString("username"), rs.getInt("score"), rs.getInt("coin"),
					rs.getString("companion"));
		}
	}
}
