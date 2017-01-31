/**
 * Created by DT173 on 2026/10/9.
 */
import org.dom4j.DocumentException
import org.dom4j.DocumentHelper
import org.jaxen.JaxenException
import org.jaxen.SimpleNamespaceContext
import org.jaxen.XPath
import org.jaxen.dom4j.Dom4jXPath
import org.dom4j.Element
import org.dom4j.io.SAXReader
import org.dom4j.Document
import org.dom4j.io.XMLWriter

import javax.swing.text.ElementIterator;
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import java.text.DecimalFormat;
public class XMLUtil {
    private static final HashMap<String, String> NAMESPACES = new HashMap<String, String>()
    static {
        NAMESPACES.put("ns1", "http://www.opentravel.org/OTA/2003/05");
        NAMESPACES.put("soapenv","http://schemas.xmlsoap.org/soap/envelope/");
        NAMESPACES.put("soap11","http://schemas.xmlsoap.org/soap/envelope/");
        NAMESPACES.put("dft","http://www.derbysoft.com/dswitch/rac");
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





def generateDoc(String filePath)
{
    try {
        java.io.File file = new java.io.File(filePath);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file);
        return doc;
    }
    catch(Exception e)
    {
        return null;
    }
}




class roomStay
{
    public roomStay()
    {
        AmountBeforeTax = AmountAfterTax = RoomTypeCode = RatePlanCode = RoomCount = "";
    }

    String getRoomTypeCode() {
        return RoomTypeCode
    }

    void setRoomTypeCode(String roomTypeCode) {
        RoomTypeCode = roomTypeCode
    }

    String getRatePlanCode() {
        return RatePlanCode
    }

    void setRatePlanCode(String ratePlanCode) {
        RatePlanCode = ratePlanCode
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        def roomStay = (roomStay) o

        if (RatePlanCode != roomStay.RatePlanCode) return false
        if (RoomTypeCode != roomStay.RoomTypeCode) return false

        return true
    }

    int hashCode() {
        int result
        result = (RoomTypeCode != null ? RoomTypeCode.hashCode() : 0)
        result = 31 * result + (RatePlanCode != null ? RatePlanCode.hashCode() : 0)
        return result
    }

    String getAmountBeforeTax() {
        return AmountBeforeTax
    }

    void setAmountBeforeTax(String amountBeforeTax) {
        AmountBeforeTax = amountBeforeTax
    }

    String getAmountAfterTax () {
        return AmountAfterTax
    }

    void setAmountAfterTax(String amountAfterTax) {
        AmountAfterTax = amountAfterTax
    }
    private String AmountBeforeTax;
    private String AmountAfterTax;
    private String RoomTypeCode;
    private String RatePlanCode;

    String getRoomCount() {
        return RoomCount
    }

    void setRoomCount(String roomCount) {
        RoomCount = roomCount
    }
	
	   int getRoomDuration() {
        return roomDuration
    }

    void setRoomDuration(int roomDuration) {
        this.roomDuration = roomDuration
    }
    private int roomDuration;

