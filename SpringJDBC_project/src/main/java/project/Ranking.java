package project;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.UserData;
import data.UserDataRepository;
import jsonObj.NameRank;

@Service
public class Ranking {
	
	@Autowired
	private UserDataRepository repo;
	
	// Maybe exception handling needed.
	public List<NameRank> sortedUserList() {
		List<UserData> sorted = repo.findAll();
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
	
	public NameRank getUserRank(UserData data) {
		List<NameRank> ranklist = sortedUserList();
		
		for (NameRank nr : ranklist) {
			if (nr.getUserName().equals(data.getUserName()))
				return nr;
		}
		// Can't find user
		System.out.println("User not found!");
		return null;
		
	}
}
