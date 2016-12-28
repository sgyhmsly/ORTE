package TestCases;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by DT173 on 2016/12/28.
 */
public class GroovyStepTest
{
    @Test
    public void execute() throws Exception
    {
        GroovyStep oStep = new GroovyStep(new File("C:\\temp\\test.groovy"));
        oStep.execute();
    }

}