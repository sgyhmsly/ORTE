package TestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JJMM on 2017/1/14.
 */
public class TestCase implements IExcutable
{
    private final List<AbstractStep> setupSteps;
    private final List<AbstractStep> runSteps;
    private final List<AbstractStep> expectedResults;
    private final List<AbstractStep> clearSteps;
    private final String actualResultPath;
    private final String outPutPath;
    private final TestSuite testSuite;
    private boolean ifSuccess;

    public TestCase(final File caseName,TestSuite testSuite) throws FileNotFoundException
    {
        ifSuccess =true;
        this.testSuite = testSuite;
        setupSteps = Collections.synchronizedList(new ArrayList<AbstractStep>());
        runSteps = Collections.synchronizedList(new ArrayList<AbstractStep>());
        expectedResults = Collections.synchronizedList(new ArrayList<AbstractStep>());
        clearSteps = Collections.synchronizedList(new ArrayList<AbstractStep>());
        actualResultPath = caseName.getPath()+"\\actualResult";
        outPutPath = caseName.getPath()+"\\outPutPath";
    }


    public void preExecute()
    {

    }


    public void afterExecute()
    {

    }

    public TestSuite getTestSuite()
    {
        return testSuite;
    }

    @Override
    public void execute() throws Exception
    {
        try
        {
            for (final AbstractStep setupStep:setupSteps)
                setupStep.execute();
            for (final AbstractStep runStep:runSteps)
                runStep.execute();
            for (final AbstractStep expectedResult:expectedResults)
                expectedResult.execute();
            for (final AbstractStep clearStep:clearSteps)
                clearStep.execute();
        } catch (Exception e)
        {
            setExecuteResult(false);
            throw e;
        }
    }

    public boolean getExecuteResult()
    {
        return ifSuccess;
    }


    public void setExecuteResult(final boolean result)
    {
        ifSuccess = result;
    }

    public void addSetupSteps(final AbstractStep setupStep)
    {
        if (setupStep !=null)
            setupSteps.add(setupStep);
    }

    public void removeSetupSteps(final AbstractStep setupStep)
    {
        setupSteps.remove(setupStep);
    }

    public List<AbstractStep> getSetupSteps()
    {
        return Collections.unmodifiableList(setupSteps);
    }


    public void addRunSteps(final AbstractStep runStep)
    {
        if (runStep!=null)
            runSteps.add(runStep);
    }

    public void removeRunSteps(final AbstractStep runStep)
    {
        runSteps.remove(runStep);
    }

    public List<AbstractStep> getRunSteps()
    {
        return Collections.unmodifiableList(runSteps);
    }

    public void addClearSteps(final AbstractStep clearStep)
    {
        if (clearStep!=null)
            clearSteps.add(clearStep);
    }

    public void removeClearSteps(final AbstractStep clearStep)
    {
        clearSteps.remove(clearStep);
    }

    public List<AbstractStep> getClearSteps()
    {
        return Collections.unmodifiableList(clearSteps);
    }
    public void addExpectedResult(final AbstractStep expectedResultStep)
    {
        if (expectedResultStep!=null)
            expectedResults.add(expectedResultStep);
    }

    public void removeExpectedResult(final AbstractStep expectedResultStep)
    {
        expectedResults.remove(expectedResultStep);
    }

    public List<AbstractStep> getExpectedResultSteps()
    {
        return Collections.unmodifiableList(expectedResults);
    }


}
