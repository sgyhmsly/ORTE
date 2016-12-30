package ORTE;//NOPMD

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DT173 on 2016/12/29.
 */
public class GlobalMgrTest//NOPMD
{
    @Test
    public void getProjectFileProperty() throws Exception//NOPMD
    {//NOPMD

    }

    @Test
    public void getWorkingDirectory() throws Exception//NOPMD
    {//NOPMD

    }

    @Test
    public void getSimulatorJdbcDriver() throws Exception//NOPMD
    {
        GlobalMgr oGlobal = GlobalMgr.getInstance();//NOPMD
        String oDriver = oGlobal.getSimulatorJdbcDriver();//NOPMD
    }

    @Test
    public void getSimulatorJdbcURL() throws Exception//NOPMD
    {//NOPMD

    }

    @Test
    public void getSimulatorJdbcUser() throws Exception//NOPMD
    {//NOPMD

    }

    @Test
    public void getSimulatorJdbcPassword() throws Exception//NOPMD
    {//NOPMD

    }

}