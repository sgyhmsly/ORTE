package TestCases;

import ORTEExceptions.StepFileNotNullException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

/**
 * Created by JJMM on 2017/1/14.
 */
public class TestComposite extends TestElement implements TestComponent
{
    protected Vector<TestComponent> components;

    public TestComposite(File testFile) throws StepFileNotNullException,FileNotFoundException
    {
        super(testFile );
        components = new Vector<TestComponent>();
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
        return StepPath.SetupFolder;
    }



    @Override
    public void execute() throws Exception
    {
        for (TestComponent component:components)
        {
            component.execute();
        }
    }

    @Override
    public void addTestElement(TestComponent tElement)
    {
        components.add(tElement);
    }

    @Override
    public void removeTestElement(TestComponent tElement)
    {
        components.remove(tElement);
    }

    @Override
    public Object getChildren()
    {
        return components;
    }


}
