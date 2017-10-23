package adb;

import java.util.ArrayList;

public class FileObj {

    public String date, rules, group, user, name, size;
    private boolean isFile = true;
    private boolean isSymlink = false;

    public FileObj() {}
    
    public FileObj(String line) throws Exception
    {
        ArrayList<String> split = formatLine(line);
        if (split.size() == 2)
        {
            throw new Exception("wrong string");
        }
        int paramNum = 0;

        String filename = "", date = "";
        
        for (String str : split) {
           
            // обработка имени файла
            if (paramNum >= 7)
            {
                // каталог
                if (str.endsWith("/"))
                {
                    filename += str.replace("/", "");
                    this.isFile = false;
                }
                // ссылка
                else if (str.endsWith("@"))
                {
                    filename += str.replace("@", "");
                    this.isFile = false;
                }
                // разделитель 
                else if (str.equals("->") || this.isSymlink)
                {
                    this.isFile = false;
                    this.isSymlink = true;
                    filename += " " + str + " ";
                }
                else
                {
                    filename += str;
                }
                paramNum++;
                continue;
            }
            
            switch (paramNum) {
                case 0:
                    this.setRules(str);
                    break;
                case 2:
                    this.setUser(str);
                    break;
                case 3:
                    this.setGroup(str);
                    break;
                case 4:
                    this.setSize(str);
                    break;
                case 5:
                case 6: 
                        date += ' ' + str;
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
        isFile = f;
    }

    public boolean isFile() {
        return isFile;
    }

}
