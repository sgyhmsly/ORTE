package ORTEExceptions;

/**
 * Created by JJMM on 2016/12/31.
 */
public class DbNameEmptyException extends RuntimeException
{
    public DbNameEmptyException(String msg)
    {
        super(msg);
    }
}
