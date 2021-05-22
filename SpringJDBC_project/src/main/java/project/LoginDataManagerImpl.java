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
	private LoginDataRepository lRepo;

	@Autowired
	private UserDataRepository uRepo;
	
	/**
	 * When inserting new loginData, UserData is inserted too, based on the user name.
	 */
	@Override
	public LoginData insert(String username, String email, String password, String userVersion) {
		LoginData data = new LoginData(username, email, password, userVersion);
		
		if (findByEmail(email) == null) {
			// When Email is not duplicated.
			
			if (findByUserName(username) == null) {
				// When Email and username is not duplicated.
				
				data = lRepo.save(data);
				uRepo.save(new UserData(username, 0, 0, "default"));
				return data;
			}
			System.out.println("From loginManager: username duplicated! insert fails...");
			data.setUserName("error");
			return data;
		}
		System.out.println("From loginManager: Email duplicated! insert fails...");
		data.setEmail("error");
		return data;
	}

	@Override
	public LoginData findOne(long id) {
		return lRepo.findOne(id);
	}
	
	@Override
	public LoginData findByEmail(String email) {
		for (LoginData d : findAll()) {
			if (d.getEmail().equalsIgnoreCase(email)) {
				return d;
			}
		}
		// No data found...
		return null;
	}
	
	@Override
	public LoginData findByUserName(String name) {
		for (LoginData d : findAll()) {
			if (d.getUserName().equals(name)) {  // initial -> d.getUserName().equalsIgnoreCase(name)
				return d;
			}
		}
		// No data found...
		return null;
	}

	@Override
	public List<LoginData> findAll() {
		return lRepo.findAll();
	}

	
	@Override
	public int update(LoginData data) {
		// Only password and userVersion can be updated.
		return lRepo.update(data);
	}

	@Override
	public int delete(LoginData data) {
		// Also delete userData. If delete fails, return 0 ... 1 on vise versa
		
		UserData u = uRepo.findOne(data.getUserName());
		
		if (u == null) {
			// When userName doesn't exist in user_table
			return 0; 
		}
		else {
			int flag = lRepo.delete(data); // Flag stores interim result
		
			if (flag == 1) { // When LoginData deleted successfully, then delete userData as well.
				return uRepo.delete(u);
			}
			
			return 0;
		}
	}

}
