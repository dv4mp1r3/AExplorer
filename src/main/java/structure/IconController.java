package structure;

import java.io.File;
import javax.swing.ImageIcon;
import gui.formExplorer;

public class IconController {

    public static ImageIcon icFolder, icText, icResource, icArchive, icImage, icAudio, icVideo, icPicture, icPdf,
            icWord, icExcel, icScript, icBash, icUntyped;
    public static ImageIcon mDelete, mView, mRename, mTransferPC, mTransferSP;

    //private static String fe.getClass().getResource( = "src"+File.separator+ "gfx" +File.separator;

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

    public static void initIcons(formExplorer fe) {
        mDelete = new ImageIcon(fe.getClass().getResource( "/gfx/delete.png"));
        mView = new ImageIcon(fe.getClass().getResource( "/gfx/page_go.png"));
        mRename = new ImageIcon(fe.getClass().getResource( "/gfx/page_edit.png"));
        icFolder = new ImageIcon(fe.getClass().getResource( "/gfx/folder.png"));
        icText = new ImageIcon(fe.getClass().getResource( "/gfx/text.png"));
        icArchive = new ImageIcon(fe.getClass().getResource( "/gfx/zip.png"));
        icVideo = new ImageIcon(fe.getClass().getResource( "/gfx/film.png"));
        icPicture = new ImageIcon(fe.getClass().getResource( "/gfx/picture.png"));
        icImage = new ImageIcon(fe.getClass().getResource( "/gfx/cd.png"));
        icAudio = new ImageIcon(fe.getClass().getResource( "/gfx/music.png"));
        icPdf = new ImageIcon(fe.getClass().getResource( "/gfx/page_white_acrobat.png"));
        icWord = new ImageIcon(fe.getClass().getResource( "/gfx/page_white_word.png"));
        icExcel = new ImageIcon(fe.getClass().getResource("/gfx/page_white_excel.png"));
        icScript = new ImageIcon(fe.getClass().getResource( "/gfx/page_white_code.png"));
        icBash = new ImageIcon(fe.getClass().getResource( "/gfx/page_white_tux.png"));
        icUntyped = new ImageIcon(fe.getClass().getResource( "/gfx/page_white.png"));
        mTransferPC = new ImageIcon(fe.getClass().getResource( "/gfx/arrow_right.png"));
        mTransferSP = new ImageIcon(fe.getClass().getResource( "/gfx/arrow_left.png"));
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
