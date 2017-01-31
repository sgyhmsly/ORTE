package TestCases;//NOPMD

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import ORTEExceptions.DbNameEmptyException;
import ORTEExceptions.ZipFileStepException;
import org.apache.commons.io.IOUtils;
import ORTEExceptions.SqlPropertyFileException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import static CommonUtil.FileReaderWithEncoding.readFilesWithEncode;
import static CommonUtil.FileReaderWithEncoding.readJsonFiles;
/**
 * Created by DT173 on 2016/12/29.
 */
public class MySqlStep extends CompositeStep
{

    private String dbName;
    private String actualFile;
    public MySqlStep(final File stepFile,final TestCase testCase)throws FileNotFoundException,IOException, ZipFileStepException
    {
        super(stepFile,testCase);
        dbName = "";
    }


    public String getDbName() throws IOException,ParseException
    {
        if (dbName.equals(""))
        {
            JSONObject jsonObject = readJsonFiles(executePropertyFile);
            dbName = (String)jsonObject.get("datasource");
        }
        return dbName;
    }

    public String getActualFile() throws IOException,ParseException
    {
        if (actualFile.equals(""))
        {
            JSONObject jsonObject = readJsonFiles(executePropertyFile);
            actualFile = (String)jsonObject.get("output");
        }
        return actualFile;
    }



    @Override
    public void execute() throws ClassNotFoundException,SQLException,FileNotFoundException,IOException
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
        final BufferedReader sqlReader = readFilesWithEncode(stepFile);
        runner.runScript(sqlReader);
    }

    private Connection generateConnection() throws ClassNotFoundException,SQLException {
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

    public void preExecute()
    {
        ;
    }
    public void afterExecute()
    {
        ;
    }

}
