package structure;

import adb.Logger;
import java.io.File;
import java.io.IOException;
import java.awt.List;
import org.ini4j.Wini;

public class Config {

    private static final String INI_FILENAME = "settings.ini";
    private static final String SECTION_DEVICE = "device",
            SECTION_OPTIONS = "options";

    private static Wini iniFile;

    private static boolean startAsRoot = false, showHiddenFiles = true;
    private static String saveLocation = null;

    public static void init() throws IOException {
        iniFile = new Wini(new File(INI_FILENAME));
        startAsRoot = iniFile.get(SECTION_DEVICE, "startAsRoot", Boolean.class);
        showHiddenFiles = iniFile.get(SECTION_DEVICE, "showHiddenFiles", Boolean.class);

        saveLocation = iniFile.get(SECTION_OPTIONS, "saveLocation", String.class);

        Logger lg = new Logger(new List());

        IconController.initIcons();
    }

    public static boolean showHiddenFiles() {
        return showHiddenFiles;
    }

    public static boolean startAsRoot() {
        return startAsRoot;
    }

    public static String saveLocation() {
        return saveLocation;
    }
}
