package ORTE;//NOPMD

import java.io.*;
import java.net.URISyntaxException;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static CommonUtil.FileReaderWithEncoding.readFilesWithEncode;
import static CommonUtil.GeneralFunc.ifEmpty;
import static CommonUtil.FileEncoding.getFileEncode;


import ORTEExceptions.DbNameEmptyException;

/**
 * Created by DT173 on 2016/12/29.
 */
public final class GlobalMgr
{
    private String workingDirectory;
    private JSONObject  jsonObject;
    private final DbProperties dbProperties;
    private static GlobalMgr ourInstance = new GlobalMgr();

    public static GlobalMgr getInstance()
    {
        return ourInstance;
    }
    private GlobalMgr()
    {
        dbProperties = new DbProperties();
        jsonObject = new JSONObject();
        workingDirectory = "";
    }


    public String generateWorkingDirectory() throws URISyntaxException
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
        BufferedReader buffReader = readFilesWithEncode(jsonPropertyFile);
        jsonObject = (JSONObject)(parser.parse(buffReader));//NOPMD
    }

    public void switchDBString(String dbName)
    {
        dbProperties.switchDbString(dbName);
    }

    public Object getProjectFileProperty(final String propertyName)
    {
        return jsonObject.get(propertyName);
    }

    public String getJdbcDriver()
    {
        return dbProperties.getJdbcDriver();
    }

    public String getJdbcURL()
    {
        return dbProperties.getJdbcURL();
    }

    public String getJdbcUser()
    {
        return dbProperties.getJdbcUser();
    }

    public String getJdbcPassword()
    {
        return dbProperties.getJdbcPassword();
    }



    private class DbProperties
    {

        private String jdbcDriverStr;
        private String jdbcURLStr;
        private String jdbcUserStr;
        private String jdbcPasswordStr;

        public DbProperties()
        {
            jdbcDriverStr = "";
            jdbcUserStr = "";
            jdbcURLStr = "";
            jdbcPasswordStr = "";
        }

        public void switchDbString(String dbName)
        {
            if(ifEmpty(dbName))
                throw new DbNameEmptyException("Db name can not be empty");
            jdbcDriverStr = dbName+".jdbc.driver";
            jdbcURLStr = dbName+".jdbc.jdbcUrl";
            jdbcUserStr = dbName+".jdbc.user";
            jdbcPasswordStr = dbName+".jdbc.password";
        }

        private void validateDbStrings(String dbString,String dbStringType)
        {
            assert (!ifEmpty(dbString));
        }


        public String getJdbcDriver()
        {
            validateDbStrings(jdbcDriverStr,"JDBC Driver String");
            return (String)getProjectFileProperty(jdbcDriverStr);
        }

        public String getJdbcURL()
        {
            validateDbStrings(jdbcURLStr,"JDBC URL String");
            return (String)getProjectFileProperty(jdbcURLStr);
        }

        public String getJdbcUser()
        {
            validateDbStrings(jdbcUserStr,"JDBC User String");
            return (String)getProjectFileProperty(jdbcUserStr);
        }
        public String getJdbcPassword()
        {
            validateDbStrings(jdbcPasswordStr,"JDBC Password String");
            return (String)getProjectFileProperty(jdbcPasswordStr);
        }

    }

}
