package TestCases;

import ORTE.GlobalMgr;
import ORTEExceptions.DbNameEmptyException;
import ORTEExceptions.StepFileNotNullException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static CommonUtil.FileReaderWithEncoding.readJsonFiles;
import static CommonUtil.GeneralFunc.ifEmpty;

/**
 * Created by JJMM on 2017/1/23.
 */
public class TestProject extends TestSuite
{
    public TestProject(final File projectName) throws StepFileNotNullException,FileNotFoundException
    {
        super(projectName);
        dbProperties = new TestProject.DbProperties();
        jsonObject = new JSONObject();
    }

    private JSONObject jsonObject;
    private final TestProject.DbProperties dbProperties;

    public void importPropertyFile(final File jsonPropertyFile) throws IOException,ParseException,URISyntaxException
    {
        readJsonFiles(jsonPropertyFile);
    }


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
