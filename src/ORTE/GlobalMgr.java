package ORTE;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static CommonUtil.GeneralFunc.notEmpty;
import static CommonUtil.FileEncoding.getFileEncode;
/**
 * Created by DT173 on 2016/12/29.
 */
public class GlobalMgr
{
    private String workingDirectory;
    private JSONObject  jsonObject;
    private SimulatorProperties mySimulator;
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
            String localJsonFile = getWorkingDirectory()+"\\project.json";
            String fileEncode = getFileEncode(new File(localJsonFile));
            FileInputStream is = new FileInputStream(localJsonFile);
            InputStreamReader isr = new InputStreamReader(is, fileEncode);
            BufferedReader buffReader = new BufferedReader(isr);
            jsonObject = (JSONObject)(parser.parse(buffReader));
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        mySimulator = new SimulatorProperties();

    }


    public String getSimulatorJdbcDriver()
    {
        return mySimulator.getJdbcDriver();
    }

    public String getSimulatorJdbcURL()
    {
        return mySimulator.getJdbcURL();
    }
    public String getSimulatorJdbcUser()
    {
        return mySimulator.getJdbcUser();
    }
    public String getSimulatorJdbcPassword()
    {
        return mySimulator.getJdbcPassword();
    }


    private class SimulatorProperties
    {
        private String jdbcDriver;
        private String jdbcURL;
        private String jdbcUser;
        private String jdbcPassword;

        private final String jdbcDriverStr;
        private final String jdbcURLStr;
        private final String jdbcUserStr;
        private final String jdbcPasswordStr;

        public SimulatorProperties()
        {
            jdbcDriver = "";
            jdbcURL = "";
            jdbcUser = "";
            jdbcPassword = "";
            jdbcDriverStr = "simulator.jdbc.driver";
            jdbcURLStr = "simulator.jdbc.jdbcUrl";
            jdbcUserStr = "root";
            jdbcPasswordStr = "Elekchina123!";

        }



        public String getJdbcDriver()
        {
            return getProperties(jdbcDriver,jdbcDriverStr);
        }

        public String getJdbcURL()
        {
            return getProperties(jdbcURL,jdbcURLStr);
        }

        public String getJdbcUser()
        {
            return getProperties(jdbcUser,jdbcUserStr);
        }
        public String getJdbcPassword()
        {
            return getProperties(jdbcPassword,jdbcPasswordStr);
        }


        private String getProperties(String outPut,String inputName)
        {
            if(outPut.equals(""))
            {
                outPut = (String)jsonObject.get(inputName);
            }
            return outPut;
        }


    }

}
