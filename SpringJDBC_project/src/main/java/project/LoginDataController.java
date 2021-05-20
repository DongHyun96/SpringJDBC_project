package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.LoginData;
import jsonObj.EmailPassword;
import jsonObj.Flag;

@RestController
public class LoginDataController {
	
	@Autowired
	private LoginDataManager lDataManager;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping(value = "/signupuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public LoginData signUpUser(@RequestBody LoginData data) {
		// case1 - Email already in DB -> return JSON data with email="error"
		// case2 - username already in DB -> return JSON data with username="error"
		System.out.println("Received a person: " + data.getUserName());
		
		String encodedPassword = this.passwordEncoder.encode(data.getPassword());  // password encryption
		
		data.setPassword(encodedPassword);
		
		LoginData result = lDataManager.insert(data.getUserName(), data.getEmail(), data.getPassword(), data.getUserVersion());
		
		return result;
		
	}
	
	@PostMapping(value = "/signinuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public LoginData signInUser(@RequestBody EmailPassword ep) {
		
		System.out.println("Received a signin request: " + ep.getEmail());
		
		LoginData lData = lDataManager.findByEmail(ep.getEmail());
	
		if (lData == null) {  // When there is no such email, return null"
			return lData;
		}
		else { // When email exists in DB
			
			Boolean isPasswordMatch = this.passwordEncoder.matches(ep.getPassword(), lData.getPassword());

			if (isPasswordMatch) {  // Email and password is valid! return valid user data
				System.out.println("Valid!");
				return lData;
			}
			
			else { // When password is wrong, return JSON data with password="error"
				lData.setPassword("error");
				System.out.println("password is wrong!");
				return lData;
			}
		}
	}
	
	@GetMapping(value = "/getalluser", produces = "application/json" )
	public List<LoginData> getAllUser() {
		System.out.println("Send all user");
		return lDataManager.findAll();
	}
	
	@PostMapping(value = "/getuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public LoginData getUserByEmail(@RequestBody EmailPassword ep) {
		// Get User by Email, only email has to be matched.
		System.out.println("Received Email: " + ep.getEmail());
		LoginData result = lDataManager.findByEmail(ep.getEmail());
		
		// If user is not exist..return null
		return result;
	}
	
	@PostMapping(value = "/updateuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag updateUser(@RequestBody LoginData data) {
		// Only for changing password and userversion. Something goes wrong, then return flag = 0
	
		System.out.println("Received new user data: " + data.toString());
		
		Flag f = new Flag();
		
		f.setFlag(lDataManager.update(data));
		
		return f;
	}
	
	
	/*
	 *  Username / email 일치해야 함 무조건! 지키기 -> 안그러면 DB가 꼬임
	 * 
	 */
	@PostMapping(value = "/deleteuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag deleteUser(@RequestBody LoginData data) {
		System.out.println("Received deleting user data: " + data.toString());
		
		//int flag = userManager.delete(data);
		
		Flag f = new Flag();
		f.setFlag(lDataManager.delete(data));
		return f;
	}
	
}
