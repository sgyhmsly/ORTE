package TestCases;

import ORTEExceptions.LeafStepException;

import java.util.Vector;

/**
 * Created by JJMM on 2017/1/14.
 */
public class TestGroup extends TestComposite implements TestComponent
{


    @Override
    public void addTestElement(TestComponent tElement)
    {
        if (tElement instanceof AbstractStep)
            throw new LeafStepException("LeafStep can not be added into test group");
        if (tElement instanceof TestCase|| tElement instanceof TestGroup)
            components.add(tElement);
    }

    @Override
    public void removeTestElement(TestComponent tElement)
    {
        if (tElement instanceof AbstractStep)
            return;
        if (tElement instanceof TestCase||tElement instanceof TestGroup)
            components.remove(tElement);
    }


    @Override
    public StepPath getStepPathType()
    {
        return StepPath.GroupFolder;
    }

}
