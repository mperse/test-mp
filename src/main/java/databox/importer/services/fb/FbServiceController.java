package databox.importer.services.fb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.constants.MainConstants;
import databox.importer.services.core.AbstractServiceController;

public class FbServiceController extends AbstractServiceController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public static final String TYPE = "facebook";
	FacebookDataUpdater updater;
	int numFailedUpdates = 0;

	public FbServiceController(String userId, String authToken) {
		super(TYPE, userId);
		updater = new FacebookDataUpdater(authToken);
		this.start();
		logger.info("Fb controller initialized and started.");
	}

	public void updateAuthToken(String authToken) {
		updater.setAuthToken(authToken);
	}

	@Override
	public synchronized void run() {
		logger.info("Strating fb update");
		try {
			updater.updateUserData();
			numFailedUpdates = 0;
		} catch (Exception e) {
			numFailedUpdates++;
			if (numFailedUpdates > 5) {
				disableAndRemove();
			}
		}
		try {
			wait(MainConstants.DEFAULT_THREAD_DELAY);
			run();
		} catch (Exception e) {
			logger.error("failed to delay thread: " + e.getLocalizedMessage(), e);
		}
	}
}
