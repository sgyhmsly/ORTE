package TestCases;//NOPMD

import ORTEExceptions.StepFileNotNullException;
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

    public GroovyStep(final File step) throws MalformedURLException, FileNotFoundException, StepFileNotNullException
    {
        super(step);
        groovyVariables =new Binding();
        fileURL = new URL[]{step.toURI().toURL()};
        groovyEngine = new GroovyScriptEngine(fileURL);

    }

    @Override
    public void execute() throws ResourceException,ScriptException
    {
        try
        {
            groovyEngine.run(getStepName(), groovyVariables);
        } catch (ResourceException|ScriptException e)
        {
            this.setExecuteResult(false);
            throw e;
        }

    }
}
