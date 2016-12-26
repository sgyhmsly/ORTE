package ORTE;
import CommonUtil.GeneralFunc;

import java.io.File;
import java.net.URL;
import static CommonUtil.GeneralFunc.notEmpty;
/**
 * Created by DT173 on 2016/12/23.
 */
public class DirectoryMgr
{
    private static String workingDirectory = "";
    private DirectoryMgr(){;}
    public static String getWorkingDirectory()
    {
        try
        {
            if (GeneralFunc.notEmpty(workingDirectory))
            {
                String tempPath = DirectoryMgr.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                File tempFile = new File(tempPath);
                workingDirectory = tempFile.getPath();

            }
            return  workingDirectory;
        }
        catch (java.net.URISyntaxException uriEx)
        {
            return "";
        }

    }
}
