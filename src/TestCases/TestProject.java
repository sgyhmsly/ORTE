package TestCases;

import ORTEExceptions.DbNameEmptyException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static CommonUtil.FileReaderWithEncoding.readJsonFiles;
import static CommonUtil.GeneralFunc.ifEmpty;

/**
 * Created by JJMM on 2017/1/23.
 */
public class TestProject implements IExcutable
{
    public TestProject(final File projectName) throws FileNotFoundException
    {
        if(projectName == null)
            throw new NullPointerException("Project file not be null");
        if(!projectName.exists())
            throw new FileNotFoundException("Step file not exist");
        projectFile = projectName;
        dbProperties = new TestProject.DbProperties();
        jsonObject = new JSONObject();
        testSuites = new ArrayList<>();
    }

    private JSONObject jsonObject;
    private final TestProject.DbProperties dbProperties;
    private final File projectFile;
    private final List<TestSuite> testSuites;

    public void addTestSuite(TestSuite testSuite)
    {
        if (testSuite!=null)
            testSuites.add(testSuite);
    }
    public void removeTestSuite(TestSuite testSuite)
    {
        testSuites.remove(testSuite);
    }

    public List<TestSuite> getTestSuites()
    {
        return Collections.unmodifiableList(testSuites);
    }

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

    @Override
    public void execute() throws Exception
    {
        for (TestSuite testSuite:testSuites)
        {
            testSuite.execute();
        }
    }
}
