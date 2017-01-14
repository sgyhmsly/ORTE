package TestCases;//NOPMD


import java.io.File;
import java.io.FileNotFoundException;

import ORTEExceptions.LeafStepException;
import ORTEExceptions.StepFileNotNullException;

/**
 * Created by DT173 on 2016/12/28.
 */
public abstract class AbstractStep implements TestComponent
{
    private boolean ifSuccess;
    protected final File stepFile;
    AbstractStep(final File stepFile) throws StepFileNotNullException,FileNotFoundException
    {
        if(stepFile == null)
            throw new StepFileNotNullException("Step file not be null");
        if(!stepFile.exists())
            throw new FileNotFoundException("Step file not exist");
        this.stepFile = stepFile;
        ifSuccess = true;
    }

    public String getStepName()
    {
        return stepFile.getName();
    }

    public String getStepPath()
    {
        return stepFile.getPath();
    }

    public boolean getExecuteResult()
    {
        return ifSuccess;
    }

    public void setExecuteResult(boolean result)
    {
        ifSuccess = result;
    }



    public abstract void execute() throws Exception;

    @Override
    public StepPath getStepPathType()
    {
        if (stepFile.getPath().toLowerCase().contains("runs"))
            return StepPath.RunFolder;
        else if(stepFile.getPath().toLowerCase().contains("setups"))
            return StepPath.SetupFolder;
        else if (stepFile.getPath().toLowerCase().contains("asserts"))
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
