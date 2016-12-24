package ORTE;

/**
 * Created by DT173 on 2016/12/23.
 */

// test github
public final class Logger
{
    private String logFilePath = "";
    private static Logger instance = null;
    private Logger()
    {
        // Exists only to defeat instantiation.
        logFilePath += DirectoryMgr.getWorkingDirectory()+"\\Logs";
    }
    public static Logger getInstance()
    {
        if (instance == null)
        {
            instance = new Logger();
        }
        return instance;
    }
};
