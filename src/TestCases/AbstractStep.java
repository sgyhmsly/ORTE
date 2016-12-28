package TestCases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by DT173 on 2016/12/28.
 */
public abstract class AbstractStep
{
    private String stepName;
    private String stepPath;


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
    public void preExecute()
    {;}
    public void afterExecute()
    {;}
}
