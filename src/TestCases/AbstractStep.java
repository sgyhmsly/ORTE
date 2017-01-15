package TestCases;//NOPMD


import java.io.File;
import java.io.FileNotFoundException;

import ORTEExceptions.LeafStepException;
import ORTEExceptions.StepFileNotNullException;

/**
 * Created by DT173 on 2016/12/28.
 */
public abstract class AbstractStep  extends  TestComponent
{

    AbstractStep(final File stepFile) throws StepFileNotNullException,FileNotFoundException
    {
        super(stepFile);
    }

    public abstract void execute() throws Exception;

    @Override
    public StepPath getStepPathType()
    {
        if (fileName.getPath().toLowerCase().contains("runs"))
            return StepPath.RunFolder;
        else if(fileName.getPath().toLowerCase().contains("setups"))
            return StepPath.SetupFolder;
        else if (fileName.getPath().toLowerCase().contains("asserts"))
            return StepPath.AssertFolder;
        else
            throw new LeafStepException("Steps not in runs/setups/asserts folder");
    }

    @Override
    public void addTestElement(TestComponent tElement)
    {
        throw new LeafStepException("Leaf Steps can not add nested steps");
    }

    @Override
    public void removeTestElement(TestComponent tElement)
    {
        throw new LeafStepException("Leaf Steps can not remove nested steps");
    }

    @Override
    public Object getChildren()
    {
        return null;
    }

    public void preExecute()//NOPMD
    {}//NOPMD
    public void afterExecute()//NOPMD
    {}//NOPMD
}
