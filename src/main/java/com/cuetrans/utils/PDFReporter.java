package com.cuetrans.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.testng.ITestNGMethod;

public class PDFReporter extends TestResultCount {
	
	
	
	
	// Hold the report font size.
	private final static float FONT_SIZE = 7f;

	private final static float HEADER_FONT_SIZE = 12f;

	// Hold the initial x position.
	private final static float X0 = 15f;

	// Hold the padding bottom of the document.
	private final static float PADDING_BOTTOM_OF_DOCUMENT = 30f;

	private static Logger LOGGER = Logger.getLogger(PDFReporter.class);

	private void createTestExectionChart(int[] contents) {
		JFreeChart chart;
		PieRenderer renderer;
		PiePlot plot;
		DefaultPieDataset dataset;
		//JFreeChartEntity url = null;
		

		// Specify the colors here
		Color[] colors = { new  Color( 0 , 150 , 0 ) , new Color( 150 , 0 , 0 ), new Color( 0 , 0 , 150 ) };

		// Defining the dataset
		dataset = new DefaultPieDataset();
		dataset.setValue("Passed", contents[0]);
		dataset.setValue("Failed", contents[1]);
		dataset.setValue("Skiped", contents[2]);
		//url.setURLText("file:///D:/GitHub/FrameworkPOM/reports/ATU%20Reports/index.html");
		// chart without header in it
		chart = ChartFactory.createPieChart3D("Test Execution Results Chart", dataset, true, true, false);
		plot = (PiePlot) chart.getPlot();
		
		// setting custom colors
		renderer = new PieRenderer(colors);
		renderer.setColor(plot, dataset);
		// setting label as percentage
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{2}", new DecimalFormat("0"),
				new DecimalFormat("0.00%"));
		plot.setLabelGenerator(generator);
		try {
			// save chart
			ChartUtilities.saveChartAsJPEG(new File( "./reports/pdfReport/executionchart.jpg"),
					chart, 550, 300);
		} catch (Exception e) {
			System.out.println("Exception while creating the chart");
		}
	}

	/*
	 * A simple renderer for setting custom colors for a pie chart.
	 */
	public static class PieRenderer {
		private Color[] color;

		public PieRenderer(Color[] color) {
			this.color = color;
		}

		public void setColor(PiePlot plot, DefaultPieDataset dataset) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Comparable> keys = dataset.getKeys();
			int aInt;
			for (int i = 0; i < keys.size(); i++) {
				aInt = i % this.color.length;
				plot.setSectionPaint(keys.get(i), this.color[aInt]);
			}
		}
	}

	/**
	 * This method for include the footer to the each page in pdf document.
	 *
	 * @param doc
	 *            Set the pdf document.
	 * @param reportName
	 *            Set the report name.
	 * @throws IOException
	 */
	/*
	 * @SuppressWarnings("rawtypes") private void addReportFooter(final PDDocument
	 * doc, final String reportName) {
	 * 
	 * PDPageContentStream footercontentStream = null; try {
	 * 
	 * List pages = doc.getDocumentCatalog().getAllPages();
	 * 
	 * for (int i = 0; i < pages.size(); i++) { PDPage page = ((PDPage)
	 * pages.get(i)); footercontentStream = new PDPageContentStream(doc, page, true,
	 * true); footercontentStream.beginText();
	 * footercontentStream.setFont(PDType1Font.HELVETICA_BOLD, HEADER_FONT_SIZE);
	 * footercontentStream.moveTextPositionByAmount(X0,
	 * (PDPage.PAGE_SIZE_A4.getLowerLeftY() + PADDING_BOTTOM_OF_DOCUMENT));
	 * footercontentStream.drawString(reportName);
	 * footercontentStream.moveTextPositionByAmount((PDPage.PAGE_SIZE_A4.
	 * getUpperRightX() / 2), (PDPage.PAGE_SIZE_A4.getLowerLeftY()));
	 * footercontentStream.drawString((i + 1) + " - " + pages.size());
	 * footercontentStream.endText(); footercontentStream.close(); } } catch (final
	 * IOException exception) { throw new RuntimeException(exception); } finally {
	 * if (footercontentStream != null) { try { footercontentStream.close(); } catch
	 * (final IOException exception) { throw new RuntimeException(exception); } } }
	 * 
	 * }
	 */

	/**
	 * This method created a document with 2 pages.
	 *
	 * @return Document with consist of two pages.
	 * @throws IOException
	 */
	public PDDocument createDocumentWithMultiplePage(String[][] executionOverview, String[][] executionSumary,
			int[] params) {

		LOGGER.info("Started PDF report creation");
		PDPageContentStream contentStream = null;
		PDDocument doc = null;
		PDPage page = null;

		try {
			// create empty document.
			doc = new PDDocument();

			// create fist page.
			page = new PDPage();
			page.setMediaBox(PDPage.PAGE_SIZE_A4);
			doc.addPage(page);
			contentStream = new PDPageContentStream(doc, page);

			contentStream.setStrokingColor(new Color(138, 185, 209));

			// File pdfLocation = getLatestReport();
			File pdfLocation = new File("./reports/pdfReport/");

			// Adding header
			addPageHeader(contentStream, doc);

			// Adding section1
			addTestExecutionOverview(contentStream, doc, page, executionOverview);

			// Adding section2
			addExecutionChartStatus(contentStream, doc, params);

			// Adding section3
			addExecutionSummary(contentStream, page, executionSumary);

			addDetailedReport(contentStream, pdfLocation);

			contentStream.close();

			File pdfFile = new File(pdfLocation + "/"+prop.getProperty("pdfFileName"));

			if (pdfFile.exists()) {
				pdfFile.delete();
			}

			doc.save(new FileOutputStream(pdfFile));
			LOGGER.info("PDF file location" + pdfFile.getAbsolutePath());

		} catch (final IOException exception) {
			LOGGER.info("Something went wrong in pdf creation. Error might be due to: " + exception.getMessage());
			throw new RuntimeException(exception);

		} catch (COSVisitorException e) {
			LOGGER.info("Something went wrong in pdf creation. Error might be due to: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (contentStream != null) {
				try {
					contentStream.close();

				} catch (final IOException exception) {
					LOGGER.info(
							"Something went wrong in pdf creation. Error might be due to: " + exception.getMessage());
					throw new RuntimeException(exception);
				}
			}
		}
		return doc;
	}

	private void addDetailedReport(PDPageContentStream contentStream, File pdfLocation) {
		fillRectangle(contentStream, 100, 100, 460, 20);

		writeToPDF(contentStream, 100, 110, "Detailed Report");

		LOGGER.info("PDF file location" + pdfLocation);

		try {
			contentStream.setNonStrokingColor(Color.BLACK);
			/*contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
			contentStream.moveTextPositionByAmount(100, 80);
			//String propertyFileName = "./resources/MasterConfiguration.properties";
			//HashMap<String, String> configPropertyMap = new PropertyUtils().getMap(propertyFileName);
			String reportUrl = "http:/192.168.7.191:8080";
			if (reportUrl.equals("")) {
				LOGGER.info("Problem getting jenkins url. Please check jenkins admin");
				reportUrl = "Problem getting jenkins url. Please check jenkins admin";
			}
			// String newLine = System.getProperty("line.separator");
			
			//contentStream.drawString(reportUrl);
			contentStream.endText();*/
			//String note1 = "Note: In case if you experience any difficulties in accessing the Detailed Report, ";
			//String note2 = "please reach out to the QA Manager";
			//String note3 = "Failed Test Cases : ";
			
			/*contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			contentStream.moveTextPositionByAmount(100, 80);
			contentStream.drawString(note1);
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			contentStream.moveTextPositionByAmount(100, 70);
			contentStream.drawString(note2);
			contentStream.endText();
			*/
			//fillRectangle(contentStream, 100, 30, 460, 10);

			writeToPDF(contentStream, 100, 80, "Failed Tests : ");
			/*
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
			contentStream.moveTextPositionByAmount(100, 30);
			contentStream.drawString(note3);
			contentStream.endText();
			*/
			
			if(!failedTests.isEmpty()) {
				int sNo = 1 ;
				int yAxis = 70 ;
				
			for( ITestNGMethod eachFailed : failedTests ) {
			
				String note4 = eachFailed.getMethodName();
				
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
				contentStream.moveTextPositionByAmount(100, yAxis);
				contentStream.drawString(sNo+". "+note4);
				contentStream.endText();
				
				sNo++;
				yAxis = yAxis-10;
				
			}
			
			}
			else if(failedTests.isEmpty()) {
				int yAxis = 70 ;
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
				contentStream.moveTextPositionByAmount(100, yAxis);
				contentStream.drawString("N/A");
				contentStream.endText();
				
			}
			
			writeToPDF(contentStream, 300, 80, "Skipped Tests : ");
			if(!skippedTests.isEmpty()) {
			int sNo = 1 ;
			int yAxis = 70 ;
			for( ITestNGMethod eachskiped : skippedTests ) {
				
				
				String note5 = eachskiped.getMethodName();
				
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
				contentStream.moveTextPositionByAmount(300, yAxis);
				contentStream.drawString(sNo+". "+note5);
				contentStream.endText();
				
				sNo++;
				yAxis = yAxis-10;
				
			}
			}else if(skippedTests.isEmpty()) {
				int yAxis = 70 ;
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
				contentStream.moveTextPositionByAmount(300, yAxis);
				contentStream.drawString("N/A");
				contentStream.endText();
				
			}
			
			
		} catch (IOException e) {
			LOGGER.info("Something went wrong in pdf creation. Error might be due to: " + e.getMessage());
		}
	}

	/*
	 * private File getLatestReport() { // File directory = new
	 * File("C://FAF//reports");
	 * 
	 * File directory = new File("reports");
	 * 
	 * // get all the files from a directory File[] fList = directory.listFiles();
	 * 
	 * File file = fList[0];
	 * 
	 * for (int i = 1; i < fList.length; i++) { file = compare(fList[i], file); }
	 * return file; }
	 */

	/*
	 * private File compare(File f1, File f2) { long result = f2.lastModified() -
	 * f1.lastModified(); if (result > 0) { return f2; } else if (result < 0) {
	 * return f1; } else { return f1; } }
	 */

	private void addPageHeader(PDPageContentStream contentStream, PDDocument doc)
			throws IOException, FileNotFoundException {
		PDXObjectImage image;

		writeToPDF(contentStream, 200, 780, "Test Summary Report");
		/*
		 * BufferedImage img = ImageIO.read( new File(System.getProperty("user.dir") +
		 * "//resources//files//BCT.png") );
		 * 
		 * int w = img.getWidth(); int h = img.getHeight(); int inset = 40;
		 */

		image = new PDJpeg(doc,
				new FileInputStream(("./resources/logo.png")));

		contentStream.setNonStrokingColor(Color.BLACK);

		contentStream.drawImage(image, 500, 760);
	}

	private void addTestExecutionOverview(PDPageContentStream contentStream, PDDocument doc, PDPage page,
			String[][] executionOverviewArray) {
		 fillRectangle(contentStream, 100, 720, 460, 20);

		 writeToPDF(contentStream, 100, 730, "Execution Report");

		headerTable(page, contentStream, 700, 100, executionOverviewArray);
	}

	private void addExecutionSummary(PDPageContentStream contentStream, PDPage page, String[][] executionSummary) {

		fillRectangle(contentStream, 100, 300, 460, 20);

		writeToPDF(contentStream, 100, 310, "Execution Summary");

		headerTable(page, contentStream, 280, 100, executionSummary);

	}

	private void addExecutionChartStatus(PDPageContentStream contentStream, PDDocument doc, int[] params) {
		PDXObjectImage image;

		fillRectangle(contentStream, 100, 540, 460, 20);

		try {
			writeToPDF(contentStream, 100, 550, "Execution Status");

			if (params[0] == params[1] && params[1] == params[2] && params[1] == 0) {
				writeToPDF(contentStream, 100, 500, "No tests were run. Please check excel sheet or testng.xml to configure tests");
			} else {
				createTestExectionChart(params);
				image = new PDJpeg(doc,
						new FileInputStream("./reports/pdfReport/executionchart.jpg"));

				contentStream.drawXObject(image, 100, 330, image.getWidth() - 150, image.getHeight() - 100);
			}

			
		} catch (IOException e) {
			LOGGER.info("Something went wrong in addExecutionChartStatus. Error might be due to: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void fillRectangle(PDPageContentStream contentStream, float x, float y, float width, float height) {
		try {
			contentStream.setNonStrokingColor(Color.gray);
			contentStream.fillRect(x, y, width, height);
			contentStream.setNonStrokingColor(Color.WHITE);
		} catch (IOException e) {
			LOGGER.error("Error in Filling Rectangle" + e.getMessage());
			// System.out.println("Error in Filling Rectangle"+ e.getMessage());

		}

	}

	private void writeToPDF(PDPageContentStream contentStream, float x, float y, String data) {
		try {
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, HEADER_FONT_SIZE);
			contentStream.moveTextPositionByAmount(x, y);
			contentStream.drawString(data);
			contentStream.endText();
		} catch (IOException e) {
			LOGGER.error("Error in pdf writing" + e.getMessage());
		}
	}

	private static void headerTable(PDPage page, PDPageContentStream contentStream, float y, float margin,
			String[][] content) {
		final int rows = content.length;
		final int cols = content[0].length;
		final float rowHeight = 20f;
		final float tableWidth = page.findMediaBox().getWidth() - (2 * margin);
		final float tableHeight = rowHeight * rows;
		final float colWidth = tableWidth / (float) cols;
		final float cellMargin = 5f;

		try {
			contentStream.setNonStrokingColor(Color.BLACK);

			// draw the rows
			float nexty = y;
			for (int i = 0; i <= rows; i++) {

				contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the tKT ext
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			float textx = margin + cellMargin;
			float texty = y - 15;
			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;
				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}
		} catch (IOException e) {
			LOGGER.error("Error in headerTable: " + e.getMessage());
		}
	}

	public static void createPDFReport() throws InterruptedException {
		File log4jfile = new File("./src/main/resources/log4j.properties");
		PropertyConfigurator.configure(log4jfile.getAbsolutePath());
	/*	int tcount = 2;
		int passed = 1;
		int failed = 1;
		int skiped = 0;
		*/
		
		totalCount = totalTests.size();
		passedCount = passedTests.size();
		failedCount = failedTests.size();
		skippedCount = skippedTests.size();

		
		
		/*System.out.println("Counts : " + totalCount + " , " + passedCount + " , "  + failedCount  + " , " + skippedCount);
	
		
		System.out.println("Failed Count inside PDF : " + prop.getProperty("FailedTestCount"));
		*/
		String[][] executionSummaryArray = {

				{ "Test Scenarios Executed:", ""+totalCount  }, { "Passed:", ""+passedCount }, { "Failed:", ""+failedCount }, { "Skipped:", ""+skippedCount },
				{ "Execution Start Time", prop.getProperty("StartTime") }, { "Execution End Time", prop.getProperty("EndTime") },
				{ "Total Execution Time", prop.getProperty("ExecutionTime") } };

		String[][] executionOverviewArray = { { "Date:", new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date()) }, { "Release:", prop.getProperty("Release") },
				{ "Type of Testing:", prop.getProperty("Category") }, { "Automation Type:", prop.getProperty("AutomationType") }, { "Application Name:", prop.getProperty("ApplicationName") },
				{ "Environment:", prop.getProperty("Environment") } };

		int[] params = { ( passedCount  * 100  / totalCount ) , ( failedCount * 100 / totalCount )  , ( skippedCount * 100 / totalCount )  };

		PDFReporter executionSummary = new PDFReporter();

		executionSummary.createDocumentWithMultiplePage(executionOverviewArray, executionSummaryArray, params);
	}

	public static float getPaddingBottomOfDocument() {
		return PADDING_BOTTOM_OF_DOCUMENT;
	}

	public static float getFontSize() {
		return FONT_SIZE;
	}

	public static float getX0() {
		return X0;
	}
}
