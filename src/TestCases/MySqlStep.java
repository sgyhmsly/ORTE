package TestCases;

import java.io.File;
import java.sql.Connection;

/**
 * Created by DT173 on 2016/12/29.
 */
public class MySqlStep extends AbstractStep
{
    private static Connection mySqlConnection;
    public MySqlStep(File stepFiles)
    {

        super(stepFiles);
    }

    @Override
    public void execute()
    {

    }


    public static Connection getConnection()
    {
        return null;
    }
}
