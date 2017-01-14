package TestCases;

import ORTEExceptions.TestGroupException;

import java.util.Vector;

/**
 * Created by JJMM on 2017/1/14.
 */
public class TestCase implements TestComponent
{
    private Vector<TestComponent> setupSteps;
    private Vector<TestComponent> runSteps;
    private Vector<TestComponent> assertSteps;

    public TestCase()
    {
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
    public StepPath getStepPathType()
    {
        return null;
    }

    @Override
    public void execute() throws Exception
    {
        for (TestComponent setupStep:setupSteps)
            setupStep.execute();
        for (TestComponent runStep:runSteps)
            runStep.execute();
        for (TestComponent assertStep:assertSteps)
            assertStep.execute();
    }

    @Override
    public void addTestElement(TestComponent tElement)
    {
        if (tElement.getStepPathType() == StepPath.SetupFolder)
            setupSteps.add(tElement);
        else if (tElement.getStepPathType()==StepPath.RunFolder)
            runSteps.add(tElement);
        else if (tElement.getStepPathType()==StepPath.AssertFolder)
            assertSteps.add(tElement);
        else if (tElement.getStepPathType()==StepPath.GroupFolder)
            throw new TestGroupException("Test groups should not be added into test cases");
    }

    @Override
    public void removeTestElement(TestComponent tElement)
    {
        if (tElement.getStepPathType() == StepPath.SetupFolder)
            setupSteps.remove(tElement);
        else if (tElement.getStepPathType()==StepPath.RunFolder)
            runSteps.remove(tElement);
        else if (tElement.getStepPathType()==StepPath.AssertFolder)
            assertSteps.remove(tElement);
    }

    @Override
    public Object getChildren()
    {
        Vector<Vector<TestComponent>> testSteps = new Vector<>();
        testSteps.add(setupSteps);
        testSteps.add(runSteps);
        testSteps.add(assertSteps);
        return testSteps;
    }

}
