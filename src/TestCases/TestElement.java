package TestCases;

import ORTEExceptions.StepFileNotNullException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by JJMM on 2017/1/14.
 */
public abstract class TestElement implements TestComponent
{
    private boolean ifSuccess;
    protected final File testFile;


    public TestElement(final File stepFile) throws StepFileNotNullException,FileNotFoundException
    {
        if(stepFile == null)
            throw new StepFileNotNullException("Step file not be null");
        if(!stepFile.exists())
            throw new FileNotFoundException("Step file not exist");
        this.testFile = stepFile;
        ifSuccess = true;
    }


    public String getTestName()
    {
        return testFile.getName();
    }

    public String getTestPath()
    {
        return testFile.getPath();
    }

    public boolean getExecuteResult()
    {
        return ifSuccess;
    }


    public void setExecuteResult(boolean result)
    {
        ifSuccess = result;
    }
}
