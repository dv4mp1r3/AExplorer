package adb;

public class FileObj {

    public String date, rules, group, user, name, size;
    private boolean file = true;

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
