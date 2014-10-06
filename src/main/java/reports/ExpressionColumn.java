package reports;

import java.util.Arrays;
import java.util.List;

import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.definition.ReportParameters;

class ExpressionColumn extends AbstractSimpleExpression<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> error=null;
	public ExpressionColumn(String[] errores) {
		error= Arrays.asList(errores);
	}

	public String evaluate(ReportParameters reportParameters) {
		
		//Locale locale=null;
		
		return error.get(reportParameters.getReportRowNumber()-1);
		
	}
}
