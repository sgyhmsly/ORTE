package TestCases;//NOPMD

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import ORTE.GlobalMgr;
import ORTEExceptions.StepFileNotNullException;
import org.apache.commons.io.IOUtils;
import ORTEExceptions.SqlPropertyFileException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import static CommonUtil.FileReaderWithEncoding.readFilesWithEncode;
import static CommonUtil.GeneralFunc.ifEmpty;
/**
 * Created by DT173 on 2016/12/29.
 */
public class MySqlStep extends AbstractStep
{

    private String dbName;
    public MySqlStep(File stepFile)throws StepFileNotNullException,SqlPropertyFileException,FileNotFoundException,IOException
    {
        super(stepFile);
        String sqlPropertyFilePath = stepFile.getParentFile().getPath()+"\\" + stepFile.getName().replaceFirst("\\d+_","")+".properties";
        File dbPropertyFile = new File(sqlPropertyFilePath);
        if (!dbPropertyFile.exists())
            throw new FileNotFoundException("Db property file not found");
        extractDbNameFromPropertyFile(dbPropertyFile);

    }

    private void generateDbQueryString()
    {
        assert(!ifEmpty(dbName));
        GlobalMgr.getInstance().switchDBString(dbName);
    }

    private void extractDbNameFromPropertyFile(final File sqlPropertiesFile) throws FileNotFoundException,IOException,SqlPropertyFileException
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
        BufferedReader sqlReader = readFilesWithEncode(stepFile);
        runner.runScript(sqlReader);
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
