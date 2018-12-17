package adb.actions;

import adb.FileObj;
import adb.Logger;
import structure.Config;
import structure.ModelSPFolders;
import structure.TableRowElementSP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActionGetDirContent extends DataReceiverAction {

    protected String dir;

    public ActionGetDirContent(String dir)
    {
        this.dir = dir;
    }

    @Override
    protected void beforeAction() {
        if (selectedDevice != null && selectedDevice.length() <= 0) {
            return;
        }

        ArrayList<FileObj> ret = new ArrayList<FileObj>();
        ModelSPFolders mf;
        Process process;
        BufferedReader br = null;
        InputStream processIN = null;
        String cmd = "ls -lF " + dir;

        initCommand(cmd);
    }

    private static String getFileExtension(String filename) {
        int i = filename.lastIndexOf(".");
        if (i == -1) {
            return "";
        }
        return filename.substring(i);
    }

    private static String getFileName(String filename) {
        int i = filename.lastIndexOf(".");
        if (i == -1) {
            return filename;
        }
        return filename.substring(0, i);
    }

    @Override
    protected void afterAction() {
       synchronized (form)
       {
            form.updateTableModelSP((ModelSPFolders) result);
       }
    }

    @Override
    public Object doEvent() {
        BufferedReader br = null;
        InputStream processIN = null;
        try {
            ArrayList<FileObj> ret = new ArrayList<FileObj>();
            ModelSPFolders mf;


            Process process = new ProcessBuilder(args).start();

            processIN = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(processIN));
            if (!dir.equals("/")) {
                FileObj f = new FileObj();
                f.setName("..");
                f.setFileIsFile(false);
                ret.add(f);
            }
            String line;
            final String[] badLines = {"No such file or directory"};
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                if (line.length() == 0 || line.indexOf("Permission denied") > 0) {
                    continue;
                }

                if (line.equals("opendir failed, Permission denied")) {
                    Logger.writeToLog("Permission (read dir) danied in " + dir);
                    return null;
                }

                for (String errorStr : badLines) {
                    int badStringIndex = line.indexOf(errorStr);
                    if ((lineCount == 0 && badStringIndex > 0) && line.length() - errorStr.length() == badStringIndex) {
                        return null;
                    }
                }

                lineCount++;
                try
                {
                    FileObj fo = new FileObj(line);
                    ret.add(fo);
                }
                catch (Exception ex)
                {
                    if (!ex.getMessage().equals("wrong string"))
                    {
                        ex.printStackTrace();
                    }
                }
            }
            mf = new ModelSPFolders(ret.size());
            for (FileObj fo : ret) {
                if (!fo.isFile()) {
                    mf.setRow(new TableRowElementSP(fo.name,
                            null,
                            fo.user, fo.group, null, fo.date, fo.rules));
                }
            }
            for (FileObj fo : ret) {
                if (fo.isFile()) {
                    if (Config.showHiddenFiles() == false && fo.name.indexOf('.') == 0) {
                        continue;
                    }
                    mf.setRow(new TableRowElementSP(getFileName(fo.name),
                            getFileExtension(fo.name),
                            fo.user, fo.group, fo.size, fo.date, fo.rules));
                }

            }
            this.result = mf;
            return result;
        } catch (IOException e) {
            Logger.writeToLog(e);

        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (processIN != null) {
                    processIN.close();
                }
            } catch (IOException e) {
                Logger.writeToLog(e);
            }
        }
        return null;
    }
}
