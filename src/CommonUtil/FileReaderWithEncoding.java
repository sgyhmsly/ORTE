package CommonUtil;

import ORTEExceptions.ZipFileStepException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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

    public static JSONObject readJsonFiles(final File jsonFile)throws IOException,ParseException
    {
        final JSONParser parser = new JSONParser();
        final BufferedReader buffReader = readFilesWithEncode(jsonFile);
        return (JSONObject)(parser.parse(buffReader));//NOPMD
    }

    public static List<File> readZipFiles(final File zipFileName) throws IOException,ZipFileStepException
    {
        ZipFile zipFile = new ZipFile(zipFileName);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        List<File> filesInZip = new ArrayList<>();
        while(entries.hasMoreElements())
        {
            ZipEntry entry = entries.nextElement();
            String fileName = entry.getName();
            File file = new File (fileName);
            filesInZip.add(file);
        }
        zipFile.close();
        return filesInZip;
    }

}
