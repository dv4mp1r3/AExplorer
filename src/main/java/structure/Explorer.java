package structure;

import adb.Logger;
import java.io.File;
import java.util.Date;
import javax.swing.JOptionPane;

public class Explorer {

    private String currentPath;
    public static int 
            DIRECTORY_ITEM_FILE = 0,
            DIRECTORY_ITEM_FOLDER = 1;
    private static final int 
            SHORTEST_PATH_LENGTH_WINDOWS = 3,
            SHORTEST_PATH_LENGTH_LINUX = 1;
    ModelPC dPrev;
    int shortestPathLength;

    public Explorer(String root) {
        currentPath = root;

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            shortestPathLength = SHORTEST_PATH_LENGTH_WINDOWS;
        } else {
            shortestPathLength = SHORTEST_PATH_LENGTH_LINUX;
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else {
            return false;
        }
    }

    public ModelPC setPath(String newPath) throws Exception {

        File f = new File(newPath);
        String dirs[] = f.list();
        if (dirs == null) {
            JOptionPane.showMessageDialog(null, "Can't to get directory content.\r\nMay be directory is protected.", "I/O Error", JOptionPane.ERROR_MESSAGE);
            return getUpperDirectory();
        }
        int count = f.list().length;
        boolean addDots = false;
        if (newPath.length() > shortestPathLength) {
            count++;
            addDots = true;
        }
        ModelPC dlm = new ModelPC(count);
        if (addDots) {
            dlm.setRow(new TableRowElementPC("..", null, null, ""));
        }
        for (File file : f.listFiles()) {
            if (file.isDirectory()) {
                dlm.setRow(new TableRowElementPC(file.getName(), null, null, ""));
            }
        }

        for (File file : f.listFiles()) {
            if (file.isFile()) {
                dlm.setRow(new TableRowElementPC(getFileName(file.getName()),
                        getFileExtension(file.getName()),
                        file.length(),
                        new Date(file.lastModified()).toString()));
            }
        }
        currentPath = newPath;
        dPrev = dlm;
        return dlm;
    }

    public ModelPC setPath() throws Exception {
        return setPath(this.currentPath);
    }

    public String getPath() {
        return this.currentPath;
    }

    public int getType(String path) {
        String s = "";
        if (currentPath.length() > shortestPathLength) {
            s = currentPath + File.separator + path;
        } else {
            s = currentPath + path;
        }
        File f = new File(s);
        if (f.isDirectory()) {
            currentPath = s;
            return Explorer.DIRECTORY_ITEM_FOLDER;
        }

        return Explorer.DIRECTORY_ITEM_FILE;
    }

    public ModelPC getUpperDirectory() throws Exception {
        int tmp = currentPath.lastIndexOf(File.separator);
        if (tmp == currentPath.indexOf(File.separator)) {
            currentPath = currentPath.substring(0, tmp + 1);
            return setPath();
        }
        //return dPrev;
        currentPath = currentPath.substring(0, tmp);
        return setPath();
    }

    private static String getFileName(String filename) {
        int i = filename.lastIndexOf('.');
        try {
            if (i <= 0) {
                return filename;
            }
            return filename.substring(0, i);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            Logger.writeToLog("IndexOutOfBoundsException ("+filename+")");
            Logger.writeToLog(ex);
            return filename;
        }

    }

    private static String getFileExtension(String filename) {
        try {
            int i = filename.lastIndexOf('.');
            if (i > 0) {
                return filename.substring(i);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            Logger.writeToLog(ex);
        }

        return "";
    }
}
