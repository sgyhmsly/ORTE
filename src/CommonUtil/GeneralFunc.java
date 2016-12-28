package CommonUtil;


import java.util.Collection;
import java.util.Map;

/**
 * The class is used for checking whether a object is empty of not.
 */
public class GeneralFunc
{
    public static  boolean notEmpty(final Collection oc)
    {
        boolean isNotEmpty = false;//NOPMD
        if (oc != null && oc.size()!=0)
            isNotEmpty = true;
        return isNotEmpty;
    }

    public static  boolean notEmpty(final Map oc)
    {
        boolean isNotEmpty = false;//NOPMD
        if (oc != null && oc.size()!=0)
            isNotEmpty = true;
        return isNotEmpty;
    }
    public static  boolean notEmpty(final String oc)
    {
        boolean isNotEmpty = false;//NOPMD
        if(oc != null && oc.equals("") )
            isNotEmpty = true;
        return isNotEmpty;
    }
}
