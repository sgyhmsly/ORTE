package CommonUtil;

import java.io.File;
import java.io.IOException;

import org.mozilla.universalchardet.UniversalDetector;



/**
 * Created by DT173 on 2016/12/29.
 */
public class FileEncoding
{

    public static String getFileEncode(File sourceFile)
    {
        byte[] buf = new byte[4096];

        UniversalDetector detector;
        java.io.FileInputStream fis;
        String encoding = "";
        try
        {
            fis = new java.io.FileInputStream(sourceFile);
            detector = new UniversalDetector(null);


            int nRead;
            while ((nRead = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nRead);
            }

            detector.dataEnd();
            if(fis != null)
                fis.close();
            encoding = detector.getDetectedCharset();
        } catch (IOException e)
        {
            e.printStackTrace();
        }



        return encoding;
    }
}
