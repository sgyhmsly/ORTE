package ORTE;


import java.io.File;
import java.net.URL;
import static CommonUtil.GeneralFunc.notEmpty;
/**
 * Created by DT173 on 2016/12/23.
 */
public final class DirectoryMgr
{
    private static String workingDirectory = "";
    private DirectoryMgr(){;}
    public static String getWorkingDirectory()
    {
        try
        {
            if (notEmpty(workingDirectory))
            {
                final String tempPath = DirectoryMgr.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                final File tempFile = new File(tempPath);
                workingDirectory = tempFile.getPath();

            }

        }
        catch (java.net.URISyntaxException uriEx)
        {
            workingDirectory ="";
        }
        return  workingDirectory;

    }
}
