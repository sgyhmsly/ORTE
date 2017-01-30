package TestCases;

import ORTEExceptions.TestSuiteException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Created by JJMM on 2017/1/23.
 */
public class TestSuite implements IExcutable
{
    private final TestProject testProject;
    private final File suitFile;
    private final List<TestSuite> testSuites;
    private final List<TestCase> testCases;
    private boolean ifSuccess;
    public TestSuite(final File suiteName,final TestProject testProject)throws FileNotFoundException
    {
        if (suiteName ==null)
            throw new NullPointerException("Suite file should not be null");
        if (testProject == null)
            throw new NullPointerException("Project file should not be null");
        this.testProject = testProject;
        suitFile = suiteName;
        testSuites = Collections.synchronizedList(new ArrayList<TestSuite>());
        testCases =  Collections.synchronizedList(new ArrayList<TestCase>());
        ifSuccess = false;
    }

    public boolean getExecuteResult()
    {
        return ifSuccess;
    }


    public void setExecuteResult(final boolean result)
    {
        ifSuccess = result;
    }

    public TestProject getTestProject()
    {
        return testProject;
    }

    public void addTestSuite(TestSuite testSuite)
    {
        if (testSuite!=null)
            testSuites.add(testSuite);
    }
    public void removeTestSuite(TestSuite testSuite)
    {
        testSuites.remove(testSuite);
    }

    public List<TestSuite> getTestSuites()
    {
        return Collections.unmodifiableList(testSuites);
    }

    public void addTestCase(TestCase testCase)
    {
        if (testCase!=null)
            testCases.add(testCase);
    }

    public void removeTestCase(TestCase testCase)
    {
        testCases.remove(testCase);
    }

    public List<TestCase> getTestCases()
    {
        return Collections.unmodifiableList(testCases);
    }

    @Override
    public void execute() throws Exception
    {
        if (testSuites.isEmpty() && testCases.isEmpty())
            return;
        else if ((!testSuites.isEmpty())&& (!testCases.isEmpty()))
            throw new TestSuiteException("Test suite can not add both test suites and test cases");
        else if (testSuites.isEmpty()&& (!testCases.isEmpty()))
        {
            for (TestCase testCase:testCases)
            {
                try
                {
                    testCase.execute();
                } catch (Exception e)
                {
                    setExecuteResult(false);
                    throw e;
                }
            }
        }
        else if ((!testSuites.isEmpty())&& testCases.isEmpty())
        {
            for (TestSuite testSuite:testSuites)
            {
                try
                {
                    testSuite.execute();
                } catch (Exception e)
                {
                    setExecuteResult(false);
                    throw e;
                }
            }
        }
    }
}
