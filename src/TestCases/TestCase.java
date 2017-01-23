package TestCases;

import ORTEExceptions.AddElementException;
import ORTEExceptions.StepFileNotNullException;
import ORTEExceptions.TestGroupException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

/**
 * Created by JJMM on 2017/1/14.
 */
public class TestCase extends TestComponent
{
    private Vector<TestComponent> setupSteps;
    private Vector<TestComponent> runSteps;
    private Vector<TestComponent> assertSteps;

    public TestCase(final File caseName) throws StepFileNotNullException,FileNotFoundException
    {
        super(caseName);
        setupSteps = new Vector<>();
        runSteps = new Vector<>();
        assertSteps = new Vector<>();
    }

    @Override
    public void preExecute()
    {

    }

    @Override
    public void afterExecute()
    {

    }



    @Override
    public void execute() throws Exception
    {

        try
        {
            for (final TestComponent setupStep:setupSteps)
                setupStep.execute();
            for (final TestComponent runStep:runSteps)
                runStep.execute();
            for (final TestComponent assertStep:assertSteps)
                assertStep.execute();
        } catch (Exception e)
        {
            setExecuteResult(false);
            throw e;
        }

    }

    @Override
    public void addTestElement(final TestComponent tElement)
    {
        if (tElement instanceof AbstractStep)
        {
            AbstractStep abSteps = (AbstractStep)tElement;
            if (abSteps.getStepPathType() == StepPath.SetupFolder)
                setupSteps.add(tElement);
            else if (abSteps.getStepPathType()==StepPath.RunFolder)
                runSteps.add(tElement);
            else if (abSteps.getStepPathType()==StepPath.AssertFolder)
                assertSteps.add(tElement);
        }
        throw new AddElementException("Test cases should only add test Steps");
    }

    @Override
    public void removeTestElement(final TestComponent tElement)
    {
        if (tElement instanceof AbstractStep)
        {
            AbstractStep abSteps = (AbstractStep) tElement;
            if (abSteps.getStepPathType() == StepPath.SetupFolder)
                setupSteps.remove(tElement);
            else if (abSteps.getStepPathType() == StepPath.RunFolder)
                runSteps.remove(tElement);
            else if (abSteps.getStepPathType() == StepPath.AssertFolder)
                assertSteps.remove(tElement);
        }
    }

    @Override
    public Object getChildren()
    {
        final Vector<Vector<TestComponent>> testSteps = new Vector<>();
        testSteps.add(setupSteps);
        testSteps.add(runSteps);
        testSteps.add(assertSteps);
        return testSteps;
    }


}
