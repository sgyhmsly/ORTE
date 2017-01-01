package ORTE;//NOPMD

import java.io.*;
import java.net.URISyntaxException;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static CommonUtil.FileReaderWithEncoding.readFilesWithEncode;
import static CommonUtil.FileReaderWithEncoding.readJsonFiles;
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
        readJsonFiles(jsonPropertyFile);
    }

//    public void switchDBString(String dbName)
//    {
//        dbProperties.switchDbString(dbName);
//    }

    public Object getProjectFileProperty(final String propertyName)
    {
        return jsonObject.get(propertyName);
    }

    public String getJdbcDriver(final String dbName)throws DbNameEmptyException
    {
        return dbProperties.getJdbcDriver(dbName);
    }

    public String getJdbcURL(final String dbName)throws DbNameEmptyException
    {
        return dbProperties.getJdbcURL(dbName);
    }

    public String getJdbcUser(final String dbName)throws DbNameEmptyException
    {
        return dbProperties.getJdbcUser(dbName);
    }

    public String getJdbcPassword(final String dbName)throws DbNameEmptyException
    {
        return dbProperties.getJdbcPassword(dbName);
    }



    private class DbProperties
    {

        private String jdbcDriverStr;
        private String jdbcURLStr;
        private String jdbcUserStr;
        private String jdbcPasswordStr;

        public DbProperties()
        {
            jdbcDriverStr = ".jdbc.driver";
            jdbcUserStr = ".jdbc.jdbcUrl";
            jdbcURLStr = ".jdbc.user";
            jdbcPasswordStr = ".jdbc.password";
        }

//        public void switchDbString(final String dbName)
//        {
//            if(ifEmpty(dbName))
//                throw new DbNameEmptyException("Db name can not be empty");
//            jdbcDriverStr = dbName+".jdbc.driver";
//            jdbcURLStr = dbName+".jdbc.jdbcUrl";
//            jdbcUserStr = dbName+".jdbc.user";
//            jdbcPasswordStr = dbName+".jdbc.password";
//        }
//
//        private void validateDbStrings(final String dbString,final String dbStringType)
//        {
//            if (ifEmpty(dbString))
//            {
//                throw new RuntimeException(dbStringType+" should not be empty");
//            }
//        }

        private void validateDbName(final String dbName)throws DbNameEmptyException
        {
            if(ifEmpty(dbName))
                throw new DbNameEmptyException("Db name should not be empty");
        }

        public String getJdbcDriver(final String dbName)throws DbNameEmptyException
        {
            validateDbName(dbName);
            final String queryString = dbName+jdbcDriverStr;
            return (String)getProjectFileProperty(queryString);
        }

        public String getJdbcURL(final String dbName)throws DbNameEmptyException
        {
            validateDbName(dbName);
            final String queryString = dbName+jdbcURLStr;
            return (String)getProjectFileProperty(queryString);
        }

        public String getJdbcUser(final String dbName)throws DbNameEmptyException
        {
            validateDbName(dbName);
            final String queryString = dbName+jdbcUserStr;
            return (String)getProjectFileProperty(queryString);
        }
        public String getJdbcPassword(final String dbName)throws DbNameEmptyException
        {
            validateDbName(dbName);
            final String queryString = dbName+jdbcPasswordStr;
            return (String)getProjectFileProperty(queryString);
        }

    }

}
