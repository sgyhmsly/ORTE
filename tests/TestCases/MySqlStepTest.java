package TestCases;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by JJMM on 2017/1/1.
 */
public class MySqlStepTest
{
    @Test
    public void execute() throws Exception
    {
        final MySqlStep ostep = new MySqlStep(new File("d:\\project\\ORTE\\ORTEChoiceRes\\03_check_request_from_choiceres_to_crs.sql"));
        ostep.execute();
    }

}