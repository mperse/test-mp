package databox.importer.services.core.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.utils.DateFormatUtil;

public class DataboxWrapper {
	private static final Logger logger = LoggerFactory.getLogger(DataboxWrapper.class);
	Databox service = null;

	String reportLocation = "logs/databoxReport.txt";

	public DataboxWrapper(String pushToken) {
		service = new Databox(pushToken);
	}

	public void pushDataAndLog(List<KPI> data) throws Exception {
		try {
			service.push(data);
			printStatistics(data, null);
		} catch (Exception e) {
			logger.error("Error on data push: " + e.getLocalizedMessage(), e);
			printStatistics(data, e);
			throw e;
		}
	}

	private void printStatistics(List<KPI> data, Exception e) {
		try (FileWriter writter = new FileWriter(reportLocation, true)) {
			String dtbRecordWithTime = prepareData(data, e);
			writter.write(dtbRecordWithTime);
			writter.flush();
		} catch (Exception e1) {
			logger.error("Error on data push: " + e1.getLocalizedMessage(), e1);
		}
	}

	private String prepareData(List<KPI> data, Exception e) {
		StringBuilder sb = new StringBuilder(DateFormatUtil.SDF.format(new Date()));
		sb.append(";numKPI:");
		sb.append(data.size());
		sb.append(";data:");
		sb.append(data);
		sb.append(";status:");
		sb.append(e == null ? "OK" : "ERROR");
		if (e != null) {
			sb.append(";error: ").append(e.getLocalizedMessage());
		}
		sb.append(System.lineSeparator());
		return sb.toString();
	}

	public void clearReport() throws Exception {
		try (PrintWriter writter = new PrintWriter(reportLocation)) {
			writter.print("");
			writter.flush();
		} catch (Exception e) {
			logger.error("Error on data push: " + e.getLocalizedMessage(), e);
			throw e;
		}
	}

	public int getReportRowLines() throws Exception {
		int noOfLines = 0;
		if (new File(reportLocation).exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(reportLocation))) {
				while (reader.readLine() != null) {
					noOfLines++;
				}
			}
		}
		return noOfLines;
	}

	public void setReportLocation(String reportLocation) {
		this.reportLocation = reportLocation;
	}

}
