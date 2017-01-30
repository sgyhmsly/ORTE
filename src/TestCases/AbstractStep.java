package TestCases;//NOPMD


import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by DT173 on 2016/12/28.
 */
public abstract class AbstractStep implements IExcutable
{

    private boolean ifSuccess;
    protected final File stepFile;
    private final TestCase testCase;

    AbstractStep(final File stepFile,final TestCase testCase) throws FileNotFoundException
    {
        if(stepFile == null)
            throw new NullPointerException("Step file not be null");
        if (testCase == null)
            throw new NullPointerException("testCase should not be null");
        if(!stepFile.exists())
            throw new FileNotFoundException("Step file not exist");
        this.testCase = testCase;
        this.stepFile = stepFile;
        ifSuccess = true;
    }

    public TestCase getTestCase()
    {
        return testCase;
    }

    public TestProject getRoot()
    {
        return getTestCase().getTestSuite().getTestProject();
    }

    public String getFileName()
    {
        return stepFile.getName();
    }

    public String getFilePath()
    {
        return stepFile.getPath();
    }

    public boolean getExecuteResult()
    {
        return ifSuccess;
    }


    public void setExecuteResult(final boolean result)
    {
        ifSuccess = result;
    }

    abstract public void preExecute();
    abstract public void afterExecute();

}
