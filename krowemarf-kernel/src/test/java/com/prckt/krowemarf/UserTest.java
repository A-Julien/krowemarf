package com.prckt.krowemarf;

import com.prckt.krowemarf.services.DbConnectionManager;
import com.prckt.krowemarf.services.SQLRequest;
import com.prckt.krowemarf.services.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserTest {

    public static void main(String[] args) throws SQLException {

        /**
         * Création d'un utilisateur avec insertion dans la base de données
         */
        //User user = new User("Seb", "mdp");


        /**
         * Affiche l'interface de connexion en ligne de commandes
         */
        User.interfaceConnexion();


    }
}

