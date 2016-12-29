package ORTE;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static CommonUtil.GeneralFunc.notEmpty;

/**
 * Created by DT173 on 2016/12/29.
 */
public class GlobalMgr
{
    private String workingDirectory;
    private JSONObject  jsonObject;

    private static GlobalMgr ourInstance = new GlobalMgr();

    public static GlobalMgr getInstance()
    {
        return ourInstance;
    }

    public Object getProjectFileProperty(String propertyName)
    {
        return jsonObject.get(propertyName);
    }

    public String getWorkingDirectory()
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



    private GlobalMgr()
    {
        JSONParser parser = new JSONParser();

        try
        {
            jsonObject = (JSONObject)(parser.parse(new FileReader(getWorkingDirectory()+"\\project.json")));
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }


    }
}
