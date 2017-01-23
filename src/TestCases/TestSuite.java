package TestCases;

import ORTEExceptions.AddElementException;
import ORTEExceptions.StepFileNotNullException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by JJMM on 2017/1/23.
 */
public class TestSuite extends TestComposite
{
    public TestSuite(final File suiteName)throws StepFileNotNullException,FileNotFoundException
    {
        super(suiteName);
    }
    @Override
    public void addTestElement(final TestComponent tElement)
    {
        if (tElement instanceof AbstractStep)
            throw new AddElementException("LeafStep can not be added into test group");
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
