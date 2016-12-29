package CommonUtil;

import java.io.File;
import java.io.IOException;

import org.mozilla.universalchardet.UniversalDetector;



/**
 * Created by DT173 on 2016/12/29.
 */
public class FileEncoding
{

    public static String getFileEncode(File sourceFile) throws IOException
    {
        byte[] buf = new byte[4096];

        UniversalDetector detector;
        java.io.FileInputStream fis =null;
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

            encoding = detector.getDetectedCharset();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(fis != null)
                fis.close();
        }



        return encoding;
    }
}
