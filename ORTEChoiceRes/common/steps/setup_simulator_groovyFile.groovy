//import groovy.text.SimpleTemplateEngine
import org.dom4j.DocumentException
import org.dom4j.DocumentHelper
import org.jaxen.JaxenException
import org.jaxen.SimpleNamespaceContext
import org.jaxen.XPath
import org.jaxen.dom4j.Dom4jXPath
import org.dom4j.Element
import org.dom4j.io.SAXReader
import org.dom4j.Document
import org.dom4j.io.XMLWriter;
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import javax.swing.JOptionPane;




public class XMLUtil {
    private static final HashMap<String, String> NAMESPACES = new HashMap<String, String>()
    static {
        NAMESPACES.put("ns1", "http://www.opentravel.org/OTA/2003/05");
        NAMESPACES.put("soapenv","http://schemas.xmlsoap.org/soap/envelope/");
        NAMESPACES.put("soap","http://schemas.xmlsoap.org/soap/envelope/");
    }

    public static List<Element> getElements(Document document,
                                            String xpathString) throws JaxenException {
        XPath xpath = new Dom4jXPath(xpathString)
        xpath.setNamespaceContext(new SimpleNamespaceContext(NAMESPACES))
        return xpath.selectNodes(document)
    }

    public static Element getElement(Document document, String xpathString)
            throws JaxenException {
        XPath xpath = new Dom4jXPath(xpathString)
        xpath.setNamespaceContext(new SimpleNamespaceContext(NAMESPACES))
        return (Element) xpath.selectSingleNode(document)
    }

    public static String getElementAttribute(String attribute,
                                             Document document, String xpathString) throws JaxenException {
        XPath xpath = new Dom4jXPath(xpathString)
        xpath.setNamespaceContext(new SimpleNamespaceContext(NAMESPACES))
        Element element = (Element) xpath.selectSingleNode(document)
        if (element == null) {
            return null
        }
        return element.attributeValue(attribute)
    }

    public static String getElementAttribute(String attribute, Element element,
                                             String xpathString) throws JaxenException, DocumentException {
        SAXReader saxReader = new SAXReader()
        Document document = saxReader.read(new StringReader(element.asXML()))
        return getElementAttribute(attribute, document, xpathString)
    }
}


public String process(String request){

    String oriFile
    if(request.toLowerCase().contains("status=\"commit\""))
    {
        try {

            java.io.File file = new java.io.File("d:\\Tomcat_Conf\\arte\\arts\\choiceres\\common\\steps\\datafiles\\CRStoderby_response_RAD.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(file);
            oriFile = doc.asXML();
            Element e = XMLUtil.getElement(doc, "/soapenv:Envelope/soapenv:Body/ns1:OTA_HotelResNotifRS/ns1:HotelReservations/ns1:HotelReservation/ns1:ResGlobalInfo/ns1:HotelReservationIDs/ns1:HotelReservationID[1]")
            String ResID_Value = e.attribute("ResID_Value").getValue();
            ResID_Value = (Integer.parseInt(ResID_Value)+1).toString();
            e.attribute("ResID_Value").setValue(ResID_Value);

            try {

                XMLWriter writer = new XMLWriter(
                        new FileWriter( "d:\\Tomcat_Conf\\arte\\arts\\choiceres\\common\\steps\\datafiles\\CRStoderby_response_RAD.xml" )
                );
                writer.write( doc );
                writer.close();

            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }
        catch(Exception ex)
        {
            ;
        }
    }
    else if (request.toLowerCase().contains("resstatus=\"cancel\""))
    {
        try {


            java.io.File file = new java.io.File("d:\\Tomcat_Conf\\arte\\arts\\choiceres\\common\\steps\\datafiles\\CRStoderby_cancel_response_RAD.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(file);
            oriFile = doc.asXML();
            Element e = XMLUtil.getElement(doc, "/soapenv:Envelope/soap:Body/ns1:OTA_HotelResNotifRS/ns1:HotelReservations/ns1:HotelReservation/ns1:ResGlobalInfo/ns1:HotelReservationIDs/ns1:HotelReservationID[3]")
            String ResID_Value = e.attribute("ResID_Value").getValue();
            ResID_Value = (Integer.parseInt(ResID_Value)+1).toString();
            e.attribute("ResID_Value").setValue(ResID_Value);

            e = XMLUtil.getElement(doc,"/soapenv:Envelope/soapenv:Body/ns1:OTA_HotelResNotifRS/ns1:HotelReservations/ns1:HotelReservation/ns1:ResGlobalInfo/ns1:HotelReservationIDs/ns1:HotelReservationID[1]");
            String derby_ID = e.attribute("ResID_Value").getValue();
            derby_ID = (Integer.parseInt(derby_ID)+1).toString();
            e.attribute("ResID_Value").setValue(derby_ID);

            try {

                XMLWriter writer = new XMLWriter(
                        new FileWriter( "d:\\Tomcat_Conf\\arte\\arts\\choiceres\\common\\steps\\datafiles\\CRStoderby_cancel_response_RAD.xml" )
                );
                writer.write( doc );
                writer.close();

            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }
        catch(Exception ex)
        {
            ;
        }
    }



    return oriFile;

}

//String fileContents = new File("D:\\Tomcat_Conf\\arte\\arts\\choiceres\\common\\steps\\datafiles\\CRStoderby_cancel_response_RAD.xml").getText('UTF-8')

//return process(fileContents);
return process(request);