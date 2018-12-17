package adb.actions;

import adb.Logger;

import java.io.IOException;

public class ActionPushFile extends DataReceiverAction {

    protected String source, destination;

    public ActionPushFile(String source, String destination)
    {
        this.source = source;
        this.destination = destination;
    }

    @Override
    protected void beforeAction() {

    }

    @Override
    protected void afterAction() {

    }

    @Override
    public Object doEvent() {
        Boolean result = false;
        String loggerMessage = "";
        try {
            loggerMessage.concat("\nPushing file "+source+" -------> "+destination+"\n");
            String[] args = {adbPath, "-s", selectedDevice, "push", source, destination};
            Process process = new ProcessBuilder(args).start();
            process.waitFor();
            loggerMessage.concat("done\n");
            result = true;
        } catch (IOException | InterruptedException e) {
            loggerMessage.concat("PushFile: can't load file " + source+"\n");
            synchronized (Logger.class)
            {
                Logger.writeToLog(loggerMessage);
                Logger.writeToLog(e);
            }
        }
        return result;
    }
}
