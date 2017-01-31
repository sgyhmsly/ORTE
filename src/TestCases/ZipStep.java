package TestCases;

import ORTEExceptions.ZipFileStepException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static CommonUtil.FileReaderWithEncoding.readZipFiles;
/**
 * Created by JJMM on 2017/1/31.
 */
public abstract class ZipStep extends AbstractStep
{

    protected Map<String, InputStream> filesInZip;
    ZipStep(final File stepFile, final TestCase testCase) throws FileNotFoundException,IOException, ZipFileStepException
    {
        super(stepFile,testCase);
        if (!stepFile.getName().toLowerCase().endsWith(".zip"))
            throw new IllegalArgumentException("Not a zip file for composite steps");
        filesInZip = readZipFiles(stepFile);
        validateFilesInZip(filesInZip.keySet());


    }



    public InputStream getExecutePropertyInputSteam() throws IOException
    {
        for(Map.Entry<String, InputStream> entry:filesInZip.entrySet())
        {
            if (entry.getKey().toLowerCase().endsWith(".properties"))
            {
                InputStream inputSteam =  entry.getValue();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while ((n = inputSteam.read(buf)) >= 0)
                    baos.write(buf, 0, n);
                byte[] content = baos.toByteArray();
                InputStream cloneInputSteam = new ByteArrayInputStream(content);
                return cloneInputSteam;
            }

        }
        throw new NullPointerException("No Execute Property input stream found");
    }

    public String getExecutePropertyFileName()
    {
        for(Map.Entry<String, InputStream> entry:filesInZip.entrySet())
        {
            if (entry.getKey().toLowerCase().endsWith(".properties"))
                return entry.getKey();

        }
        throw new NullPointerException("Property file name is null");
    }

    public InputStream getExecuteInputSteam()
    {
        for(Map.Entry<String, InputStream> entry:filesInZip.entrySet())
        {
            if (!entry.getKey().toLowerCase().endsWith(".properties"))
                return entry.getValue();

        }
        throw new NullPointerException("No Execute input stream found");
    }

    public String getExecuteFileName()
    {
        for(Map.Entry<String, InputStream> entry:filesInZip.entrySet())
        {
            if (!entry.getKey().toLowerCase().endsWith(".properties"))
                return entry.getKey();

        }
        throw new NullPointerException("Execute file name is null");
    }

    private void validateFilesInZip(final Set<String> filesInZip) throws ZipFileStepException
    {
        if (filesInZip.size() !=2)
            throw new ZipFileStepException("Zip Step contains "+filesInZip.size()+" files");
        int countOfPropertyFile = 0;
        for (String fileName :filesInZip)
        {
            if (fileName.toLowerCase().endsWith(".properties"))
            {
                countOfPropertyFile ++;
            }
        }
        if (countOfPropertyFile != 1)
            throw new ZipFileStepException("Zip Step contains "+countOfPropertyFile+" property files");

    }


    private boolean isPropertyFile(final File file)
    {
        if (file.getName().toLowerCase().endsWith(".properties"))
            return true;
        else
            return false;
    }

}
