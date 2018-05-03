package com.prckt.krowemarf;

import com.prckt.krowemarf.services.DbConnectionManager;
import com.prckt.krowemarf.services.SQLRequest;
import com.prckt.krowemarf.services.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SQLTest {

    public static void main( String[] args ) throws SQLException {


        /**
         * Pour faire une requete :
         * 1) DbConnectionManager
         * 2) Connection
         * 3) Utiliser la classe SQLRequest
         * 4) DbConnectionManager.close();
         */
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect();

        /**
         * Exemple d'une requete SQL, renvoie une liste d'objet
         */
        List<List<Object>> list = SQLRequest.sqlToListObject(connexion, "SELECT * FROM user;", true);
        /**
         * Parcours de la liste recuperer par SQL
         */
        for(List<Object> list1 : list){
            System.out.println(list1);
        }

        /**
         * Exemple d'un insert
         */
        SQLRequest.insertOrUpdateOrDelete(connexion, "INSERT INTO `User`(`login`, `password`) VALUES ('Philippe','" + User.hash("mdp") + "')");



        dbConnectionManager.close(connexion);


    }
}
