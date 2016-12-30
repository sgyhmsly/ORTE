package CommonUtil;//NOPMD

import java.io.*;
import java.nio.charset.Charset;
import org.mozilla.universalchardet.UniversalDetector;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by DT173 on 2016/12/29.
 */
public class FileEncoding
{//NOPMD
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static String getFileEncode(final File sourceFile) throws IOException
    {
        final byte[] buf = new byte[4096];
        FileInputStream  fis = null;
        String encoding =null;
        try
        {
            fis = new FileInputStream(sourceFile);
            final UniversalDetector detector = new UniversalDetector(null);

            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone())
                detector.handleData(buf, 0, nread);

            detector.dataEnd();
            encoding = detector.getDetectedCharset();

            if (encoding == null)
                encoding = DEFAULT_ENCODING;
        }

        finally
        {
            if(fis !=null)
                fis.close();


        }



        return encoding;
    }


}
