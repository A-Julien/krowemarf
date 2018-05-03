package com.prckt.krowemarf;

import com.prckt.krowemarf.services.DbConnectionManager;
import com.prckt.krowemarf.services.SQLRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LoginTest {

    public static void main( String[] args ) throws SQLException {

        System.out.println("ESPACE DE CONNEXION UTILISATEUR :");

        boolean isAuth = false;
        String login = null, password = null;

        Scanner sc = new Scanner(System.in);

        while(!isAuth){
            System.out.println("Login : ");
            login = sc.next();
            System.out.println("Password : ");
            password = sc.next();
            password = hash(password);
            isAuth = isAuthenticated(login, password);
        }

        System.out.println("Bonjour : " + login);
    }

    public static boolean isAuthenticated(String login, String password){
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect();

        List<List<Object>> list = null;
        try {
            list = SQLRequest.sqlToListObject(connexion, "SELECT password FROM users WHERE login = '" + login + "'", false);
        } catch (SQLException e) {
            return false;
        }
        dbConnectionManager.close(connexion);


        if(password.equals(list.get(0).get(0).toString())){
            return true;
        }else {
            return false;
        }



    }

    public static String hash(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(str.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            str = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return str;
    }
}

