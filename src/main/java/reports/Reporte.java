package reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;


public class Reporte {

	public void reporte(String xml) throws DRException, ParserConfigurationException, SAXException, IOException
	{
		build(xml);

	}
	
	private void build(String xml) throws DRException, ParserConfigurationException, SAXException, IOException {
		//AggregationSubtotalBuilder<Integer> quantityRight;
		//AggregationSubtotalBuilder<Integer> quantityWrong;
		//AggregationSubtotalBuilder<Integer> quantityExceptions;
		
		try {
			
			JRXmlDataSource dataSource = new JRXmlDataSource(xml, "/testResults/result");
			
			//Styles
			StyleBuilder boldStyle         = stl.style().bold(); 
			StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
			StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle).setBorder(stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY).setFontSize(10);
			StyleBuilder columnStyle  = stl.style().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);

			
			FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
			StyleBuilder titleStyle = stl.style(boldCenteredStyle).setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(15);


			//para leer el xml
			Xml_details read_xml= new Xml_details();
			String[] errores= read_xml.details(xml);
			
			TextColumnBuilder<String> relativePageName = col.column("Test", "relativePageName", type.stringType()).setFixedColumns(10);
			TextColumnBuilder<String> typeName = col.column("Type", "counts/ignores", type.stringType()).setStyle(columnStyle);
			TextColumnBuilder<Integer> right = col.column("Pass", "counts/right", type.integerType()).setStyle(columnStyle);
			TextColumnBuilder<Integer> exceptions = col.column("Exceptions", "counts/exceptions", type.integerType());
			TextColumnBuilder<Integer> wrong = col.column("Fail","counts/wrong", type.integerType());
			TextColumnBuilder<String> content= col.column("Details", new ExpressionColumn(errores));
			
			//Color conditions for columns
			ConditionalStyleBuilder right_style = stl.conditionalStyle(cnd.greater(right, 0)).setForegroundColor(new Color(0, 190, 0)).bold();
			ConditionalStyleBuilder wrong_style = stl.conditionalStyle(cnd.greater(wrong, 0)).setForegroundColor(new Color(190, 0, 0)).bold();
			ConditionalStyleBuilder exceptions_style = stl.conditionalStyle(cnd.greater(exceptions, 0)).setForegroundColor(new Color(255,215,0)).bold();
			
			StyleBuilder rightStyle = stl.style().conditionalStyles(right_style);
			StyleBuilder wrongStyle = stl.style().conditionalStyles(wrong_style);
			StyleBuilder exceptionsStyle = stl.style().conditionalStyles(exceptions_style);

			right=right.setStyle(rightStyle).setFixedColumns(4);
			wrong=wrong.setStyle(wrongStyle).setFixedColumns(4);
			exceptions=exceptions.setStyle(exceptionsStyle).setFixedColumns(6);
			
			//Series Colors of BarChar
			Map<String, Color> seriesColors = new HashMap<String, Color>();
			seriesColors.put("Pass", new Color(0,190,0));
			seriesColors.put("Fail", new Color(190, 0, 0));
			seriesColors.put("Exceptions", new Color(255,215,0));
			
			report()
				.setColumnStyle(columnStyle)
				.setColumnTitleStyle(columnTitleStyle)
				.columns(relativePageName, right, wrong, exceptions, content)				
				.title(//title of the report
						Components.text("City Index iPhone Report")
						.setHorizontalAlignment(HorizontalAlignment.CENTER)
						.setStyle(titleStyle)
						.setRows(3))
				.pageFooter(Components.pageXofY())
				.subtotalsAtSummary(sbt.sum(right), sbt.sum(wrong), sbt.sum(exceptions))
				.summary( //barchart
						cht.bar3DChart()
						.seriesColorsByName(seriesColors)
						.setTitle("Bar Chart")
						.setTitleFont(boldFont)
						.setCategory(typeName)
						.series(cht.serie(right),cht.serie(wrong),cht.serie(exceptions))
				)
				.setDataSource(dataSource)
				.toPdf(new FileOutputStream("C:\\Reporte.pdf"))
				.show();
			


		} catch (DRException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	
}

