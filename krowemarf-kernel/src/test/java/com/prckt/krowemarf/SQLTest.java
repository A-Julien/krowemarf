package com.prckt.krowemarf;

import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SQLTest {

    public static void main( String[] args ) throws SQLException, RemoteException {


        /**
         * Pour faire une requete :
         * 1) DbConnectionManager
         * 2) Connection
         * 3) Utiliser la classe SQLRequest
         * 4) DbConnectionManager.close();
         */
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("test");

        /**
         * Exemple d'une requete SQL, renvoie une liste d'objet
         */
        List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT * FROM user;", true);
        /**
         * Parcours de la liste recuperer par SQL
         */
        for(List<Object> list1 : list){
            System.out.println(list1);
        }

        /**
         * Exemple d'un insert
         */
        //SQLRequest.insertOrUpdateOrDelete(connexion, "INSERT INTO `User`(`login`, `password`) VALUES ('Philippe','" + hash("mdp") + "')");



        dbConnectionManager.close(connexion);


    }
}
