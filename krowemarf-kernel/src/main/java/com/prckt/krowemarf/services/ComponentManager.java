package com.prckt.krowemarf.services;

import com.prckt.krowemarf.components.Component;

import javax.swing.*;
import java.rmi.Remote;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//TODO extend iterable
public class ComponentManager implements Remote {
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

    public HashMap<String, Component> getComponents() {
        return components;
    }

    public Iterator getIterator(){
        return this.components.entrySet().iterator();
    }


}
