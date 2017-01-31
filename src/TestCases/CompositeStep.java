package TestCases;

import ORTEExceptions.ZipFileStepException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static CommonUtil.FileReaderWithEncoding.readZipFiles;
/**
 * Created by JJMM on 2017/1/31.
 */
public abstract class CompositeStep extends AbstractStep
{
    protected final File executePropertyFile,executeFile;
    CompositeStep(final File stepFile, final TestCase testCase) throws FileNotFoundException,IOException, ZipFileStepException
    {
        super(stepFile,testCase);
        if (!stepFile.getName().contains(".zip"))
            throw new IllegalArgumentException("Not a zip file for composite steps");
        List<File> filesInZip = readZipFiles(stepFile);
        validateFilesInZip(filesInZip);

        // Get Step Files.
        File file1 = filesInZip.get(0);
        File file2 = filesInZip.get(1);
        if (isPropertyFile(file1))
        {
            executePropertyFile = file1;
            executeFile = file2;
        }
        else
        {
            executePropertyFile = file2;
            executeFile = file1;
        }

    }

    private void validateFilesInZip(List<File> filesInZip) throws ZipFileStepException
    {
        if (filesInZip.size() !=2)
            throw new ZipFileStepException("Zip Step contains "+filesInZip.size()+" files");
        int countOfPropertyFile = 0;
        for (File file :filesInZip)
        {
            if (file.getName().toLowerCase().contains(".properties"))
            {
                countOfPropertyFile ++;
            }
        }
        if (countOfPropertyFile != 1)
            throw new ZipFileStepException("Zip Step contains "+countOfPropertyFile+" property files");

    }


    private boolean isPropertyFile(final File file)
    {
        if (file.getName().contains(".properties"))
            return true;
        else
            return false;
    }

}
