package _Services;

import java.util.LinkedList;
import _Components.Component;

public class ComponentManager {
	LinkedList<Component> ComponentTab = null;
	
//////////////////////////String ? adresse
	/**
	 * Initializes a newly created ComponentManager object so that it represents the manager of different components.
	 * @param port
	 * @param adresse
	 */
	public ComponentManager(int port, String adresse) {
		try {
			this.ComponentTab = new LinkedList<Component>();
		} finally {	}
	}
	
	/**
	 * Check if a component named by _named exists in the list
	 * @param _name
	 * @return the component named by _name
	 * @return null if the component doesn't exists in the list
	 */
	public Component getComponentByName(String _name) {
		for(Component _component: this.ComponentTab) {
			if(_component.name.equals(_name)) { return _component; }
		}
		
		return null;
	}
}
