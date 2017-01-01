package CommonUtil;

import java.io.*;

import static CommonUtil.FileEncoding.getFileEncode;

/**
 * Created by JJMM on 2017/1/1.
 */
public class FileReaderWithEncoding
{
    public static BufferedReader readFilesWithEncode(final File fileObj) throws FileNotFoundException,IOException
    {
        if(!fileObj.exists())
        {
            throw new FileNotFoundException("File to be read not exist");
        }
        final String fileEncode = getFileEncode(fileObj);
        final FileInputStream is = new FileInputStream(fileObj);//NOPMD
        final InputStreamReader isr = new InputStreamReader(is, fileEncode);
        final BufferedReader buffReader = new BufferedReader(isr);
        return buffReader;
    }

}
