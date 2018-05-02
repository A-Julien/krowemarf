package _Components;

import _Services.Right;
import _Services.User;

public abstract class Component {
	public String name;
	
	///////////////// plutôt sur ComponentManager
	public abstract Component getComponent();
	public abstract Right getAccess(User user);
}
