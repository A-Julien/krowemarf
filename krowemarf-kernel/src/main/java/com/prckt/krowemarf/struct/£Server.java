package com.prckt.krowemarf.struct;

public class £Server {
    protected final String postTableName = "post_krowemarf";
    protected final String messengerTableName = "messenger_krowemarf";
    protected final String documentLibraryTableName = "documentLibrary_krowemarf";


    protected static String sqlTable(String tableName){
        if(tableName.equals("documentLibrary_krowemarf"))
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
