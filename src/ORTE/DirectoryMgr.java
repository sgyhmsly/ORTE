package ORTE;
import java.io.File;
import java.net.URL;
/**
 * Created by DT173 on 2016/12/23.
 */
public class DirectoryMgr
{
    private DirectoryMgr(){;}
    public static String getWorkingDirectory()
    {
        try
        {
            String tempPath = DirectoryMgr.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            File tempFile = new File(tempPath);
            tempPath = tempFile.getPath();
            return  tempPath;
        }
        catch (java.net.URISyntaxException uriEx)
        {
            return "";
        }

    }
}
