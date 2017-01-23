package ORTE;//NOPMD

import java.io.*;
import java.net.URISyntaxException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import static CommonUtil.FileReaderWithEncoding.readJsonFiles;
import static CommonUtil.GeneralFunc.ifEmpty;



import ORTEExceptions.DbNameEmptyException;

/**
 * Created by DT173 on 2016/12/29.
 */
public final class GlobalMgr
{
    private String workingDirectory;
//    private JSONObject  jsonObject;
//    private final DbProperties dbProperties;
    private static GlobalMgr ourInstance = new GlobalMgr();

    public static GlobalMgr getInstance()
    {
        return ourInstance;
    }
    private GlobalMgr()
    {
//        dbProperties = new DbProperties();
//        jsonObject = new JSONObject();
        workingDirectory = "";
    }


    public String getWorkingDirectory() throws URISyntaxException
    {
        if (ifEmpty(workingDirectory))
        {
            final String tempPath = GlobalMgr.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();//NOPMD

            final File tempFile = new File(tempPath);
            workingDirectory = tempFile.getPath();//NOPMD
        }
        return  workingDirectory;
    }





}
