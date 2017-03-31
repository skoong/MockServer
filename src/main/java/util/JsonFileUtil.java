package util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

/**
 * @author fkrauthan
 */
public final class JsonFileUtil {

    public static String loadFile(String fileName) {

        URL path = JsonFileUtil.class.getClassLoader().getResource(fileName);

        try {
            return IOUtils.toString(path.openStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
