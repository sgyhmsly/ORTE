package ORTE;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by DT173 on 2016/12/30.
 */
public class GlobalMgrTest
{
    @Test
    public void importPropertyFile() throws Exception
    {
        GlobalMgr oMgr = GlobalMgr.getInstance();
        oMgr.importPropertyFile(new File("d:\\Project\\ORTE\\ORTEChoiceRes\\project.json"));
    }

    @Test
    public void getProjectFileProperty() throws Exception
    {


    }

    @Test
    public void getSimulatorJdbcDriver() throws Exception
    {
        GlobalMgr oMgr = GlobalMgr.getInstance();
        oMgr.importPropertyFile(new File("d:\\Project\\ORTE\\ORTEChoiceRes\\project.json"));
        oMgr.switchDBString("choiceres");
        String tempStr = oMgr.getJdbcDriver();

    }

    @Test
    public void getSimulatorJdbcURL() throws Exception
    {
        GlobalMgr oMgr = GlobalMgr.getInstance();
        oMgr.importPropertyFile(new File("d:\\Project\\ORTE\\ORTEChoiceRes\\project.json"));
        oMgr.switchDBString("choiceres");
        String tempStr = oMgr.getJdbcURL();
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