package com.prckt.krowemarf.struct.Server;

import com.prckt.krowemarf.components._Component;

public class £Server {

    /**
     * This method build sql request to create table for component type.
     *
     * @param tableName the name of the table
     * @return
     */
    protected static String sqlTable(String tableName){
        if(tableName.equals(_Component.documentLibraryTableName ))
            return "CREATE TABLE " + tableName + "(" +
                    "name VARCHAR(40),"+
                    "extension VARCHAR(10),"+
                    "size FLOAT,"+
                    "path VARCHAR(90),"+
                    "Composant_Name VARCHAR (40)," +
                    "serialized_object blob," +
                    "PRIMARY KEY(path, name))";

        return "CREATE TABLE " + tableName + "(" +
                "id MEDIUMINT NOT NULL AUTO_INCREMENT,"+
                "Composant_Name VARCHAR (40)," +
                "serialized_object blob," +
                "PRIMARY KEY(id))";
    }
}
