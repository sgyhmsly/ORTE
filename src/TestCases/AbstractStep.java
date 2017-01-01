package TestCases;//NOPMD


import java.io.File;
import java.io.FileNotFoundException;

import ORTEExceptions.StepFileNotNullException;

/**
 * Created by DT173 on 2016/12/28.
 */
public abstract class AbstractStep
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
    public void preExecute()//NOPMD
    {}//NOPMD
    public void afterExecute()//NOPMD
    {}//NOPMD
}
