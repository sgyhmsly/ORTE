package CommonUtil;

import ORTEExceptions.ZipFileStepException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

    public static BufferedReader readFileSteamWithEncode(final InputStream fileInputSteam) throws FileNotFoundException,IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while ((n = fileInputSteam.read(buf)) >= 0)
            baos.write(buf, 0, n);
        byte[] content = baos.toByteArray();

        InputStream is1 = new ByteArrayInputStream(content);
        InputStream is2 = new ByteArrayInputStream(content);

        final String fileEncode = getFileEncode(is1);
        //final String fileEncode = "UTF-8";
        final InputStreamReader isr = new InputStreamReader(is2, fileEncode);
        final BufferedReader buffReader = new BufferedReader(isr);
        return buffReader;
    }


    public static JSONObject readJsonFiles(final File jsonFile)throws IOException,ParseException
    {
        final JSONParser parser = new JSONParser();
        final BufferedReader buffReader = readFilesWithEncode(jsonFile);
        return (JSONObject)(parser.parse(buffReader));//NOPMD
    }

    public static JSONObject readJsonFiles(final InputStream jsonInputSteam)throws IOException,ParseException
    {
        final JSONParser parser = new JSONParser();
        BufferedReader buffReader = readFileSteamWithEncode(jsonInputSteam);
        return (JSONObject)(parser.parse(buffReader));//NOPMD
    }

    public static Map<String,InputStream> readZipFiles(final File zipFileName) throws IOException,ZipFileStepException
    {
        ZipFile zipFile = new ZipFile(zipFileName);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        Map<String,InputStream> filesInZip = new HashMap<>();
        while(entries.hasMoreElements())
        {
            ZipEntry entry = entries.nextElement();
            InputStream is = zipFile.getInputStream(entry);
            String fileName = entry.getName();
            filesInZip.put(fileName,is);
        }

        //zipFile.close();
        return filesInZip;
    }



}
