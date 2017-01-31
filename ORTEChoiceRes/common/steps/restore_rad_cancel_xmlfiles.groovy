import java.nio.file.Files
import java.nio.file.StandardCopyOption

void copyFileUsingJava7Files(File source, File dest)
        throws IOException {
    Files.copy(source.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);

}


File sourceXML = new File("d:\\Tomcat_Conf\\arte\\arts\\choiceres\\common\\steps\\datafiles\\CRStoderby_cancel_response_RAD_bak.xml");
File destXML = new File("d:\\Tomcat_Conf\\arte\\arts\\choiceres\\common\\steps\\datafiles\\CRStoderby_cancel_response_RAD.xml");
copyFileUsingJava7Files(sourceXML,destXML);