package databox.importer.services.core.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataboxWrapper {

	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

	private static final Logger logger = LoggerFactory.getLogger(DataboxWrapper.class);
	Databox service = null;

	String reportLocation = "logs/databoxReport.txt";

	public DataboxWrapper(String pushToken) {
		service = new Databox(pushToken);
	}

	public void pushDataAndLog(List<KPI> data) throws Exception {
		try (OutputStream outputStream = new FileOutputStream(reportLocation)) {
			service.push(data);
			String dtbRecordWithTime = SDF.format(new Date()) + " -> " + data + System.lineSeparator();
			outputStream.write(dtbRecordWithTime.getBytes());
			outputStream.flush();
		} catch (Exception e) {
			logger.error("Error on data push: " + e.getLocalizedMessage(), e);
			throw e;
		}
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