    private String RoomCount;

}


def getOriginalPrice()
{
    String filePath = "d:\\Tomcat_Conf\\simulator\\dispatch\\_checkavaliability.xml";
    def doc = generateDoc(filePath);
    Element e = XMLUtil.getElement(doc, "/soap11:Envelope/soap11:Body/dft:AvailabilityResponse/dft:Availability/dft:Hotels/dft:Hotel/dft:RoomStays");
    def roomStays = e.elements("RoomStay");
    def roomStayList = [];
    int i = 1;
    for (def roomStayIT = roomStays.iterator();roomStayIT.hasNext();)
    {
        def t_roomStayObj = roomStayIT.next();
        String t_RoomTypeCode = t_roomStayObj.attribute("RoomTypeCode").getValue();
        String t_RatePlanCode = t_roomStayObj.attribute("RatePlanCode").getValue();
        //roomStayIT.n
        def t_roomStay = new roomStay();
        t_roomStay.roomTypeCode = t_RoomTypeCode;
        t_roomStay.ratePlanCode = t_RatePlanCode;


        Element e_roomRate = XMLUtil.getElement(doc, "/soap11:Envelope/soap11:Body/dft:AvailabilityResponse/dft:Availability/dft:Hotels/dft:Hotel/dft:RoomStays/dft:RoomStay["+i+"]/dft:RoomRates/dft:RoomRate/dft:Rate");
        String valueAmountBeforeTax = e_roomRate.attribute("AmountBeforeTax").getValue();
        String valueAmountAfterTax = e_roomRate.attribute("AmountAfterTax").getValue();
        t_roomStay.amountBeforeTax = valueAmountBeforeTax;
        t_roomStay.amountAfterTax = valueAmountAfterTax;
        roomStayList.add(t_roomStay);

        i = i + 1;
    }




    return roomStayList;

}

def calculatePrice(def listRooms) {

    //String filePath = "d:\\Tomcat_Conf\\arte\\arts\\choiceres\\testsuites\\RAC_Request\\05_RAC_ChaiDan_Agoda_two_roomcount =1 one day room type same rateplan different\\runs\\choiceres_rac_req.xml";
    String filePath = runPath+"/choiceres_rac_req.xml";
    //String filePath = "d:\\Tomcat_Conf\\arte\\arts\\choiceres\\testsuites\\RAC_Request\\04_RAC_ChaiDan_Agoda_two_roomcount =1 one day\\runs\\choiceres_rac_req.xml";
    println filePath+"\n"
    def doc = generateDoc(filePath);
    Element e = XMLUtil.getElement(doc, "/soap11:Envelope/soap11:Body/dft:BookReservationRequest/dft:Reservation/dft:Hotel/dft:RoomStays");
    def roomStays = e.elements("RoomStay");
    def requestRooms = [];
    String t_Start = XMLUtil.getElementAttribute("Start",doc,"/soap11:Envelope/soap11:Body/dft:BookReservationRequest/dft:Reservation/dft:Hotel/dft:RoomStays/dft:RoomStay[1]/dft:DateSpan");
    String t_End = XMLUtil.getElementAttribute("End",doc,"/soap11:Envelope/soap11:Body/dft:BookReservationRequest/dft:Reservation/dft:Hotel/dft:RoomStays/dft:RoomStay[1]/dft:DateSpan");
    def startdate = new Date().parse("yyyy-MM-dd", t_Start)
    def enddate = new Date().parse("yyyy-MM-dd", t_End)
    int t_roomDuring;
    def duration = enddate - startdate
    t_roomDuring = duration.intValue();


    for (def roomStayIT = roomStays.iterator(); roomStayIT.hasNext();) {
        def t_roomStayObj = roomStayIT.next();
        String t_RoomTypeCode = t_roomStayObj.attribute("RoomTypeCode").getValue();
        String t_RatePlanCode = t_roomStayObj.attribute("RatePlanCode").getValue();
        String t_RoomCount = t_roomStayObj.attribute("RoomCount").getValue();
        def t_roomStay = new roomStay();
        t_roomStay.setRoomTypeCode(t_RoomTypeCode);
        t_roomStay.setRatePlanCode(t_RatePlanCode);
        t_roomStay.setRoomCount(t_RoomCount);
        t_roomStay.setRoomDuration(t_roomDuring.intValue());
        requestRooms.add(t_roomStay);

    }

    // Start to compare the two lists, requestRooms, list Rooms
    for (def requestRoomIt = requestRooms.iterator();requestRoomIt.hasNext();)
    {
        def requestRoom = requestRoomIt.next();
        int index = listRooms.indexOf(requestRoom);
        if (index != -1)
        {
            String from_AmountBeforeTax = listRooms[index].getAmountBeforeTax();
            String from_roomCount = requestRoom.getRoomCount();
            int from_dateDuration = requestRoom.getRoomDuration();
            double beforeTaxValue = from_AmountBeforeTax.toDouble()*from_roomCount.toDouble()*from_dateDuration;
            requestRoom.setAmountBeforeTax(beforeTaxValue.toString());


            String from_AmountAfterTax = listRooms[index].getAmountAfterTax();
            double afterTaxValue = (from_AmountAfterTax.toDouble())*(from_roomCount.toDouble()*from_dateDuration);
            requestRoom.setAmountAfterTax(afterTaxValue.toString());
        }

    }

    // Add all price
    double sumBeforeTaxPrice = 0.0;
    double sumeAfterTaxPrice = 0.0;
    for (def requestRoomIt = requestRooms.iterator();requestRoomIt.hasNext();)
    {
        def requestRoom = requestRoomIt.next();
        sumBeforeTaxPrice += requestRoom.getAmountBeforeTax().toDouble();
        sumeAfterTaxPrice += requestRoom.getAmountAfterTax().toDouble();
    }

    def priceMap = new HashMap<Double,Double>();
    priceMap.put("AmountBeforeTax",sumBeforeTaxPrice);
    priceMap.put("AmountAfterTax",sumeAfterTaxPrice);
    return priceMap;

}

boolean validatePrice(def orginalPrice)
{
    // ********     FilePath need to be changed for every case
    //String filePath = "d:\\Tomcat_Conf\\arte\\output\\choiceres\\RAC_Request\\05_RAC_ChaiDan_Agoda_two_roomcount =1 one day room type same rateplan different\\01_derbytoOTA_response.xml";
    String filePath = outputPath+"/01_derbytoOTA_response.xml";
    //String filePath ="d:\\Tomcat_Conf\\arte\\output\\choiceres\\RAC_Request\\04_RAC_ChaiDan_Agoda_two_roomcount =1 one day\\01_derbytoOTA_response.xml";
    println filePath+"\n"
    def doc = generateDoc(filePath);
    Element e = XMLUtil.getElement(doc, "/soap11:Envelope/soap11:Body/dft:BookReservationResponse/dft:ReservationConfirmation/dft:Rate");
    float amount_BeforeTax = e.attribute("AmountBeforeTax").getValue().toFloat();
    float amount_AfterTax =  e.attribute("AmountAfterTax").getValue().toFloat();
    //DecimalFormat df = new DecimalFormat("######0.00");
    float expected_BeforeTax = orginalPrice["AmountBeforeTax"];
    float expected_AfterTax = orginalPrice["AmountAfterTax"];
    println "excepcted before tax:\t"+expected_BeforeTax
    println amount_BeforeTax
    println "excepcted after tax tax:\t"+expected_AfterTax
    println amount_AfterTax

    if(amount_BeforeTax != expected_BeforeTax)
        return false;
    if(amount_AfterTax != expected_AfterTax)
        return false;

    return true;
}



def listRooms = getOriginalPrice();
def orignalPrice = calculatePrice(listRooms);
return validatePrice(orignalPrice);
