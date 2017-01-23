package TestCases;

import ORTEExceptions.AddElementException;
import ORTEExceptions.StepFileNotNullException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

/**
 * Created by JJMM on 2017/1/14.
 */
public class TestComposite extends  TestComponent
{
    protected Vector<TestComponent> components;

    public TestComposite(final File testFile) throws StepFileNotNullException,FileNotFoundException
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
    public void execute() throws Exception
    {

        try
        {
            for (final TestComponent component:components)
            {
                component.execute();
            }
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
            throw new AddElementException("Steps must be added to Cases");
        tElement.setParentComponent(this);
        components.add(tElement);
    }

    @Override
    public void removeTestElement(final TestComponent tElement)
    {
        if (components.contains(tElement))
        {
            tElement.setParentComponent(null);
            components.remove(tElement);
        }
    }

    @Override
    public Object getChildren()
    {
        return components;
    }


}
