package TestCases;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by JJMM on 2017/1/31.
 */
public class ReplaceStep extends AbstractStep
{
    public ReplaceStep(final File stepFile,final TestCase testCase) throws FileNotFoundException
    {
        super(stepFile,testCase);
    }

    @Override
    public void execute() throws Exception
    {

    }

    @Override
    public void preExecute()
    {

    }

    @Override
    public void afterExecute()
    {

    }
}
