package databox.importer.services.fb;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.constants.DataboxKeys;
import databox.importer.constants.MainConstants;
import databox.importer.services.core.sdk.DataboxWrapper;
import databox.importer.services.core.sdk.KPI;
import databox.importer.services.fb.FacebookDataUpdater;

public class FacebookDataUpdaterTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Test
	public void test1() {
		try {
			DataboxWrapper databox = new DataboxWrapper(MainConstants.databoxFbPushToken);
			List<KPI> userInformation = new ArrayList<>();
			userInformation.add(new KPI().setKey(DataboxKeys.FACEBOOK.PAGES).setValue(5));
			userInformation.add(new KPI().setKey(DataboxKeys.FACEBOOK.LIKES).setValue(8));
			databox.pushDataAndLog(userInformation);
		} catch (Exception e) {
			String msg = "Updating user data should not fail: " + e.getLocalizedMessage();
			logger.error(msg, e);
			fail(msg);
		}
	}

	@Test
	public void test2() {
		FacebookDataUpdater updater = new FacebookDataUpdater(FbTestConstants.getUserAuthToken());
		try {
			updater.updateUserData();
		} catch (Exception e) {
			String msg = "Updating user data should not fail: " + e.getLocalizedMessage();
			logger.error(msg, e);
			fail(msg);
		}
	}

}
