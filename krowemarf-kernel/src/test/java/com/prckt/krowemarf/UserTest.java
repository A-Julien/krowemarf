package com.prckt.krowemarf;

import com.prckt.krowemarf.services.UserManagerServices.User;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class UserTest {

    public static void main(String[] args) throws SQLException, RemoteException {

        /**
         * Création d'un utilisateur avec insertion dans la base de données
         */
        User user = new User("Seb");


        /**
         * Affiche l'interface de connexion en ligne de commandes
         */
        //User.interfaceConnexion();


    }
}

