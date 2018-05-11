package com.prckt.krowemarf.services;

import com.prckt.krowemarf.services.UserManagerServices.User;

public class Access {

	private User user;
	private String right;
	
	public Access(User user, String right) {
		
		boolean known = false;

		for(Right r : Right.values()) {
			if(r.toString().equals(right)) {
                this.user = user;
				this.right = right;
				known = true;
			}
		}

		if(!known) {
		    System.out.println("Droit d'utilisateur inexistant !");
        }
	}
	
	public User getUser() {
		return this.user;
	}
	
	public String getRight() {
		return this.right;
	}
	
}
