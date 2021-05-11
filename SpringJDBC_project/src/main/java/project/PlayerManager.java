package project;

import java.util.List;

import data.Player;

public interface PlayerManager {
	Player insert(String username, int score, int coin, String companion);

	Player findOne(String userName);
	
	Player findByPlayerName(String name);

	List<Player> findAll();

	int update(Player p);

	int delete(Player p);
	
}
