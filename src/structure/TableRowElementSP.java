package structure;

import javax.swing.ImageIcon;

public class TableRowElementSP {

    public String filename, ext, owner, group, attr, date;

    public String size;
    public ImageIcon Icon;

    public TableRowElementSP(String fn, String ext, String on, String gr, String sz, String date, String attr) {
        filename = fn;
        this.ext = ext;
        owner = on;
        group = gr;
        size = sz;
        this.date = date;
        this.attr = attr;

        Icon = IconController.getIconByExtension(ext);
    }
}
