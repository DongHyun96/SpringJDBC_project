package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import data.UserData;
import data.UserDataRepository;
import data.LoginData;
import data.LoginDataRepository;

@Service
public class LoginDataManagerImpl implements LoginDataManager {

	@Autowired
	private LoginDataRepository repo;

	@Autowired
	private UserDataRepository repoPlayer;
	
	/**
	 * When inserting new user, player data is inserted too, based on the user name.
	 */
	@Override
	public LoginData insert(String username, String email, String password, String userVersion) {
		LoginData user = new LoginData(username, email, password, userVersion);
		
		if (findByEmail(email) == null) {
			// When Email is not duplicated.
			
			if (findByUserName(username) == null) {
				// When Email and username is not duplicated.
				
				user = repo.save(user);
				repoPlayer.save(new UserData(username, 0, 0, "default"));
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
	public LoginData findOne(long id) {
		return repo.findOne(id);
	}
	
	// a custom service method that uses findAll()
	@Override
	public LoginData findByEmail(String email) {
		for (LoginData u : findAll()) {
			if (u.getEmail().equalsIgnoreCase(email)) {
				return u;
			}
		}
		// No Player found...
		return null;
	}
	
	@Override
	public LoginData findByUserName(String name) {
		for (LoginData u : findAll()) {
			if (u.getUserName().equalsIgnoreCase(name)) {
				return u;
			}
		}
		// No User found...
		return null;
	}

	@Override
	public List<LoginData> findAll() {
		return repo.findAll();
	}

	
	@Override
	public int update(LoginData data) {
		// Only password and userVersion can be update.
		return repo.update(data);
	}

	@Override
	public int delete(LoginData data) {
		// Also delete player data. If delete fails, return 0 / 1 on vise versa
		
		int result = repo.delete(data);
		
		if (result == 1) {
			UserData p = repoPlayer.findOne(data.getUserName());
			return repoPlayer.delete(p);
		}
		else {
			return 0;
		}
	}

}
