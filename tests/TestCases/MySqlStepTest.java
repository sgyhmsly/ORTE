package TestCases;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by JJMM on 2017/1/31.
 */
public class MySqlStepTest
{
    @Test
    public void execute() throws Exception
    {
        MySqlStep oSqlStep = new MySqlStep(new File("d:\\project\\ORTE\\ORTEChoiceRes\\testsuites\\RAD_Request\\Agoda_MongoDB_Exist_roomcount=1\\runs\\check_request_from_choiceres_to_crs.sql.zip"),null);
        oSqlStep.execute();
    }

}