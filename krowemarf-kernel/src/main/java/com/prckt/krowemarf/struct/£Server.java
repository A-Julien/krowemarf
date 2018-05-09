package com.prckt.krowemarf.struct;

public class £Server {
    protected final String postTableName = "post_krowemarf";
    protected final String messengerTableName = "messenger_krowemarf";

    protected static String sqlTable(String tableName){
        return "CREATE TABLE " + tableName + "(" +
                "id MEDIUMINT NOT NULL AUTO_INCREMENT,"+
                "Composant_Name VARCHAR (40)," +
                "serialized_message blob," +
                "PRIMARY KEY(id))";
    }
}
