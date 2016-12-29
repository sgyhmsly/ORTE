package CommonUtil;


import java.util.Collection;
import java.util.Map;

/**
 * The class is used for checking whether a object is empty of not.
 */
public class GeneralFunc
{
    public static  boolean isEmpty(final Collection oc)
    {
        boolean bIsEmpty = true;//NOPMD
        if (oc != null && oc.size()!=0)
            bIsEmpty = false;
        return bIsEmpty;
    }

    public static  boolean isEmpty(final Map oc)
    {
        boolean bIsEmpty = true;//NOPMD
        if (oc != null && oc.size()!=0)
            bIsEmpty = false;
        return bIsEmpty;
    }
    public static  boolean isEmpty(final String oc)
    {
        boolean bIsEmpty = true;//NOPMD
        if(oc != null && oc.equals("") )
            bIsEmpty = false;
        return bIsEmpty;
    }
}
