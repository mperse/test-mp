package databox.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainExecutor {
	private static final Logger logger = LoggerFactory.getLogger(MainExecutor.class);

	public static void main(String[] args) {
		try {
			logger.info("Working ok");
		} catch (Exception e) {
			logger.error("Error while pushing data: " + e.getLocalizedMessage(), e);
		}
	}
}
