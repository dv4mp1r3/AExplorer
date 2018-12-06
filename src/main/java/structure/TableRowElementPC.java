package structure;

import javax.swing.ImageIcon;

public class TableRowElementPC {

    public String f, e, d;
    public Long s;
    public ImageIcon icon;

    TableRowElementPC(String filename, String ext, Long size, String date) {
        f = filename;
        e = ext;
        d = date;
        s = size;

        icon = IconController.getIconByExtension(ext);
    }
}
