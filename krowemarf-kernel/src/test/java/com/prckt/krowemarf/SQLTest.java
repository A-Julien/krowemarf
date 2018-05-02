package com.prckt.krowemarf;

import com.prckt.krowemarf.services.DbConnectionManager;
import com.prckt.krowemarf.services.SQLRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLTest {

    public static void main( String[] args ) throws SQLException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect();


        /**
         * Exemple d'une requete SQL, renvoie une liste d'objet
         */
        List<List<Object>> list = SQLRequest.sqlToList(connexion, "SELECT * FROM users;", true);
        /**
         * Parcours de la liste recuperer par SQL
         */
        for(List<Object> list1 : list){
            System.out.println(list1);
        }

        /**
         * Exemple d'un insert
         */
        //SQLRequest.insertOrUpdateOrDelete(connexion, "INSERT INTO `users`(`login`, `password`, `permission`) VALUES ('Philippe','morrat',2)");



        dbConnectionManager.close(connexion);


    }
}
