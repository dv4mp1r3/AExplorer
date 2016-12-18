package adb;

import java.util.ArrayList;

public class FileObj {

    public String date, rules, group, user, name, size;
    private boolean file = true;

    public FileObj() {}
    
    public FileObj(String line)
    {
        ArrayList<String> split = formatLine(line);
        int paramNum = 0, partOfFilename = 0, controlNumber = -1;
        String filename = "", date = "";
        boolean devOutput = false;
        
        for (String str : split) {
            if (devOutput && paramNum >= 7) {
                filename += str;
            }

            if (paramNum == controlNumber) {
                if (partOfFilename != 0) {
                    filename += ' ';
                }
                filename += str;
                partOfFilename++;
                continue;
            }
            switch (paramNum) {
                case 0:
                    this.setRules(str);
                    break;
                case 1:
                    this.setUser(str);
                    break;
                case 2:
                    this.setGroup(str);
                    break;
                case 3:
                    if (str.indexOf(',') > 0) {
                        devOutput = true;
                        this.size = str;
                        break;
                    }
                    // если не удалось спарсить число из параметра
                    // значит устанавливаем параметр как дату
                    // и перед нами файл (размер идет раньше даты)
                    try {
                        Long.parseLong(str);
                        this.setSize(str);
                        controlNumber = 6;
                    } catch (NumberFormatException ex) {
                        this.setFileIsFile(false);
                        this.setSize(null);
                        controlNumber = 5;
                        date = str;
                        //Logger.writeToLog(ex);
                    }
                    break;
                case 4:
                    if (devOutput) {
                        this.size += str;
                        break;
                    }
                    if (!this.isFile()) {
                        date += ' ' + str;
                    } else {
                        date = str;
                    }
                    break;
                case 5: //date
                    if (devOutput) {
                        date = str;
                        break;
                    }
                    if (!this.isFile()) {
                        filename = str;
                    } else {
                        date += ' ' + str;
                    }
                    break;
                case 6:
                    if (devOutput) {
                        date += str;
                        break;
                    }
                    if (!this.isFile()) {
                        filename = str;
                    }
                    break;
            }
            paramNum++;
        }
        this.setName(filename);
        this.setDate(date);
    }
    
    private ArrayList<String> formatLine(String line)
    {
        String[] tmp = line.split(" ");
        ArrayList<String> result = new ArrayList<String>();
        
        for (String param : tmp) {
            if (param.equals(""))
                continue;
            result.add(param);
        }
        
        return result;
    }

    //public FileObj() { file = true; }
    public void setDate(String d) {
        date = d;
    }

    public void setRules(String r) {
        rules = r;
    }

    public void setGroup(String g) {
        group = g;
    }

    public void setUser(String u) {
        user = u;
    }

    public void setName(String n) {
        name = n;
    }

    public void setSize(String s) {
        size = s;
    }

    public void setFileIsFile(boolean f) {
        file = f;
    }

    public boolean isFile() {
        return file;
    }

}
