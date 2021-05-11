package project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.Player;
import data.PlayerRepository;

// TODO: input validation in methods

@Service
public class PlayerManagerImpl implements PlayerManager {

	@Autowired
	private PlayerRepository repo;

	@Override
	public Player insert(String username, int score, int coin, String companion) {
		try {
		Player player = repo.save(new Player(username, score, coin, companion));
		return player;
		} catch (Exception e) { 
			// When username(primary key) is duplicated. 
			System.out.println("From PlayerManagerImpl: Insertion fails due to duplicated username");
			return null;
		}
	}

	@Override
	public Player findOne(String userName) {
		return repo.findOne(userName);
	}
	
	// a custom service method that uses findAll()
	@Override
	public Player findByPlayerName(String name) {
		for (Player p : findAll()) {
			if (p.getUserName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		// No Player found...
		return null;
	}

	@Override
	public List<Player> findAll() {
		return repo.findAll();
	}

	
	@Override
	public int update(Player p) {
		// Update score, coin, companion
		return repo.update(p);
	}

	@Override
	public int delete(Player p) {
		return repo.delete(p);
	}


}
