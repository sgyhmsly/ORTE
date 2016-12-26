package CommonUtil;


import java.util.Collection;
import java.util.Map;

/**
 * Created by DT173 on 2016/12/26.
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
        if(oc != "" && oc != null)
            return true;
        else
            return false;
    }
}
