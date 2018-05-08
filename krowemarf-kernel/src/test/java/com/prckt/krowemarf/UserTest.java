package com.prckt.krowemarf;

import java.sql.SQLException;

public class UserTest {

    public static void main(String[] args) throws SQLException {

        /**
         * Création d'un utilisateur avec insertion dans la base de données
         */
        User user = new User("Seb", "mdp");


        /**
         * Affiche l'interface de connexion en ligne de commandes
         */
        //User.interfaceConnexion();


    }
}

