public String process(String request)
{
    String fileContents = new File("d:\\Tomcat_Conf\\arte\\arts\\choiceres\\common\\steps\\confirm_fail.xml").getText('UTF-8')
    return  fileContents;

}