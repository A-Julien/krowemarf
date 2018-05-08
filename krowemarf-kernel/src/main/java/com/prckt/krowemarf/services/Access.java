package com.prckt.krowemarf.services;

import com.prckt.krowemarf.services.UserManagerServices.User;

public class Access {
	
	private Users user;
	private String right;
	
	public Access(Users user, String right) {
		
		boolean known = false;
		this.user = user;
		for(Right r : Right.values()) {
			if(r.toString().equals(right)) {
				this.right = right;
				known = true;
			}
		}
		if(!known) {
			this.right = null;
			throw new Exeption("Mauvais droit d'utilisateur!");
		}
	}
	
	public Users getUser() {
		return this.user;
	}
	
	public String getRight() {
		return this.right;
	}
	
}
