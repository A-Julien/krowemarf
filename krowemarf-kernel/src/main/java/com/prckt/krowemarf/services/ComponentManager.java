package com.prckt.krowemarf.services;

import com.prckt.krowemarf.components.Component;

import java.util.HashMap;


public class ComponentManager {
    private HashMap<String,Component> components;

    public ComponentManager() {
        this.components = new HashMap<>();
    }

    public void addComponent(Component c){
        this.components.put(c.name,c);
    }

    public void removeComponent(String name){
        this.components.remove(name);
    }

}
