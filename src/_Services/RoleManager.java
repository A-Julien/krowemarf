package _Services;
import java.util.ArrayList;

public class RoleManager {
    ArrayList<Role> roleList;

    public RoleManager() {
        this.roleList = new ArrayList<Role>();


        //  Importer la liste de la BD
    }

    /**
    *   Role adding to the list
    **/
    public void addRole(Role role){ this.roleList.add(role); }

    /**
    *   Role removing from the list
    **/
    public void remove(Role role){ this.roleList.remove(role); }

    /**
    *   Vérifier l'accès pour un utilisateur
    **/
    public void getAccess(User user){
    	
    }


}



