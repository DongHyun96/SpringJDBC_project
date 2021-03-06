package project;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import jsonObj.LoginData;
import jsonObj.NameRank;
import jsonObj.UserData;

@SpringBootApplication(exclude=SecurityAutoConfiguration.class ,scanBasePackages={"data", "project"})
public class SpringJDBCDemoApplication {
	

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringJDBCDemoApplication.class, args);
		

		UserDataManager playerManager = ctx.getBean(UserDataManager.class);
		LoginDataManager userManager = ctx.getBean(LoginDataManager.class);
		Ranking ranking = ctx.getBean(Ranking.class);
	
		System.out.println("All players: ");
		for (UserData u : playerManager.findAll()) {
			System.out.println(u);
		}
		System.out.println("");
		
		
		System.out.println("All users: ");
		for (LoginData u : userManager.findAll()) {
			System.out.println(u);
		}
		System.out.println("");
		
		/*
		System.out.println("Find one user: ");
		UserData user = playerManager.findOne("4");
		System.out.println("Found user: " + user);
		
		System.out.println("");
		*/
		
		System.out.println("Sorted players by score: ");
		for (NameRank u : ranking.sortedUserList()) {
			System.out.println(u.toString());
		}
		System.out.println("");
		
		/*
		System.out.println("Delete all: ");
		for (LoginData d : userManager.findAll()) {
			if (d.getUserName().equals("10") || d.getUserName().equals("user10") || d.getUserName().equals("3")) {
				continue;
			}
			else {
				userManager.delete(d);
				System.out.println(d);
			}
		}
		*/
	
		// close the context
		//ctx.close();

	}

}
