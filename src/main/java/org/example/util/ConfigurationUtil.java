package org.example.util;

import org.example.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration utility. Allows to get configuration properties from the
 * default configuration file
 *
 * @author Boris Jmailov
 */
public class ConfigurationUtil {

    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";
    private static final Properties configuration = new Properties();
    /**
     * Hides default constructor
     */
    public ConfigurationUtil() {
    }

    private static Properties getConfiguration() throws IOException {
        if(configuration.isEmpty()){
            loadConfiguration();
        }
        return configuration;
    }

    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     * @throws IOException In case of the configuration file read failure
     */
    private static void loadConfiguration() throws IOException{
        File nf;
        if (System.getProperty(Constants.PROPERTIES_PATH) != null) {
            nf = new File(System.getProperty(Constants.PROPERTIES_PATH));
        } else {
            nf = new File(DEFAULT_CONFIG_PATH);
        }
        try (InputStream in = new FileInputStream(nf)) {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    /**
     * Gets configuration entry value
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public static String getConfigurationEntry(String key) throws IOException{
        return getConfiguration().getProperty(key);
    }

}
