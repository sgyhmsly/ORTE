package CommonUtil;//NOPMD


import java.util.Collection;
import java.util.Map;

/**
 * The class is used for checking whether a object is empty of not.
 */
public class GeneralFunc
{//NOPMD
    public static  boolean ifEmpty(final Collection oc)//NOPMD
    {

        boolean bIsEmpty = true;//NOPMD
        if (oc != null && oc.size()!=0)//NOPMD
            bIsEmpty = false;
        return bIsEmpty;
    }

    public static  boolean ifEmpty(final Map oc)//NOPMD
    {
        boolean bIsEmpty = true;//NOPMD
        if (oc != null && oc.size()!=0)//NOPMD
            bIsEmpty = false;
        return bIsEmpty;
    }
    public static  boolean ifEmpty(final String oc)//NOPMD
    {
        boolean bIsEmpty = true;//NOPMD
        if(oc != null && (!oc.trim().equals("")) )//NOPMD
            bIsEmpty = false;
        return bIsEmpty;
    }
}
