package project;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import data.Player;
import data.User;
import data.NameRank;


@SpringBootApplication(scanBasePackages={"data", "project"})  // Some package problem
public class SpringJDBCDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringJDBCDemoApplication.class, args);
		

		PlayerManager playerManager = ctx.getBean(PlayerManager.class);
		UserManager userManager = ctx.getBean(UserManager.class);
		Ranking ranking = ctx.getBean(Ranking.class);
	
		System.out.println("All players: ");
		for (Player u : playerManager.findAll()) {
			System.out.println(u);
		}
		System.out.println("");
		
		
		System.out.println("All users: ");
		for (User u : userManager.findAll()) {
			System.out.println(u);
		}
		System.out.println("");
		
		
		System.out.println("Sorted players by score: ");
		for (NameRank u : ranking.sortedPlayerList()) {
			System.out.println(u.toString());
		}
		System.out.println("");
		
		//ctx.close();
		/*
		teemu.setEmail("[private]");
		userManager.update(teemu);
		System.out.println("Updated: " + userManager.findOne(teemu.getId()));
		*/
		
		/*
		System.out.println("Find DongHyun by name in player DB: ");
		Player j = playerManager.findByPlayerName("DongHyun");
		System.out.println("Result: " + j.toString());
		*/
		
		/*
		System.out.println("Find DongHyun by name in user DB: ");
		User x = userManager.findByUserName("Donghyun");
		System.out.println("Result: " + x.toString());
		
		System.out.println("");
		System.out.println("Update DongHyun's score");
		Player donghyun = playerManager.findOne("DH");
		donghyun.setScore(0);
		playerManager.update(donghyun);
		System.out.println("Updated: " + playerManager.findOne("DH"));
		*/
		
		/*
		System.out.println("Delete all: ");
		for (Player p : playerManager.findAll()) {
			playerManager.delete(p);
			System.out.println(p);
		}
		
		for (User p : userManager.findAll()) {
			userManager.delete(p);
			System.out.println(p);
		}
		*/
		
		// close the context
		//ctx.close();

	}

}
