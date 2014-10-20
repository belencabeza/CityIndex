package CI.City_Index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.dynamicreports.report.exception.DRException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import email.SendEmail;
import reports.Reporte;
//import testlink.CreateBuild;
//import testlink.api.java.client.TestLinkAPIException;


public class Main{
	public static void main(String[] args) throws SAXException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, DRException {
		
		   try {
			 
			//new CreateBuild();
			//CreateBuild.createBuild("Automation 4");
			URL url = new URL("http://localhost:8085/CityIndexSuite?suite&format=xml");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	 
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
	 
			String output;
			StringBuilder sb = new StringBuilder();
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			output=sb.toString();
			
			//Convert string to XML file
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(output)));
			
			Source source = new DOMSource(document);
			Result resultado = new StreamResult(new java.io.File("C:\\resultado.xml"));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, resultado);
			
			Reporte reporte=new Reporte();
			reporte.reporte("C:\\resultado.xml");
			
			//new SendEmail().send();
	 
			conn.disconnect();
	 
		  } catch (MalformedURLException e) {
	 
			e.printStackTrace();	
	 
		  } catch (IOException e) {
	 
			e.printStackTrace();
	 
		  }
	 
		}

}

