package CommonUtil;


import java.util.Collection;
import java.util.Map;

/**
 * The class is used for checking whether a object is empty of not.
 */
public class GeneralFunc
{
    public static  boolean notEmpty(Collection oc)
    {
        if (oc != null && oc.size()!=0)
            return true;
        else
            return  false;
    }

    public static  boolean notEmpty(Map oc)
    {
        if (oc != null && oc.size()!=0)
            return true;
        else
            return  false;
    }
    public static  boolean notEmpty(String oc)
    {
        if(oc != null && oc.equals("") )
            return true;
        else
            return false;
    }
}
