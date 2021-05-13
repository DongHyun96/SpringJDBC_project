package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.User;
import data.EmailPassword;

@RestController
public class UserController {
	
	@Autowired
	private UserManager userManager;
	
	@PostMapping(value = "/signupuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public User signUpUser(@RequestBody User u) {
		// case1 - Email already in DB -> return JSON data with email="error"
		// case2 - username already in DB -> return JSON data with username="error"
		System.out.println("Received a person: " + u.getUserName());
		User result = userManager.insert(u.getUserName(), u.getEmail(), u.getPassword(), u.getUserVersion());
		return result;
	}
	
	@PostMapping(value = "/signinuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public User signInUser(@RequestBody EmailPassword ep) {
		
		System.out.println("Received a signin request: " + ep.getEmail());
		
		User u = userManager.findByEmail(ep.getEmail());
	
		if (u == null) {  // When there is no such email, return null"
			return u;
		}
		else { // When email exists in DB
			if (u.getPassword().equals(ep.getPassword())) {  // Email and password is valid! return valid user data
				System.out.println("Valid!");
				return u;
			}
			else { // When password is wrong, return JSON data with password="error"
				u.setPassword("error");
				return u;
			}
		}
	}
	
	@GetMapping(value = "/getalluser", produces = "application/json" )
	public List<User> getAllUser() {
		System.out.println("Send all user");
		return userManager.findAll();
	}
	
	@PostMapping(value = "/getuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public User getUserByEmail(@RequestBody EmailPassword ep) {
		// Get User by Email, only email has to be matched.
		System.out.println("Received Email: " + ep.getEmail());
		User result = userManager.findByEmail(ep.getEmail());
		
		// If user is not exist..return null
		return result;
	}
	
	@PostMapping(value = "/updateuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public User updateUser(@RequestBody User u) {
		// Only for changing password and userversion. Something goes wrong, then return null User data
		/*
		 * When Update fails, then you get,
		 * 
		 * {
    "id": 0,
    "userName": null,
    "email": null,
    "password": null,
    "userVersion": null}
		 */
		System.out.println("Received new user data: " + u.toString());
		int flag = userManager.update(u);
		
		User returnUser = new User();
		if (flag == 1) {
			// Update succeed
			returnUser = userManager.findByEmail(u.getEmail());
		}
		return returnUser;
	}
	
	
	/*
	 *  Username / email 일치해야 함 무조건! 지키기 -> 안그러면 DB가 꼬임
	 * 
	 */
	@PostMapping(value = "/deleteuser", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public User deleteUser(@RequestBody User u) {
		System.out.println("Received deleting user data: " + u.toString());
		int flag = userManager.delete(u);
		User returnUser = new User();
		
		if (flag == 1) {
			// delete succeed
			returnUser = u;
		}
		
		// If deletion fails, return null
		return returnUser;
	}
	
}
