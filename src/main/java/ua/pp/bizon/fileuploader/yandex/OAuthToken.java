package ua.pp.bizon.fileuploader.yandex;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuthToken {

    private Properties knownTokens = new Properties();
    private Logger logger = LoggerFactory.getLogger(getClass());

    {
        try {
            knownTokens.load(new FileReader("filemanager.oauth.keys"));
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    private static OAuthToken token;

    public static String getTokenByUserName(String username) {
        if (token == null) {
            synchronized (OAuthToken.class) {
                if (token == null) {
                    token = new OAuthToken();
                }
            }
        }
        return token.knownTokens.getProperty(username);
    }

}
