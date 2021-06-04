package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jsonObj.CompanionData;

@Repository
public class JdbcCompRepository implements CompDataRepository {
	
	private JdbcOperations jdbc;

	private static final String SQL_FIND_ONE = "select * from companion_table where username = ? and companion = ?"; // Not sure...yet
	private static final String SQL_FIND_NAME = "select * from companion_table where username = ?";
	private static final String SQL_INSERT = "insert into companion_table (username, companion, level) values (?, ?, ?)";
	private static final String SQL_UPDATE = "update companion_table set level=? where username=? and companion = ?";
	private static final String SQL_DELETE_ONE = "delete from companion_table where username = ? and companion = ?";
	private static final String SQL_DELETE_BY_NAME = "delete from companion_table where username = ?";

	
	@Autowired
	public JdbcCompRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public CompanionData findOne(String userName, String comp) {
		try {
			return jdbc.queryForObject(SQL_FIND_ONE, new CompRowMapper(), userName, comp);
		}
		catch (Exception e) {
			return null; // If compdata doesn't exist
		}
	}
	
	@Override
	public List<CompanionData> findByName(String userName) {
		try {
			return jdbc.query(SQL_FIND_NAME, new CompRowMapper(), userName);
		} catch (Exception e) {
			// If compdata doesn't exist
			return null;
		}
	}
	
	@Override
	public CompanionData save(CompanionData c) {
		try {
			int rows = jdbc.update(SQL_INSERT, c.getUserName(), c.getCompanion(), c.getLevel());
			
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
		return jdbc.update(SQL_UPDATE, c.getLevel(), c.getUserName(), c.getCompanion());
	}

	@Override
	public int deleteOne(String userName, String comp) {
		return jdbc.update(SQL_DELETE_ONE, userName, comp);
	}
	
	@Override
	public int deleteByName(String userName) {
		return jdbc.update(SQL_DELETE_BY_NAME, userName);
	}
	
	private static class CompRowMapper implements RowMapper<CompanionData>{
		public CompanionData mapRow(ResultSet rs, int rowNum) throws SQLException{
			return new CompanionData(rs.getString("username"),
					rs.getString("companion"), rs.getInt("level"));
		}
	}

}
