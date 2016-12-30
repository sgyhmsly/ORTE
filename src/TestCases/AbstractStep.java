package TestCases;//NOPMD


import java.io.File;


/**
 * Created by DT173 on 2016/12/28.
 */
public abstract class AbstractStep
{
    private final String stepName;
    private final String stepPath;


    AbstractStep(final File stepFiles)
    {
        this.stepName = stepFiles.getName();
        this.stepPath = stepFiles.getPath();

    }


    public String getStepName()
    {
        return stepName;
    }

    public String getStepPath()
    {
        return stepPath;
    }

    public abstract void execute();
    public void preExecute()//NOPMD
    {}//NOPMD
    public void afterExecute()//NOPMD
    {}//NOPMD
}
