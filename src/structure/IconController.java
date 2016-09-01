package structure;

import javax.swing.ImageIcon;

public class IconController {

    public static ImageIcon icFolder, icText, icResource, icArchive, icImage, icAudio, icVideo, icPicture, icPdf,
            icWord, icExcel, icScript, icBash, icUntyped;
    public static ImageIcon mDelete, mView, mRename, mTransferPC, mTransferSP;

    private static String iconDirectory = "src\\gfx\\";

    private static String[] fileMusic = {".mp3", ".wav", ".wave", ".flac", ".aac", ".ogg", ".mid"},
            fileVideo = {".mkv", ".avi", ".wmv", ".flv", ".mp4"},
            filePicture = {".png", ".bmp", ".jpg", ".jpeg", ".tif", ".ico"},
            fileScript = {".bat", ".vb", ".py", ".js", ".php", ".rb", ".au"},
            fileArchive = {".zip", ".rar", ".7z", ".gzip", ".tar", ".tgz", ".gz"};

    private static final int ARRAY_TYPE_MUSIC = 0,
            ARRAY_TYPE_VIDEO = 1,
            ARRAY_TYPE_PICTURE = 2,
            ARRAY_TYPE_SCRIPT = 3,
            ARRAY_TYPE_ARCHIVE = 4;

    public static void initIcons() {
        mDelete = new ImageIcon(iconDirectory + "delete.png");
        mView = new ImageIcon(iconDirectory + "page_go.png");
        mRename = new ImageIcon(iconDirectory + "page_edit.png");
        icFolder = new ImageIcon(iconDirectory + "folder.png");
        icText = new ImageIcon(iconDirectory + "text.png");
        icArchive = new ImageIcon(iconDirectory + "zip.png");
        icVideo = new ImageIcon(iconDirectory + "film.png");
        icPicture = new ImageIcon(iconDirectory + "picture.png");
        icImage = new ImageIcon(iconDirectory + "cd.png");
        icAudio = new ImageIcon(iconDirectory + "music.png");
        icPdf = new ImageIcon(iconDirectory + "page_white_acrobat.png");
        icWord = new ImageIcon(iconDirectory + "page_white_word.png");
        icExcel = new ImageIcon(iconDirectory + "page_white_excel.png");
        icScript = new ImageIcon(iconDirectory + "page_white_code.png");
        icBash = new ImageIcon(iconDirectory + "page_white_tux.png");
        icUntyped = new ImageIcon(iconDirectory + "page_white.png");
        mTransferPC = new ImageIcon(iconDirectory + "arrow_right.png");
        mTransferSP = new ImageIcon(iconDirectory + "arrow_left.png");
        //icResource = new ImageIcon("icResource.png");
    }

    public static ImageIcon getIconByExtension(String ext) {
        if (ext == null) {
            return icFolder;
        } else if (hasAnyFromArray(ext, ARRAY_TYPE_MUSIC)) {
            return icAudio;
        } else if (hasAnyFromArray(ext, ARRAY_TYPE_PICTURE)) {
            return icPicture;
        } else if (hasAnyFromArray(ext, ARRAY_TYPE_SCRIPT)) {
            return icScript;
        } else if (hasAnyFromArray(ext, ARRAY_TYPE_VIDEO)) {
            return icVideo;
        } else if (hasAnyFromArray(ext, ARRAY_TYPE_ARCHIVE)) {
            return icArchive;
        } else if (ext.equals(".doc") || ext.equals(".docx")) {
            return icWord;
        } else if (ext.equals(".pdf")) {
            return icPdf;
        } else if (ext.equals(".xls") || ext.equals(".xlsx")) {
            return icExcel;
        } else if (ext.equals(".sh")) {
            return icBash;
        } else if (ext.equals(".txt")) {
            return icText;
        }

        return icUntyped;
    }

    private static boolean hasAnyFromArray(String element, final int arrType) {
        String[] extns = null;
        switch (arrType) {
            case ARRAY_TYPE_MUSIC:
                extns = fileMusic;
                break;
            case ARRAY_TYPE_PICTURE:
                extns = filePicture;
                break;
            case ARRAY_TYPE_SCRIPT:
                extns = fileScript;
                break;
            case ARRAY_TYPE_VIDEO:
                extns = fileVideo;
                break;
            case ARRAY_TYPE_ARCHIVE:
                extns = fileArchive;
                break;
        }
        if (extns != null) {
            for (String s : extns) {
                if (s.equals(element)) {
                    return true;
                }
            }
        }

        return false;
    }
}
