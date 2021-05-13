package project;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.Player;
import data.PlayerRepository;
import data.NameRank;

@Service
public class Ranking {
	
	@Autowired
	private PlayerRepository repo;
	
	// Maybe exception handling needed.
	public List<NameRank> sortedPlayerList() {
		List<Player> sorted = repo.findAll();
		Collections.sort(sorted);
		
		List<NameRank> ranklist = new ArrayList<NameRank>();
		int rank = 1;
		
		NameRank first = new NameRank();
		first.setUserName(sorted.get(0).getUserName());
		first.setRank(rank);
		first.setScore(sorted.get(0).getScore());
		ranklist.add(first);
		
		for (int i=1; i < sorted.size(); i++) {
			
			NameRank current = new NameRank();
			
			if (sorted.get(i).getScore() != sorted.get(i - 1).getScore()) {
				rank = rank + 1;
			}
			current.setRank(rank);
			current.setUserName(sorted.get(i).getUserName());
			current.setScore(sorted.get(i).getScore());
			
			ranklist.add(current);
		}
		
		return ranklist;
	}
	
	public NameRank getPlayerRank(Player p) {
		List<NameRank> ranklist = sortedPlayerList();
		
		for (NameRank nr : ranklist) {
			if (nr.getUserName().equals(p.getUserName()))
				return nr;
		}
		// Can't find players
		System.out.println("Player not found!");
		return null;
		
	}
}
