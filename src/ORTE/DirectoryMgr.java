package ORTE;
import java.net.URL;
/**
 * Created by DT173 on 2016/12/23.
 */
public class DirectoryMgr
{
    private DirectoryMgr(){;}
    public static String getWorkingDirectory()
    {
        URL location = DirectoryMgr.class.getProtectionDomain().getCodeSource().getLocation();
        return location.getPath();
    }
}
