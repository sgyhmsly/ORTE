package TestCases;

import ORTEExceptions.AddElementException;
import ORTEExceptions.StepFileNotNullException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by JJMM on 2017/1/23.
 */
public class TestSuite extends TestComposite
{
    public TestSuite(final File suiteName)throws StepFileNotNullException,FileNotFoundException
    {
        super(suiteName);
    }

}
