package TestCases;//NOPMD

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import ORTE.GlobalMgr;
import org.apache.commons.io.IOUtils;
import ORTEExceptions.SqlPropertyFileException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Created by DT173 on 2016/12/29.
 */
public class MySqlStep extends AbstractStep
{

    private String dbName;
    private File stepFile;
    public MySqlStep(File stepFiles) throws FileNotFoundException,IOException,SQLException,SqlPropertyFileException
    {
        super(stepFiles);
        this.stepFile = stepFiles;
        File dbPropertyFile = new File(stepFiles.getPath()+".properties");
        generateDbName(dbPropertyFile);
        generateConnection();
    }

    private void generateDbQueryString()
    {
        GlobalMgr.getInstance().switchDBString(dbName);
    }

    private void generateDbName(final File sqlPropertiesFile) throws FileNotFoundException,IOException,SqlPropertyFileException
    {
        final FileInputStream propertyStream = new FileInputStream(sqlPropertiesFile);
        String[] parts = null;
        try
        {
            final String dataSource = IOUtils.toString(propertyStream);
            parts = dataSource.split("=");
            if((parts.length !=2)||(parts[0].equalsIgnoreCase("datasource")))
            {
                dbName = "";
                throw new SqlPropertyFileException("Sql property file: invalid content");
            }
            else
            {
                dbName = parts[1];
                generateDbQueryString();
            }
        }
        finally
        {
            IOUtils.closeQuietly(propertyStream);

        }
    }


    @Override
    public void execute() throws ClassNotFoundException,SQLException,FileNotFoundException,IOException
    {
        Connection myConnection = null;
        try {
            String jdbcDriver = GlobalMgr.getInstance().getJdbcDriver();
            Class.forName(jdbcDriver);
            myConnection = generateConnection();
        }
        catch (ClassNotFoundException|SQLException e) {
          setExecuteResult(false);
          throw(e);
        }

        ScriptRunner runner = new ScriptRunner(myConnection, true, true);
        runner.runScript(new BufferedReader(new FileReader(getStepName())));
    }



    private Connection generateConnection() throws SQLException {


        String jdbcUser = GlobalMgr.getInstance().getJdbcUser();
        String jdbcPassword = GlobalMgr.getInstance().getJdbcPassword();
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        dataSource.setServerName(dbName);
        return dataSource.getConnection();
    }



}
