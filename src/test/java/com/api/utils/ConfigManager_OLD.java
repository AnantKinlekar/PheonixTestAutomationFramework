package com.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager_OLD {
    private static Properties prop = new Properties();

    static{
        FileReader fileReader = null;
        File configFile = null;
        try{
            configFile = new File(System.getProperty("user.dir") + File.separator +"src" + File.separator + "test" + File.separator + "resources" + File.separator + "config"  + File.separator + "config.properties");
            fileReader = new FileReader(configFile);
            prop.load(fileReader);
        }catch (NullPointerException e){
            throw new NullPointerException();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
