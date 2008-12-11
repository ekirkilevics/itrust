package edu.ncsu.csc.itrust.action;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import edu.ncsu.csc.itrust.beans.DiagnosisCount;
import edu.ncsu.csc.itrust.beans.forms.EpidemicForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.EpidemicDAO;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.EpidemicFormValidator;

/**
 * Handles creating the epidemic detection image (graph) Used by epidemicGraphImage.jsp
 * 
 * @author laurenhayward
 * 
 */
public class EpidemicDetectionImageAction {
	private EpidemicDAO epDAO;

	/**
	 * 
	 * @param factory
	 */
	public EpidemicDetectionImageAction(DAOFactory factory) {
		this.epDAO = factory.getEpidemicDAO();
	}

	/**
	 * Creates the graph based on the EpidemicForm param
	 * 
	 * @param form
	 * @return a BufferedImage
	 * @throws FormValidationException
	 * @throws DBException
	 * @throws ParseException
	 */
	public BufferedImage createGraph(EpidemicForm form) throws FormValidationException, DBException,
			ParseException {
		new EpidemicFormValidator().validate(form);
		JFreeChart chart = ChartFactory.createBarChart3D("Diagnosis Count for ICD Range "
				+ form.getIcdLower() + " to " + form.getIcdUpper(), "Category Axis Label",
				"Value Axis Label", createDataSet(form), PlotOrientation.VERTICAL, true, true, true);
		return chart.createBufferedImage(700, 500);
	}

	/**
	 * Creates a data set from the given form
	 * 
	 * @param form the form to be translated
	 * @return a set containing all the data from the form
	 * @throws DBException
	 * @throws ParseException
	 * @throws FormValidationException
	 */
	
	private CategoryDataset createDataSet(EpidemicForm form) throws DBException, ParseException,
			FormValidationException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<DiagnosisCount> dCounts = epDAO.getDiagnosisCounts(Double.valueOf(form.getIcdLower()), Double
				.valueOf(form.getIcdUpper()), form.getZip(), State.parse(form.getState()),
				new SimpleDateFormat("MM/dd/yyyy").parse(form.getDate()), Integer
						.valueOf(form.getWeeksBack()));
		int week = Integer.valueOf(form.getWeeksBack());
		for (DiagnosisCount dCount : dCounts) {
			dataset.addValue(dCount.getNumInRegion(), "Regional", week + " weeks ago");
			dataset.addValue(dCount.getNumInState(), "State", week + " weeks ago");
			dataset.addValue(dCount.getNumInTotal(), "Total", week + " weeks ago");
			week--;
		}
		return dataset;
	}
}
