package data;

import java.util.List;

public interface PlayerRepository {
	//CRUD ( create, read, update, delete )

	Player findOne(String userName);

	Player save(Player player);
	
	List<Player> findAll();
	
	int update(Player p);
	
	int delete(Player p);
	
}
