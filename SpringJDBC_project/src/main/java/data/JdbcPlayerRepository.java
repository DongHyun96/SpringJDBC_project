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
public class JdbcPlayerRepository implements PlayerRepository {

	private JdbcOperations jdbc;

	private static final String SQL_INSERT = "insert into player (username, score, coin, companion) values (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update player set score=?, coin=?, companion=? where username=?";
	private static final String SQL_FIND_ONE = "select * from player where username = ?";
	private static final String SQL_FIND_ALL = "select * from player order by username";
	private static final String SQL_DELETE_ONE = "delete from player where username = ?";
	
	@Autowired
	public JdbcPlayerRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public Player findOne(String username) {
		return jdbc.queryForObject(SQL_FIND_ONE, new UserRowMapper(), username);
	}

	@Override
	public Player save(final Player player) {
		// KeyHolder will hold the ID after insertion
		KeyHolder keyHolder = new GeneratedKeyHolder();

		// insert with PreparedStatement. This is required for KeyHolder to work.
		
		
		int rows = jdbc.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
				PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] { "username" });
				ps.setString(1, player.getUserName());
				ps.setInt(2, player.getScore());
				ps.setInt(3, player.getCoin());
				ps.setString(4, player.getCompanion());
				return ps;
			}
		}, keyHolder);
		
		// check if insert was OK
		if (rows == 1) {
			// update ID and return the object
			//player.setUserName(keyHolder.getKey().StringValue());
			return player;
		} else {
			return null;
		}
	}
	
	@Override
	public List<Player> findAll() {
		return jdbc.query(SQL_FIND_ALL, new UserRowMapper());
	}
	
	@Override
	public int update(Player p) {
		return jdbc.update(SQL_UPDATE, p.getScore(), p.getCoin(), p.getCompanion(),
				p.getUserName());
	}

	@Override
	public int delete(Player p) {
		return jdbc.update(SQL_DELETE_ONE, p.getUserName());
	}
	
	
	private static class UserRowMapper implements RowMapper<Player> {
		public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Player(rs.getString("username"), rs.getInt("score"), rs.getInt("coin"),
					rs.getString("companion"));
		}
	}
}
