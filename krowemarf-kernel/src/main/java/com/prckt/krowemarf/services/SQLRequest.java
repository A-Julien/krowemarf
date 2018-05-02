package com.prckt.krowemarf.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLRequest {

    /**
     * Execute une requete
     * @param connexion
     * @param query
     * @throws SQLException
     */
    public static void insertOrUpdateOrDelete(Connection connexion, String query) throws SQLException {
        Statement statement = connexion.createStatement();

        System.out.println("Requête : " + query);
        /* Exécution d'une requête */
        statement.executeUpdate(query);
    }

    /**
     * Execute une requete sql et return la réponse en une liste d'object.
     * @param connexion
     * @param query
     * @param withColumnNames
     * @return List<List<Object>>
     * @throws SQLException
     */
    public static List<List<Object>> sqlToList(Connection connexion, String query, boolean withColumnNames) throws SQLException {
        Statement statement = connexion.createStatement();

        System.out.println("Requête : " + query);
        /* Exécution d'une requête de lecture */
        ResultSet resultat = statement.executeQuery(query);

        // To contain all rows.
        List<List<Object>> list = new ArrayList<List<Object>>();
        ResultSetMetaData metaData = resultat.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        List<Object> columnNames = new ArrayList<Object>();

        // Get the column names
        if(withColumnNames){
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.add(metaData.getColumnLabel(column + 1));
            }
            list.add(columnNames);
        }

        while (resultat.next()) {
            List<Object> newRow = new ArrayList<Object>();

            for (int i = 1; i <= numberOfColumns; i++) {
                newRow.add(resultat.getObject(i));
            }

            list.add(newRow);
        }

        resultat.close();

        return list;
    }
}
