package ORTE;//NOPMD

import java.io.*;
import java.net.URISyntaxException;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static CommonUtil.GeneralFunc.ifEmpty;
import static CommonUtil.FileEncoding.getFileEncode;
/**
 * Created by DT173 on 2016/12/29.
 */
public final class GlobalMgr
{
    private String workingDirectory;
    private JSONObject  jsonObject;
    private final SimulatorProperties mySimulator;
    private static GlobalMgr ourInstance = new GlobalMgr();

    public static GlobalMgr getInstance()
    {
        return ourInstance;
    }
    private GlobalMgr()
    {
        mySimulator = new SimulatorProperties();
        jsonObject = new JSONObject();
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



    public void importPropertyFile(final File jsonPropertyFile) throws IOException,ParseException,URISyntaxException
    {

        final JSONParser parser = new JSONParser();
        final String localJsonFile = getWorkingDirectory()+"\\project.json";
        final String fileEncode = getFileEncode(new File(localJsonFile));
        final FileInputStream is = new FileInputStream(localJsonFile);//NOPMD
        final InputStreamReader isr = new InputStreamReader(is, fileEncode);
        final BufferedReader buffReader = new BufferedReader(isr);
        jsonObject = (JSONObject)(parser.parse(buffReader));//NOPMD
        updateSimulatorProperties();
    }



    public Object getProjectFileProperty(final String propertyName)
    {
        return jsonObject.get(propertyName);
    }

    public String getSimulatorJdbcDriver()
    {
        return mySimulator.getJdbcDriver(false);
    }

    public String getSimulatorJdbcURL()
    {
        return mySimulator.getJdbcURL(false);
    }

    public String getSimulatorJdbcUser()
    {
        return mySimulator.getJdbcUser(false);
    }

    public String getSimulatorJdbcPassword()
    {
        return mySimulator.getJdbcPassword(false);
    }

    private void updateSimulatorProperties()
    {
        // Update all the simulator functions.
        mySimulator.getJdbcDriver(true);
        mySimulator.getJdbcPassword(true);
        mySimulator.getJdbcURL(true);
        mySimulator.getJdbcUser(true);
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



        public String getJdbcDriver(final boolean needUpdate)
        {
            if(needUpdate)//NOPMD
                jdbcDriver =(String)getProjectFileProperty(jdbcDriverStr);
            return jdbcDriver;
        }

        public String getJdbcURL(final boolean needUpdate)
        {
            if(needUpdate)//NOPMD
                jdbcURL = (String)getProjectFileProperty(jdbcURLStr);
            return jdbcURL;
        }

        public String getJdbcUser(final boolean needUpdate)
        {
            if(needUpdate)//NOPMD
                jdbcUser = (String)getProjectFileProperty(jdbcUserStr);
            return jdbcUser;
        }
        public String getJdbcPassword(final boolean needUpdate)
        {
            if(needUpdate)//NOPMD
                jdbcPassword = (String)getProjectFileProperty(jdbcPasswordStr);
            return jdbcPassword;
        }

    }

}
