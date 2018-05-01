package com.prckt.krowemarf;

import com.prckt.krowemarf.services.DbConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        System.out.println( "Hello World!" );
        DbConnectionManager mysql = new DbConnectionManager();
        String sql = "SELECT * FROM BIDULE";
        PreparedStatement statement = mysql.connect().prepareStatement(sql);
        System.out.println(statement);
    }
}
