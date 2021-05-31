package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jsonObj.Flag;
import jsonObj.NameRank;
import jsonObj.UserData;

@RestController
public class UserDataController {
	
	@Autowired
	private UserDataManager uDataManager;
	
	@Autowired
	private Ranking ranking;
	
	@GetMapping(value = "/getall/user", produces = "application/json" )
	public List<UserData> getAllUser() {
		System.out.println("Send all player");
		return uDataManager.findAll();
	}
	
	@PostMapping(value = "/get/user", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public UserData getUserByName(@RequestBody NameRank nr) {
		// Get player by user name. Only name has to be matched.
		
		System.out.println("(getUserByName)Received Name: " + nr.getUserName());
		UserData result = uDataManager.findByUserName(nr.getUserName());
		
		// No player found -> return null
		return result;
	}
	
	@PostMapping(value = "/update/user", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag updateUser(@RequestBody UserData u) {
		// Can update score, coin, companion
		System.out.println("Received new player data: " + u.toString());
		
		Flag f = new Flag();
		
		f.setFlag(uDataManager.update(u));
		
		return f;
	}
	
	@GetMapping(value = "/sorted", produces = "application/json" )
	public List<NameRank> getSortedRank() {
		System.out.println("Send sorted Rank List of Player");
		return ranking.sortedUserList();
	}
	
	@PostMapping(value = "/getrankscore", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public NameRank getUserRank(@RequestBody UserData u) {
		System.out.println("Get " + u.getUserName() + "'s rank.");
		NameRank result = ranking.getUserRank(u);
		return result;
	}

}
