package com.prckt.krowemarf.services.ConfigManagerServices;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public abstract class ConfigManager {
    private static String pathConfigFile =
            "/Users/julien/Documents/MIAGE/Projet-Framework/krowemarf/krowemarf-kernel/src/main/java/com/prckt/krowemarf/services/config.json";


    public static String getConfig(String conf){
         JSONParser parser = new JSONParser();

        Object config = null;
        try {
            config = parser.parse(new FileReader(pathConfigFile));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonConfig = (JSONObject) config;

        assert jsonConfig != null;
        return (String) jsonConfig.get(conf);
    }
}
