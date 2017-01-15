package TestCases;//NOPMD

import org.junit.Test;

import java.io.File;//NOPMD

import static org.junit.Assert.*;

/**
 * Created by DT173 on 2016/12/28.
 */
public class GroovyStepTest//NOPMD
{
    @Test
    public void execute() throws Exception//NOPMD
    {
        final GroovyStep oStep = new GroovyStep(new File("C:\\temp\\test.groovy"));
        oStep.execute();
    }

}