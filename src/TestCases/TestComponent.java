package TestCases;

import java.util.Objects;

/**
 * Created by JJMM on 2017/1/14.
 */
public interface TestComponent
{
    void execute() throws Exception;
    void preExecute();
    void afterExecute();
    void addTestElement(TestComponent tElement);
    void removeTestElement(TestComponent tElement);
    Object getChildren();
    StepPath getStepPathType();
}
