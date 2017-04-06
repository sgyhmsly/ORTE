package TestCases;//NOPMD

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;


import ORTEExceptions.ZipFileStepException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.json.simple.parser.ParseException;




/**
 * Created by DT173 on 2016/12/29.
 */
public class MySqlStep
{

    private String dbName;
    private String actualFile;
    public MySqlStep(final File stepFile,final TestCase testCase)throws FileNotFoundException,IOException, ZipFileStepException,ParseException
    {
        //super(stepFile,testCase);
//        if (!stepFile.getName().toLowerCase().endsWith(".sql.zip"))
//            throw new IllegalArgumentException("Sql Step Zip File must be .sql.zip");
//        if (!getExecuteFileName().toLowerCase().endsWith(".sql"))
//        {
//            throw new IllegalArgumentException(".sql File not exist in Sql Zip File");
//        }
//        JSONObject jsonObject = readJsonFiles(getExecutePropertyInputSteam());
//        dbName = (String)jsonObject.get("datasource");
    }



    public String getDbName() throws IOException,ParseException
    {
        return dbName;
    }

    public String getActualFile() throws IOException,ParseException
    {
//        if (actualFile.equals(""))
//        {
//            JSONObject jsonObject = readJsonFiles(getExecutePropertyInputSteam());
//            actualFile = (String)jsonObject.get("output");
//        }
//        return actualFile;
        return null;
    }




    public void execute() throws ClassNotFoundException,SQLException,FileNotFoundException,IOException,ParseException
    {
//        Connection myConnection = null;
//        try {
//            myConnection = generateConnection();
//        }
//        catch (ClassNotFoundException|SQLException e) {
//          setExecuteResult(false);
//          throw(e);
//        }
//
//        try
//        {
//            RunSQLScriptFile(myConnection);
//        } catch (IOException|SQLException e)
//        {
//            setExecuteResult(false);
//            throw(e);
//        }
//        finally
//        {
//            myConnection.close();
//        }

    }

    private void RunSQLScriptFile(final Connection myConnection)throws IOException,SQLException,ParseException
    {

//        final ScriptRunner runner = new ScriptRunner(myConnection, true, true,new File("D:\\project\\ORTE\\ORTEChoiceRes\\testsuites\\RAD_Request\\Agoda_MongoDB_Exist_roomcount=1\\actualResults\\request_from_choiceres_to_crs.txt"));
//        final BufferedReader sqlReader = readFileSteamWithEncode(getExecuteInputSteam());
//        runner.runScript(sqlReader);
    }

    private Connection generateConnection() throws ClassNotFoundException,SQLException {

        final String jdbcDriver = "com.mysql.jdbc.Driver";
        Class.forName(jdbcDriver);
        final String jdbcUser = "root";
        final String jdbcPassword = "Elekchina123!";



        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        dataSource.setServerName("localhost");
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
