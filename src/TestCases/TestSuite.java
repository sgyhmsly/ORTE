package TestCases;

import ORTEExceptions.LeafStepException;
import ORTEExceptions.StepFileNotNullException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by JJMM on 2017/1/23.
 */
public class TestSuite extends TestComposite
{
    public TestSuite(final File groupName)throws StepFileNotNullException,FileNotFoundException
    {
        super(groupName);
    }
    @Override
    public void addTestElement(final TestComponent tElement)
    {
        if (tElement instanceof AbstractStep)
            throw new LeafStepException("LeafStep can not be added into test group");
        if (tElement instanceof TestCase|| tElement instanceof TestSuite)
            components.add(tElement);
    }

    @Override
    public void removeTestElement(final TestComponent tElement)
    {
        if (tElement instanceof AbstractStep)
            return;
        if (tElement instanceof TestCase||tElement instanceof TestSuite)
            components.remove(tElement);
    }
}
