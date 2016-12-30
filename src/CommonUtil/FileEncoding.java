package CommonUtil;//NOPMD

import java.io.File;
import java.io.IOException;

import org.mozilla.universalchardet.UniversalDetector;



/**
 * Created by DT173 on 2016/12/29.
 */
public class FileEncoding
{//NOPMD

    public static String getFileEncode(final File sourceFile) throws IOException
    {
        final byte[] buf = new byte[4096];

        final UniversalDetector detector = new UniversalDetector(null);
        final java.io.FileInputStream fis = new java.io.FileInputStream(sourceFile);


        int nRead;
        while ((nRead = fis.read(buf)) > 0 && !detector.isDone()) {//NOPMD
            detector.handleData(buf, 0, nRead);//NOPMD
        }
        fis.close();//NOPMD
        detector.dataEnd();//NOPMD


        return detector.getDetectedCharset();//NOPMD

    }
}
