package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.UserData;
import jsonObj.Flag;
import jsonObj.NameRank;

@RestController
public class UserDataController {
	
	@Autowired
	private UserDataManager uDataManager;
	
	@Autowired
	private Ranking ranking;
	
	@GetMapping(value = "/getallplayer", produces = "application/json" )
	public List<UserData> getAllPlayer() {
		System.out.println("Send all player");
		return uDataManager.findAll();
	}
	
	@PostMapping(value = "/updateplayer", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag updatePlayer(@RequestBody UserData p) {
		// Can update score, coin, companion
		System.out.println("Received new player data: " + p.toString());
		
		Flag f = new Flag();
		
		f.setFlag(uDataManager.update(p));
		
		return f;
	}
	
	@PostMapping(value = "/getplayer", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public UserData getPlayerByName(@RequestBody NameRank nr) {
		// Get player by user name. Only name has to be matched.
		System.out.println("Received Name: " + nr.getUserName());
		UserData result = uDataManager.findByPlayerName(nr.getUserName());
		
		// No player found -> return null
		return result;
	}
	
	@GetMapping(value = "/sorted", produces = "application/json" )
	public List<NameRank> getSortedRank() {
		System.out.println("Send sorted Rank List of Player");
		return ranking.sortedUserList();
	}
	
	@PostMapping(value = "/getrankscore", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public NameRank getPlayerRank(@RequestBody UserData p) {
		System.out.println("Get " + p.getUserName() + "'s rank.");
		NameRank result = ranking.getUserRank(p);
		return result;
	}

}
