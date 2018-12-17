package adb.actions;

import adb.Logger;
import adb.proccess.ProcessHelper;
import structure.Explorer;

import java.io.File;
import java.util.Date;

public class ActionMakeScreenshot extends DataReceiverAction {

    protected final String resultPath = "/sdcard/screen.png";

    protected  File saveLocation;

    protected String currentDir;

    public ActionMakeScreenshot(String currentDir, File saveLocation)
    {
        this.currentDir = currentDir;
        this.saveLocation = saveLocation;
    }

    @Override
    protected void beforeAction() {

    }

    @Override
    protected void afterAction() {
        initCommand("rm " + resultPath);
        ProcessHelper.executeCommand(args);
        synchronized (form)
        {
            form.refreshTablePC();
        }
        new ActionGetDirContent(currentDir).initFromAction(this).run();
    }

    @Override
    public Object doEvent() {
        Date dt = new Date();
        initCommand("screencap -p " + resultPath);
        String screenCapResult = ProcessHelper.executeCommand(args);
        if (screenCapResult.indexOf("not found") > 0)
        {
            Logger.writeToLog("Screencap not found on android device");
            return null;
        }
        //перенос скриншота на бб
        String dest = dt.toString().replaceAll(" ", "_") + ".png";
        dest = dest.replaceAll(":", "-");

        new ActionPullFile(saveLocation, resultPath, dest).initFromAction(this).run();

        return dt.toString();
    }

}
