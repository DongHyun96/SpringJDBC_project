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
		System.out.println("Received a signup request: " + data.getUserName());
		
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
	
	@GetMapping(value = "/getall/logindata", produces = "application/json" )
	public List<LoginData> getAllLoginD() {
		System.out.println("Send all user");
		return lDataManager.findAll();
	}
	
	@PostMapping(value = "/get/logindata", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public LoginData getLoginDataByEmail(@RequestBody EmailPassword ep) {
		// Get Login data by Email, only email has to be matched.
		System.out.println("Received Email: " + ep.getEmail());
		LoginData result = lDataManager.findByEmail(ep.getEmail());
		
		// If loginD is not exist..return null
		return result;
	}
	
	@PostMapping(value = "/update/logindata", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag updateLoginD(@RequestBody LoginData data) {
		// Only for changing password and userversion. Something goes wrong, then return flag = 0
	
		System.out.println("Received new user data: " + data.toString());
		
		Flag f = new Flag();
		
		String encodedPassword = this.passwordEncoder.encode(data.getPassword());  // password encryption
		
		data.setPassword(encodedPassword);
		
		f.setFlag(lDataManager.update(data));
		
		return f;
	}
	
	
	@PostMapping(value = "/delete", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag deleteUser(@RequestBody LoginData data) {
		// Delete Login data and also delete user data too.
		// Given password must have to be matched.
		System.out.println("Received deleting user data: " + data.toString());
		
		Flag f = new Flag();
		LoginData lData = lDataManager.findByEmail(data.getEmail());
		
		// Check if the password is matched
		Boolean isPasswordMatch = this.passwordEncoder.matches(data.getPassword(), lData.getPassword()); 
		
		// If the password doesn't match, return flag with number 2
		if (!isPasswordMatch) {
			f.setFlag(2);
			return f;
		}
		else {
			f.setFlag(lDataManager.delete(data)); // If deletion succeed, then return 1, otherwise return 0
			
			return f;
		}		
	}
	
}
