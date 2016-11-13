/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.io.File;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author divan
 */
public class Explorer 
{
    private String currentPath;
    public static int   DIRECTORY_ITEM_FILE   = 0,
                        DIRECTORY_ITEM_FOLDER = 1;
    private static int SHORTEST_PATH_LENGTH_WINDOWS = 3,
                       SHORTEST_PATH_LENGTH_LINUX   = 1;
    private static String   SEPARATOR_WINDOWS = "\\",
                            SEPARATOR_LINUX   = "/";
    ModelPC dPrev;
    public static String separator;
    int shortestPathLength;
    
    public Explorer(String root)
    {
        currentPath = root;
        
        if (System.getProperty("os.name").toLowerCase().contains("windows"))
        {
            shortestPathLength = SHORTEST_PATH_LENGTH_WINDOWS; 
            separator          = SEPARATOR_WINDOWS;
        }           
        else
        {
            shortestPathLength = SHORTEST_PATH_LENGTH_LINUX;
            separator          = SEPARATOR_LINUX;
        }       
    }
    
    public static boolean deleteDir(File dir) 
    { 
        if (dir.isDirectory()) 
        { 
            String[] children = dir.list(); 
            for (int i=0; i<children.length; i++)
            { 
                boolean success = deleteDir(new File(dir, children[i])); 
                if (!success)   
                    return false; 
            }     
            return dir.delete(); 
        }
        else
            return false;
    }
    
    public ModelPC setPath(String newPath) throws Exception
    {
        
        File f = new File(newPath);
        String dirs[] = f.list();
        if (dirs == null)
        {
            JOptionPane.showMessageDialog(null, "Can't to get directory content.\r\nMay be directory is protected.", "I/O Error", JOptionPane.ERROR_MESSAGE);
            return getUpperDirectory();
        }
        int count = f.list().length;
        boolean addDots = false;
        if (newPath.length() > shortestPathLength)
        { 
            count++;
            addDots = true;
        }
        ModelPC dlm = new ModelPC(count);
        if (addDots)
            dlm.setRow(new TableRowElementPC("..", null, null, ""));
        for (File file : f.listFiles())
            if (file.isDirectory())
                dlm.setRow(new TableRowElementPC(file.getName(), null, null, ""));

        for (File file : f.listFiles())
            if (file.isFile())
                dlm.setRow(new TableRowElementPC(getFileName(file.getName()), 
                        getFileExtension(file.getName()),
                        file.length(), 
                        new Date(file.lastModified()).toString()));
        currentPath = newPath;
        dPrev = dlm;
        return dlm;
    }
    public ModelPC setPath() throws Exception
    {
         return setPath(this.currentPath);
    }
    public String getPath()
    {
        return this.currentPath;
    }
    
    public int getType(String path)
    {
        String s = "";
        if (currentPath.length() > shortestPathLength)
            s = currentPath + separator + path;
        else
            s = currentPath + path;
        File f = new File(s);
        if (f.isDirectory())
        {
            currentPath = s;
            return this.DIRECTORY_ITEM_FOLDER;       
        }
            
        return this.DIRECTORY_ITEM_FILE;
    }
    
    public ModelPC getUpperDirectory() throws Exception
    {
        int tmp = currentPath.lastIndexOf(separator);
        if (tmp == currentPath.indexOf(separator))
        {
            currentPath = currentPath.substring(0, tmp + 1);
            return setPath();
        }
            //return dPrev;
        currentPath = currentPath.substring(0, tmp);
        return setPath();
    }
    
    private static String getFileName(String filename)
    {
        try
        {
            int i = filename.lastIndexOf(".");
            if (i == 0)
                return filename;
            return filename.substring(0,i);
        }
        catch(IndexOutOfBoundsException ex)
        {
            ex.printStackTrace();
            return filename;
        }
        
    }
    
    private static String getFileExtension(String filename)
    {
        try
        {
            int i = filename.lastIndexOf(".");
            if (i > 0)
                return filename.substring(i);
        }
        catch(IndexOutOfBoundsException ex)
        {
            ex.printStackTrace();
        }
        
        return "";
    }
}
