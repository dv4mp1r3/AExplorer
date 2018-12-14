package structure;

import adb.Logger;
import java.io.File;
import java.io.IOException;
import org.ini4j.Wini;
import gui.formExplorer;

public class Config {

    private static final String INI_FILENAME = "settings.ini";
    private static final String SECTION_DEVICE = "device",
            SECTION_OPTIONS = "options";

    private static Wini iniFile;

    private static boolean startAsRoot = false, showHiddenFiles = true;
    private static String saveLocation = null, adbPath = null;

    public static void init(formExplorer fe) throws IOException {
        iniFile = new Wini(new File(INI_FILENAME));
        startAsRoot = iniFile.get(SECTION_DEVICE, "startAsRoot", Boolean.class);
        showHiddenFiles = iniFile.get(SECTION_DEVICE, "showHiddenFiles", Boolean.class);

        saveLocation = iniFile.get(SECTION_OPTIONS, "saveLocation", String.class);
        adbPath = iniFile.get(SECTION_OPTIONS, "adbPath", String.class);

        Logger lg = new Logger();

        IconController.initIcons(fe);
    }

    public static boolean showHiddenFiles() {
        return showHiddenFiles;
    }

    public static boolean startAsRoot() {
        return startAsRoot;
    }

    public static String saveLocation() {
        return File.separator+saveLocation;
    }
    
    public static String adbPath() {
        return adbPath;
    }
}
