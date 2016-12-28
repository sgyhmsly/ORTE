package TestCases;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by DT173 on 2016/12/28.
 */
public class GroovyStep extends AbstractStep
{
    private GroovyScriptEngine groovyEngine;
    private final Binding groovyVariables;
    private final URL[] fileURL;
    public URL[] getURL()
    {
        return fileURL.clone();
    }

    public GroovyStep(final File step) throws MalformedURLException
    {
        super(step);
        groovyVariables =new Binding();
        fileURL = new URL[]{step.toURI().toURL()};
        groovyEngine = new GroovyScriptEngine(fileURL);

    }

    @Override
    public void execute()
    {


        try
        {
            groovyEngine.run(getStepName(), groovyVariables);
        }
        catch (groovy.util.ResourceException e)
        {
            e.printStackTrace();
        }
        catch (groovy.util.ScriptException e)
        {
            e.printStackTrace();
        }
    }
}
