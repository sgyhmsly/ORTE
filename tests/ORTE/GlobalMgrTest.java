package ORTE;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DT173 on 2016/12/29.
 */
public class GlobalMgrTest
{
    @Test
    public void getProjectFileProperty() throws Exception
    {

    }

    @Test
    public void getWorkingDirectory() throws Exception
    {

    }

    @Test
    public void getSimulatorJdbcDriver() throws Exception
    {
        GlobalMgr oGlobal = GlobalMgr.getInstance();
        String oDriver = oGlobal.getSimulatorJdbcDriver();
    }

    @Test
    public void getSimulatorJdbcURL() throws Exception
    {

    }

    @Test
    public void getSimulatorJdbcUser() throws Exception
    {

    }

    @Test
    public void getSimulatorJdbcPassword() throws Exception
    {

    }

}