package adb.actions;

import adb.Logger;

import java.io.File;
import java.io.IOException;

public class ActionPullFile extends DataReceiverAction {

    protected String source, destination;
    protected File saveLocation;

    protected String strLog;

    public ActionPullFile(File saveLocation, String source, String destination)
    {
        this.source = source;
        this.destination = destination;
        this.saveLocation = saveLocation;
    }

    @Override
    protected void beforeAction() {

    }

    @Override
    protected void afterAction() {
        synchronized (form)
        {
            form.refreshTablePC();
        }
    }

    @Override
    public Object doEvent() {
        strLog = "";
        try {
            String dest = saveLocation.getAbsolutePath();
            if (!saveLocation.exists()) {
                saveLocation.mkdirs();
            }
            String[] splits = source.split("/");
            if (source.endsWith("/")) {
                String interrimsPath = "";
                for (int i = 0; i < splits.length - 1; i++) {
                    interrimsPath += splits[i] + '/';
                }
                source = interrimsPath + splits[splits.length - 1];
            }
            strLog.concat("\nPulling file "+source+" -------> "+destination+"\n");
            String args[] = {adbPath, "-s", selectedDevice, "pull", source, destination};
            Process p = new ProcessBuilder(args).start();
            p.waitFor();
            strLog.concat("done");
            result = new File(destination + '\\' + splits[splits.length - 1]);
            return result;
        } catch (IOException | InterruptedException e) {
            strLog.concat("PullFile: can't load file " + source+"\n");
            Logger.writeToLog(strLog);
            Logger.writeToLog(e);
            return null;
        }
    }
}
