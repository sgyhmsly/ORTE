package TestCases;//NOPMD

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by DT173 on 2016/12/28.
 */
public class GroovyStep extends AbstractStep
{
    private final GroovyScriptEngine groovyEngine;
    private final Binding groovyVariables;
    private final URL[] fileURL;
    public URL[] getURL()
    {
        return fileURL.clone();
    }

    public GroovyStep(final File step,final TestCase testCase) throws MalformedURLException, FileNotFoundException
    {
        super(step,testCase);
        groovyVariables =new Binding();
        String runPath = step.getParentFile().getParentFile().getPath()+"\\runs";
        String assertPath = step.getParentFile().getParentFile().getPath()+"\\asserts";
        String setupPath = step.getParentFile().getParentFile().getPath()+"\\setups";
        groovyVariables.setProperty("runPath",runPath);
        groovyVariables.setProperty("assertPath",assertPath);
        groovyVariables.setProperty("setupPath",setupPath);
        fileURL = new URL[]{step.toURI().toURL()};
        groovyEngine = new GroovyScriptEngine(fileURL);

    }

    @Override
    public void execute() throws ResourceException,ScriptException
    {
        try
        {
            groovyEngine.run(getFileName(), groovyVariables);
        } catch (ResourceException|ScriptException e)
        {
            this.setExecuteResult(false);
            throw e;
        }

    }

    public void preExecute()
    {
        ;
    }
    public void afterExecute()
    {
        ;
    }
}
