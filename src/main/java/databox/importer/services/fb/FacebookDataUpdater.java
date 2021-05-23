package databox.importer.services.fb;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restfb.types.User;

import databox.importer.constants.DataboxKeys;
import databox.importer.constants.MainConstants;
import databox.importer.entity.fb.FeedData;
import databox.importer.entity.fb.FeedsResponse;
import databox.importer.entity.fb.PagesResponse;
import databox.importer.services.core.sdk.DataboxWrapper;
import databox.importer.services.core.sdk.KPI;
import databox.importer.utils.ConnectionUtil;

public class FacebookDataUpdater {

	public static final DateTimeFormatter SDF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+SSSS");

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	DataboxWrapper databox = new DataboxWrapper(MainConstants.databoxFbPushToken);
	ConnectionUtil util = new ConnectionUtil();

	private String authToken;

	public FacebookDataUpdater(String authToken) {
		this.authToken = authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * Stores to databox the information about the user and some basic information
	 * 
	 * @throws Exception
	 */
	public void updateUserData() throws Exception {
		// TODO: is it possible to automatically renew authToken after using existing token?
		logger.debug("Updating user data");

		List<KPI> userInformation = new ArrayList<>();
		updateUserStatistics(userInformation);
		updateFeedsStatistics(userInformation);
		updatePagesStatistics(userInformation);
		databox.pushDataAndLog(userInformation);
	}

	private void updateUserStatistics(List<KPI> userInformation) {
		User user = util.executeGetRequest(new FbQueryParams("me", authToken), User.class);
		if (user.getLikes() != null) {
			userInformation.add(new KPI().setKey(DataboxKeys.FACEBOOK.USER_LIKES).setValue(user.getLikes().getTotalCount()).addAttribute("name", user.getName()));
		}
	}

	private void updatePagesStatistics(List<KPI> userInformation) {
		PagesResponse pages = util.executeGetRequest(new FbQueryParams("me/accounts", authToken), PagesResponse.class);
		if (pages != null && pages.getData().size() > 0) {
			// PagesData page = pages.getData().get(0);
			userInformation.add(new KPI().setKey(DataboxKeys.FACEBOOK.PAGES).setValue(pages.getData().size()));
		} else {
			userInformation.add(new KPI().setKey(DataboxKeys.FACEBOOK.PAGES).setValue(0));
		}
	}

	private void updateFeedsStatistics(List<KPI> userInformation) {
		FeedsResponse feeds = util.executeGetRequest(new FbQueryParams("me/feed?limit=10", authToken), FeedsResponse.class);
		List<FeedData> feedsList = feeds.getData();
		if (feedsList != null && feedsList.size() > 0) {
			FeedData lastFeed = feedsList.get(0);
			LocalDateTime lastFeedDate = LocalDateTime.parse(lastFeed.getCreatedTime(), SDF);
			long duration = Duration.between(lastFeedDate, LocalDateTime.now()).toDays();
			userInformation.add(new KPI().setKey(DataboxKeys.FACEBOOK.DAYS_LAST_POST).setValue(duration));
			loadAdditionalFeeds(feeds, feedsList, 1200);

			System.out.println("feeds size: " + feedsList.size());
			feedsList.forEach(f -> System.out.println(f.getCreatedTime()));

			List<FeedData> feddPerPeriod = feedsList.stream().filter(post -> Duration.between(LocalDateTime.parse(post.getCreatedTime(), SDF), LocalDateTime.now()).toDays() <= 30)
					.collect(Collectors.toList());
			userInformation.add(new KPI().setKey(DataboxKeys.FACEBOOK.POSTS_LAST_30_DAYS).setValue(feddPerPeriod.size()));
		} else {
			userInformation.add(new KPI().setKey(DataboxKeys.FACEBOOK.POSTS_LAST_30_DAYS).setValue(0));
		}
	}

	/**
	 * Recursively loads additional feeds in orfer to obtain the full list of feeds on selected dnumDays period
	 * 
	 * @param feeds
	 * @param feedsList
	 */

	private List<FeedData> loadAdditionalFeeds(FeedsResponse feeds, List<FeedData> feedsList, long numDays) {
		FeedData latestFeed = feeds.getData().get(feeds.getData().size() - 1);
		LocalDateTime latestFeedDate = LocalDateTime.parse(latestFeed.getCreatedTime(), SDF);
		if (Duration.between(latestFeedDate, LocalDateTime.now()).toDays() <= numDays) {
			FeedsResponse nextFeed = util.executeGetRequest(new FbQueryParams(feeds.getPaging().getNext(), null), FeedsResponse.class);
			feedsList.addAll(nextFeed.getData());
			return loadAdditionalFeeds(nextFeed, feedsList, numDays);
		} else {
			return feedsList;
		}
	}
}
