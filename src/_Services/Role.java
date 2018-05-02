package _Services;

public class Role {
    public int level;
    public String nom;
    
    /**
     * Constructor of a Role _name with a priority _level
     * @param _level
     * @param _name
     */
    public Role(int _level, String _name) {
        this.level = _level;
        this.nom = _name;
    }
}