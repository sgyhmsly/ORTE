import java.nio.file.Files
import java.nio.file.StandardCopyOption

void copyFileUsingJava7Files(File source, File dest)
        throws IOException {
    Files.copy(source.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);

}


File sourceXML = new File(outputPath+"/01_derbytoOTA_response.xml");
File destXML = new File(assertPath+"/01_derbytoOTA_response.xml");
copyFileUsingJava7Files(sourceXML,destXML);