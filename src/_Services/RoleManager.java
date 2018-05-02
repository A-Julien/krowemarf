package _Services;
import java.util.ArrayList;

public class RoleManager {
    ArrayList<Role> roleList;
    
    
    /**
     * Initializes a newly created RoleManager object so that it represents the manager of different roles. It recovers all roles in a DataBase.
     */
    public RoleManager() {
        this.roleList = new ArrayList<Role>();


        //  Importer la liste de la BD
    }

    /**
     * Role's adding to the list
     * @param role
     */
    public void addRole(Role role){ this.roleList.add(role); }

    /**
     * Role's removing from the list
     * @param role
     */
    public void remove(Role role){ this.roleList.remove(role); }

    /**
     * Access verifying
     * @param user
     */
    public void getAccess(User user){
    	
    }
}



