package ORTE;

/**
 * Created by DT173 on 2016/12/23.
 */


public final class Logger
{


    private String logFilePath;
    private static Logger instance;//NOPMD

    private Logger()
    {
        // Exists only to defeat instantiation.
        logFilePath= DirectoryMgr.getWorkingDirectory()+"\\ORTE_Logs";

    }

    public String getLogFilePath()
    {
        return logFilePath;
    }

    public synchronized static Logger getInstance()
    {
        if (instance == null)
        {
            instance = new Logger();
        }
        return instance;
    }


};
