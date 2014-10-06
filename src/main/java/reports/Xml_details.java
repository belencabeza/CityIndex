/**
 * 
 */
package reports;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author mcabeza
 *
 */
public class Xml_details{
	
	
	
	public String[] details(String xml) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);
		doc.getDocumentElement().normalize();
		NodeList nodeLst = doc.getElementsByTagName("result");
		String[] errores = new String[nodeLst.getLength()];
		String patternString="expected</span><hr>(.*?) <span class";
		String patternString2="fit_stacktrace\">(.*?)</div></pre>";
		
		for (int s = 0; s < nodeLst.getLength(); s++) 
		{
		    Node fstNode = nodeLst.item(s);
		    Element fstElmnt = (Element) fstNode;
		    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("content");
		    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		    NodeList fstNm = fstNmElmnt.getChildNodes();
		    String text=(String) fstNm.item(0).getNodeValue();
		    Pattern pattern = Pattern.compile(patternString);
		    Pattern pattern2= Pattern.compile(patternString2);
		    Matcher matcher = pattern.matcher(text);
		    Matcher matcher2= pattern2.matcher(text);
		    
		    if(matcher.find()){
		    	errores[s]=matcher.group(1).toString();
		    }
		    else if(matcher2.find())
		    {
		    	int num=matcher2.group(1).toString().indexOf("at");
		    	String error=matcher2.group(1).toString().substring(0, num);
		    	errores[s]=error;
		    }
		    	else
		    	{
		    		errores[s]="Pass";
		    	}
		}
		return errores;
	}

}
