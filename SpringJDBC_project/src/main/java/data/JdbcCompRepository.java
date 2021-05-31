package data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jsonObj.CompanionData;

@Repository
public class JdbcCompRepository implements CompDataRepository {
	
	private JdbcOperations jdbc;

	private static final String SQL_FIND_ONE = "select * from companion_table where username = ?";
	private static final String SQL_INSERT = "insert into companion_table (username, knight, archer, mage) values (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update companion_table set knight=?, archer=?, mage=? where username=?";
	private static final String SQL_DELETE_ONE = "delete from companion_table where username = ?";

	
	@Autowired
	public JdbcCompRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public CompanionData findOne(String userName) {
		try {
			return jdbc.queryForObject(SQL_FIND_ONE, new CompRowMapper(), userName);
		}
		catch (Exception e) {
			return null; // If compdata doesn't exist
		}
	}

	@Override
	public CompanionData save(CompanionData c) {
		try {
			int rows = jdbc.update(SQL_INSERT, c.getUserName(), c.isKnight(), c.isArcher(), c.isMage());
			
			// Check if insert was OK
			if (rows == 1) {
				return c;
			}
		}
		catch(Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public int update(CompanionData c) {
		return jdbc.update(SQL_UPDATE, c.isKnight(), c.isArcher(), c.isMage(),
				c.getUserName());
	}

	@Override
	public int delete(CompanionData c) {
		return jdbc.update(SQL_DELETE_ONE, c.getUserName());
	}
	
	private static class CompRowMapper implements RowMapper<CompanionData>{
		public CompanionData mapRow(ResultSet rs, int rowNum) throws SQLException{
			return new CompanionData(rs.getString("username"),
					rs.getBoolean("knight"), rs.getBoolean("archer"), rs.getBoolean("mage"));
		}
	}

}
