package adb.actions;

import adb.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActionDeleteFile extends DataReceiverAction {


    String path, currentDir;

    public ActionDeleteFile(String currentDir, String path)
    {
        this.currentDir = currentDir;
        this.path = path;
    }

    @Override
    protected void beforeAction() {

    }

    @Override
    protected void afterAction() {
       ActionGetDirContent action = new ActionGetDirContent(currentDir);
       action.initFromAction(this);
       action.run();
    }

    @Override
    public Object doEvent() {
        deleteFile(path);
        return null;
    }

    protected void deleteFile(String path)
    {
        Process p;
        InputStream processIN = null;
        BufferedReader reader = null;

        try {
            initCommand("file " + path);
            p = new ProcessBuilder(args).start();
            processIN = p.getInputStream();
            reader = new BufferedReader(new InputStreamReader(processIN));
            String line = reader.readLine();
            Logger.writeToLog(path);
            if (line.endsWith("directory")) {
                initCommand("rm -r " + path);
            } else {
                initCommand("rm " + path);
            }
            Process process = new ProcessBuilder(args).start();
            process.waitFor();
        } catch (IOException e) {
            Logger.writeToLog(path);
            Logger.writeToLog(e);
        } catch (InterruptedException e) {
            Logger.writeToLog(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (processIN != null) {
                    processIN.close();
                }
            } catch (IOException e) {
                Logger.writeToLog(e);
            }
        }
    }
}
