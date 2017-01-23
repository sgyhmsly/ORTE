package TestCases;

import ORTEExceptions.NoRootException;
import ORTEExceptions.StepFileNotNullException;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by JJMM on 2017/1/14.
 */
public abstract class TestComponent
{
    private boolean ifSuccess;
    protected final File fileName;
    private TestProject root;
    private TestComponent parentComponent;



    abstract void execute() throws Exception;
    abstract void preExecute();
    abstract void afterExecute();


    abstract void addTestElement(TestComponent tElement);
    abstract void removeTestElement(TestComponent tElement);
    abstract Object getChildren();




    public TestComponent(final File stepFile) throws StepFileNotNullException,FileNotFoundException
    {
        if(stepFile == null)
            throw new StepFileNotNullException("Step file not be null");
        if(!stepFile.exists())
            throw new FileNotFoundException("Step file not exist");
        this.fileName = stepFile;
        ifSuccess = true;
        root = null;
        parentComponent = null;
    }

    public TestProject getRoot()
    {
        if (root == null)
        {

            TestComponent oParent = this;
            do
            {
                if (oParent instanceof TestProject)
                    return (TestProject)oParent;
                oParent = oParent.getParentComponent();
            }
            while(oParent !=null);
            if (oParent == null)
                throw new NoRootException("No Root Found");
        }
        return  root;
    }

    public TestComponent getParentComponent()
    {
        return parentComponent;
    }

    public void setParentComponent(TestComponent component)
    {
        parentComponent = component;
    }


    public String getFileName()
    {
        return fileName.getName();
    }

    public String getFilePath()
    {
        return fileName.getPath();
    }

    public boolean getExecuteResult()
    {
        return ifSuccess;
    }


    public void setExecuteResult(final boolean result)
    {
        ifSuccess = result;
    }
}
