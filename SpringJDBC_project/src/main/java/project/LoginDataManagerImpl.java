package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import data.UserDataRepository;
import jsonObj.CompanionData;
import jsonObj.LoginData;
import jsonObj.UserData;
import data.CompDataRepository;
import data.LoginDataRepository;

@Service
public class LoginDataManagerImpl implements LoginDataManager {

	@Autowired
	private LoginDataRepository lRepo;

	@Autowired
	private UserDataRepository uRepo;
	
	@Autowired
	private CompDataRepository cRepo;
	
	/**
	 * When inserting new loginData, UserData is inserted too, based on the user name.
	 */
	@Override
	public LoginData insert(LoginData ld) {
		
		if (findByEmail(ld.getEmail()) == null) {
			// When Email is not duplicated.
			
			if (findByUserName(ld.getUserName()) == null) {
				// When Email and username is not duplicated.
				
				ld = lRepo.save(ld);
				uRepo.save(new UserData(ld.getUserName(), 0, 0, "knight"));
				cRepo.save(new CompanionData(ld.getUserName(), true, false, false));
				return ld;
			}
			System.out.println("From loginManager: username duplicated! insert fails...");
			ld.setUserName("error");
			return ld;
		}
		System.out.println("From loginManager: Email duplicated! insert fails...");
		ld.setEmail("error");
		return ld;
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
		
		if (u == null || !u.getUserName().equals(data.getUserName())) {
			// When userName doesn't exist in user_table, or name doesn't match with each DB table
			return 0;
		}
		else {
			int flag = lRepo.delete(data); // Flag stores interim result
		
			if (flag == 1) { // When LoginData deleted successfully, then delete userData and companionData as well.
				cRepo.delete(cRepo.findOne(data.getUserName()));
				uRepo.delete(u);
				return 1;
			}
			
			return 0;
		}
	}
	
}
