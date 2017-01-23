package TestCases;//NOPMD

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import ORTE.GlobalMgr;
import ORTEExceptions.DbNameEmptyException;
import ORTEExceptions.StepFileNotNullException;
import org.apache.commons.io.IOUtils;
import ORTEExceptions.SqlPropertyFileException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import static CommonUtil.FileReaderWithEncoding.readFilesWithEncode;

/**
 * Created by DT173 on 2016/12/29.
 */
public class MySqlStep extends AbstractStep
{

    private String dbName;
    public MySqlStep(final File stepFile)throws StepFileNotNullException,SqlPropertyFileException,FileNotFoundException,IOException
    {
        super(stepFile);
        final String sqlPropertyFilePath = stepFile.getParentFile().getPath()+"\\" + stepFile.getName().replaceFirst("\\d+_","")+".properties";
        final File dbPropertyFile = new File(sqlPropertyFilePath);
        if (!dbPropertyFile.exists())
            throw new FileNotFoundException("Db property file not found");
        extractDbNameFromPropertyFile(dbPropertyFile);

    }

//    private void generateDbQueryString()
//    {
//        assert(!ifEmpty(dbName));
//        GlobalMgr.getInstance().switchDBString(dbName);
//    }

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
//                generateDbQueryString();
            }
        }
        finally
        {
            IOUtils.closeQuietly(propertyStream);

        }
    }


    @Override
    public void execute() throws ClassNotFoundException,SQLException,FileNotFoundException,IOException, DbNameEmptyException
    {
        Connection myConnection = null;
        try {
            myConnection = generateConnection();
        }
        catch (ClassNotFoundException|SQLException e) {
          setExecuteResult(false);
          throw(e);
        }

        try
        {
            RunSQLScriptFile(myConnection);
        } catch (IOException|SQLException e)
        {
            setExecuteResult(false);
            throw(e);
        }
        finally
        {
            myConnection.close();
        }

    }

    private void RunSQLScriptFile(final Connection myConnection)throws IOException,SQLException
    {
        final ScriptRunner runner = new ScriptRunner(myConnection, true, true);
        final BufferedReader sqlReader = readFilesWithEncode(fileName);
        runner.runScript(sqlReader);
    }

    private Connection generateConnection() throws ClassNotFoundException,SQLException,DbNameEmptyException {
        final String jdbcDriver = getRoot().getJdbcDriver(dbName);
        Class.forName(jdbcDriver);
        final String jdbcUser = getRoot().getJdbcUser(dbName);
        final String jdbcPassword = getRoot().getJdbcPassword(dbName);
        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        dataSource.setServerName(dbName);
        return dataSource.getConnection();
    }



}
