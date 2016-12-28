package TestCases;

/**
 * Created by DT173 on 2016/12/28.
 */
public abstract class AbstractStep
{
    private String stepName;
    private String stepPath;


    AbstractStep(final String stepName, final String stepPath)
    {
        this.stepName = stepName;
        this.stepPath = stepPath;
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
    public void preExecute()
    {;}
    public void afterExecute()
    {;}
}
