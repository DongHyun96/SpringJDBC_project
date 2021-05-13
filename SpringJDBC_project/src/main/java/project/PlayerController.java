package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Player;
import data.NameRank;

@RestController
public class PlayerController {
	
	@Autowired
	private PlayerManager playerManager;
	
	@Autowired
	private Ranking ranking;
	
	@GetMapping(value = "/getallplayer", produces = "application/json" )
	public List<Player> getAllPlayer() {
		System.out.println("Send all player");
		return playerManager.findAll();
	}
	
	@PostMapping(value = "/updateplayer", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public int updateUser(@RequestBody Player p) {
		// Can update score, coin, companion
		System.out.println("Received new player data: " + p.toString());
		int result = playerManager.update(p);
		return result;
	}
	
	@PostMapping(value = "/getplayer", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Player getPlayerByName(@RequestBody NameRank nr) {
		// Get player by user name. Only name has to be matched.
		System.out.println("Received Name: " + nr.getUserName());
		Player result = playerManager.findByPlayerName(nr.getUserName());
		
		// No player found -> return null
		return result;
	}
	
	@GetMapping(value = "/sorted", produces = "application/json" )
	public List<NameRank> getSortedRank() {
		System.out.println("Send sorted Rank List of Player");
		return ranking.sortedPlayerList();
	}
	
	@PostMapping(value = "/getrankscore", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public NameRank getPlayerRank(@RequestBody Player p) {
		System.out.println("Get " + p.getUserName() + "'s rank.");
		NameRank result = ranking.getPlayerRank(p);
		return result;
	}

}
