package ORTEExceptions;

/**
 * Created by JJMM on 2016/12/31.
 */
public class DbQueryStringEmptyException extends RuntimeException
{
    public DbQueryStringEmptyException(String msg)
    {
        super(msg);
    }
}
