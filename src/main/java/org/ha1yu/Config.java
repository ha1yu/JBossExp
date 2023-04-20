package org.ha1yu;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.io.File;
import java.io.IOException;

public class Config {
    private static PropertiesConfiguration propConfig;
    private static final Config CONFIG = new Config();
    private static boolean autoSave = true;

    private Config() {
    }

    public static Config getInstance(String propertiesFile) {
        init(propertiesFile);
        return CONFIG;
    }

    private static void init(String propertiesFile) {
        try {
            File f = new File(propertiesFile);
            if (!f.exists()) {
                f.createNewFile();
            }

            propConfig = new PropertiesConfiguration(propertiesFile);
            propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            propConfig.setAutoSave(autoSave);
        } catch (IOException | ConfigurationException var2) {
            var2.printStackTrace();
        }

    }

    public Object getValue(String key) {
        return propConfig.getProperty(key);
    }

    public int getIntValue(String key) {
        return propConfig.getInt(key);
    }

    public boolean getBooleanValue(String key) {
        return propConfig.getBoolean(key, false);
    }

    public String getStringValue(String key) {
        return propConfig.getString(key);
    }

    public void setProperty(String key, Object value) {
        propConfig.setProperty(key, value);
    }
}
