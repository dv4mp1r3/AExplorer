package adb;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextArea;
import structure.Config;
import structure.ModelPackage;
import structure.ModelSPFolders;
import structure.TableRowElementPackage;
import structure.TableRowElementSP;

public class DataReciever {

    public static String selectedDevice = "";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    private File saveLocation;

    public static String adbPath = "adb";

    public DataReciever() {
        saveLocation = new File(Config.saveLocation());
        // if OS is Windows
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            adbPath = System.getProperty("user.dir")+"\\adb\\adb.exe";
        }
        else // nix-based system
        {
            String customAdbPath = Config.adbPath();
            // custom adb path is defined in settings.ini
            if (customAdbPath.length() > 0)
            {
                adbPath = customAdbPath;
            }
        }

        BufferedReader reader = null;
        InputStream processIn = null;
        try {
            // Иногда процесс не завершается
            // Например, когда выводится сообщение об ошибке перезапуска с правами рута
            // В таких случаях надо грохнуть процесс и сделать рестарт adb           
            if (Config.startAsRoot()) {
                new ProcessBuilder(adbPath, "root").start();
            }

            Process process = new ProcessBuilder(adbPath, "version").start();
            processIn = process.getInputStream();
            reader = new BufferedReader(new InputStreamReader(processIn));
            Logger.writeToLog(reader.readLine());
        } catch (IOException e) {
            Logger.writeToLog(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (processIn != null) {
                    processIn.close();
                }
            } catch (IOException e) {
                Logger.writeToLog(e);
            }
        }

    }

    public ArrayList<String> getDevices(boolean log) {
        ArrayList<String> ret = new ArrayList<String>();
        InputStream processIN = null;
        BufferedReader br = null;
        Process process = null;
        try {
            process = new ProcessBuilder(adbPath, "devices").start();
            processIN = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(processIN));

            br.readLine();
            String line;
            while ((line = br.readLine()) != null && line.length() > 0) {
                String[] split = line.split("\t");
                if (split.length >= 1) {
                    ret.add(split[0]);
                }

            }
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
        if (log) {
            Logger.writeToLog(ret.size() + LanguageStrings.getProperty("devicesLog"));
        }
        return ret;
    }

    public static String executeCommand(String[] command) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(command).start();
        // Какая-то хрень при получении списка пакетов
        // Процесс чего-то ждет
        // Приходится ставить таймер на ожидание
        if (command[command.length - 1].equals("packages") && command.length == 7) {
            synchronized (process) {
                process.wait(2000);
            }
        } else {
            process.waitFor();
        }

        String result = "";
        String line;
        BufferedReader shellBR = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while (shellBR.ready() && (line = shellBR.readLine()) != null) {
            if (line.length() == 0) {
                continue;
            }
            result += line;
            result += "\r\n";
        }

        return result;

    }

    public void connectDevice(final String ip) {
        if (ip != null && ip.length() > 0) {
            try {
                new ProcessBuilder(adbPath, "connect", ip).start();
                Logger.writeToLog(ip + LanguageStrings.getProperty("deviceConnectedLog"));
            } catch (IOException e) {
                Logger.writeToLog(e);
            }
        }
    }

    public boolean renameFile(String oldValue, String newValue) {
        String moveString = "mv '" + oldValue + "' " + "'" + newValue + "'";
        String[] args = {adbPath, "-s", selectedDevice, "shell", moveString};
        try {
            String result = executeCommand(args);

            if (result.toLowerCase().indexOf("failed on") == 0) {
                return false;
            }

        } catch (IOException ex) {
            Logger.writeToLog(ex);
            return false;
        } catch (InterruptedException ex) {
            Logger.writeToLog(ex);
            return false;
        }
        return true;

    }

    public ModelSPFolders getDirContent(String dir) throws Exception {
        if (selectedDevice != null && selectedDevice.length() <= 0) {
            return null;
        }

        ArrayList<FileObj> ret = new ArrayList<FileObj>();
        ModelSPFolders mf;
        Process process;
        BufferedReader br = null;
        InputStream processIN = null;
        try {
            String cmd = "ls -l '" + dir + '\'';
            //List<String> ls = Arrays.asList(adbPath,"-s", selectedDevice, "shell", "ls", "-l", dir);

            process = new ProcessBuilder(adbPath, "-s", selectedDevice, "shell", cmd).start();

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
                    throw new Exception("Permission (read dir) danied in " + dir);
                }

                for (String errorStr : badLines) {
                    int badStringIndex = line.indexOf(errorStr);
                    if ((lineCount == 0 && badStringIndex > 0) && line.length() - errorStr.length() == badStringIndex) {
                        return null;
                    }
                }

                lineCount++;
                FileObj fo = new FileObj(line);               
                ret.add(fo);
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

            return mf;

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

    public void setSelectedDevice(String newDevice) {
        DataReciever.selectedDevice = newDevice;
    }
    
    public File pullFile(String path, String dPath) {
        try {
            String dest = saveLocation.getAbsolutePath();
            if (!saveLocation.exists()) {
                saveLocation.mkdirs();
            }
            String[] splits = path.split("/");
            if (path.endsWith("/")) {
                String interrimsPath = "";
                for (int i = 0; i < splits.length - 1; i++) {
                    interrimsPath += splits[i] + '/';
                }

                path = interrimsPath + splits[splits.length - 1];
            }
            Logger.writeToLog("Pulling file "+path+" -------> "+dPath);
            Process p = new ProcessBuilder(adbPath, "-s", selectedDevice, "pull", path, dPath).start();
            p.waitFor();
            Logger.writeToLog("done");
            //Logger.writeToLog(path + LanguageStrings.getProperty("pullFromToLog") + dPath);

            return new File(dPath + '\\' + splits[splits.length - 1]);
        } catch (IOException e) {
            Logger.writeToLog("PullFile: can't load file " + path);
            Logger.writeToLog(e);
            return null;
        } catch (InterruptedException e) {
            Logger.writeToLog("PullFile: can't build process");
            Logger.writeToLog(e);
            return null;
        }
    }

    public void pushFile(String source, String destination) {
        try {
            Logger.writeToLog("Pushing file "+source+" -------> "+destination);
            Process process = new ProcessBuilder(adbPath, "-s", selectedDevice, "push", source, destination).start();
            process.waitFor();
            Logger.writeToLog("done");
            //Logger.writeToLog(source + LanguageStrings.getProperty("pushFailedLog") + destination);
        } catch (IOException e) {
            Logger.writeToLog("PushFile: can't load file " + source);
            Logger.writeToLog(e);
        } catch (InterruptedException e) {
            Logger.writeToLog("PushFile: can't load file " + source);
            Logger.writeToLog(e);
        }
    }

    public void deleteFile(String path) {
        Process p;
        InputStream processIN = null;
        BufferedReader reader = null;
        try {
            p = new ProcessBuilder(adbPath, "-s", selectedDevice, "ls", path).start();
            processIN = p.getInputStream();
            reader = new BufferedReader(new InputStreamReader(processIN));
            String line = reader.readLine();
            if (line == null) {
                Logger.writeToLog(path);
                Process process = new ProcessBuilder(adbPath, "-s", selectedDevice, "shell", "rm", path).start();
                process.waitFor();

            } else {
                reader.readLine();
                ArrayList<String> filesToDelete = new ArrayList<String>();

                while ((line = reader.readLine()) != null) {
                    String[] splits = line.split(" ");
                    filesToDelete.add(path + '/' + splits[3]);
                }
                if (filesToDelete.isEmpty()) {
                    Logger.writeToLog(path);
                    Process process = new ProcessBuilder(adbPath, "-s", selectedDevice, "shell", "rmdir", path).start();
                    process.waitFor();
                } else {
                    for (String toDel : filesToDelete) {
                        deleteFile(toDel);
                    }
                    deleteFile(path);
                }
            }
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

    public String getSaveLocation() {
        if (saveLocation != null) {
            return saveLocation.getAbsolutePath();
        } else {
            return System.getProperty("user.dir") + "\\pull";
        }
    }

    private static String getFileName(String filename) {
        int i = filename.lastIndexOf(".");
        if (i == -1) {
            return filename;
        }
        return filename.substring(0, i);
    }

    private static String getFileExtension(String filename) {
        int i = filename.lastIndexOf(".");
        if (i == -1) {
            return "";
        }
        return filename.substring(i);
    }

    public String makeScreenshot() throws IOException, InterruptedException {
        Date dt = new Date();
        String resultPath = "/sdcard/screen.png";
        String[] cmd1 = {adbPath, "shell", "screencap", "-p", resultPath};
        executeCommand(cmd1);
        String dest = dt.toString().replaceAll(" ", "_") + ".png";
        dest = dest.replaceAll(":", "-");
        pullFile(resultPath, dest);
        String[] cmd2 = {adbPath, "shell", "rm", resultPath};
        executeCommand(cmd2);

        return dt.toString();
    }

    public static ModelPackage getPackages() throws IOException, InterruptedException {
        String[] args = {adbPath, "-s", selectedDevice, "shell", "pm", "list", "packages"};
        String res = executeCommand(args);
        String[] packages = res.split("\r\npackage:");

        packages[0] = packages[0].substring(packages[0].indexOf(':') + 1);
        packages[packages.length - 1] = packages[packages.length - 1].replace("\r\n", "");

        ModelPackage mp = new ModelPackage(packages.length);
        for (String _package : packages) {
            mp.setRow(new TableRowElementPackage(_package));
        }

        return mp;
    }

}
