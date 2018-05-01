package _Components;

import _Services.Right;
import _Services.Role;

public abstract class Component {
	public String name;
	
	
	public Component(String _name) {
		this.name = _name;
	}
	
	///////////////// plutôt sur ComponentManager
	public Component getComponent() {
		
		return null;
	}
	
	public Right getAccess(Role role) {
		
		
		return null;
		
	}
}
