package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import data.Player;
import data.PlayerRepository;
import data.User;
import data.UserRepository;

@Service
public class UserManagerImpl implements UserManager {

	@Autowired
	private UserRepository repo;

	@Autowired
	private PlayerRepository repoPlayer;
	
	/**
	 * When inserting new user, player data is inserted too, based on the user name.
	 */
	@Override
	public User insert(String username, String email, String password, String userVersion) {
		User user = new User(username, email, password, userVersion);
		
		if (findByEmail(email) == null) {
			// When Email is not duplicated.
			
			if (findByUserName(username) == null) {
				// When Email and username is not duplicated.
				
				user = repo.save(user);
				repoPlayer.save(new Player(username, 0, 0, "default"));
				return user;
			}
			System.out.println("From userManager: username duplicated! insert fails...");
			user.setUserName("error");
			return user;
		}
		System.out.println("From userManager: Email duplicated! insert fails...");
		user.setEmail("error");
		return user;
	}

	@Override
	public User findOne(long id) {
		return repo.findOne(id);
	}
	
	// a custom service method that uses findAll()
	@Override
	public User findByEmail(String email) {
		for (User u : findAll()) {
			if (u.getEmail().equalsIgnoreCase(email)) {
				return u;
			}
		}
		// No Player found...
		return null;
	}
	
	@Override
	public User findByUserName(String name) {
		for (User u : findAll()) {
			if (u.getUserName().equalsIgnoreCase(name)) {
				return u;
			}
		}
		// No User found...
		return null;
	}

	@Override
	public List<User> findAll() {
		return repo.findAll();
	}

	
	@Override
	public int update(User u) {
		// Only password and userVersion can be update.
		return repo.update(u);
	}

	@Override
	public int delete(User u) {
		// Also delete player data. If delete fails, return 0 / 1 on vise versa
		
		int result = repo.delete(u);
		
		if (result == 1) {
			Player p = repoPlayer.findOne(u.getUserName());
			return repoPlayer.delete(p);
		}
		else {
			return 0;
		}
	}

}
