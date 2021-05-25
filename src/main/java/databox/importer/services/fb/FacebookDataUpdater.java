package databox.importer.services.fb;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.DebugTokenInfo;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Likes;
import com.restfb.types.Page;
import com.restfb.types.Post;

import databox.importer.constants.DataboxKeys;
import databox.importer.constants.MainConstants;
import databox.importer.services.core.sdk.DataboxWrapper;
import databox.importer.services.core.sdk.KPI;
import databox.importer.utils.ConnectionUtil;
import databox.importer.utils.DateFormatUtil;

public class FacebookDataUpdater {
	public static final DateTimeFormatter SDF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+SSSS");

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	DataboxWrapper databox = new DataboxWrapper(MainConstants.databoxFbPushToken);
	ConnectionUtil util = new ConnectionUtil();
	DefaultFacebookClient facebookClient;

	private String userId;

	private String authToken;

	public FacebookDataUpdater(String authToken) {
		setAuthToken(authToken);
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
		facebookClient = new DefaultFacebookClient(authToken, Version.VERSION_10_0);
		DebugTokenInfo info = facebookClient.debugToken(authToken);
		userId = info.getUserId();
	}

	/**
	 * Stores to databox the information about the user and some basic information
	 * 
	 * @throws Exception
	 */
	public void updateUserData() throws Exception {
		logger.debug("Updating user data");

		checkTokenExpiration();

		List<KPI> userInformation = new ArrayList<>();
		updateFeedsStatistics(userInformation);
		updatePagesStatistics(userInformation);
		updateLikesStatistics(userInformation);
		databox.pushDataAndLog(userInformation);
	}

	private void checkTokenExpiration() {
		DebugTokenInfo info = facebookClient.debugToken(authToken);
		if (ChronoUnit.DAYS.between(info.getExpiresAt().toInstant(), new Date().toInstant()) < 1) {
			// TODO: extend token
			// facebookClient.obtainExtendedAccessToken(appId, appSecret);
		}
	}

	private void updatePagesStatistics(List<KPI> userInformation) {
		Connection<Page> feedConnection = facebookClient.fetchConnection(userId + "/accounts", Page.class, Parameter.with("limit", 100));

		int pageCount = 0;
		for (List<Page> pages : feedConnection) {
			pageCount += pages.size();
		}

		addKPI(userInformation, DataboxKeys.FACEBOOK.PAGES, pageCount);
	}

	private void addKPI(List<KPI> userInformation, String key, Object value) {
		userInformation.add(new KPI().setKey(key).setValue(value).setDate(DateFormatUtil.getCurrentDay()));
	}

	private void updateLikesStatistics(List<KPI> userInformation) {
		Connection<Likes> feedConnection = facebookClient.fetchConnection(userId + "/likes", Likes.class, Parameter.with("limit", 1000));
		int likesCount = 0;
		for (List<Likes> pages : feedConnection) {
			likesCount += pages.size();
		}

		addKPI(userInformation, DataboxKeys.FACEBOOK.LIKES, likesCount);
	}

	private void updateFeedsStatistics(List<KPI> userInformation) {
		Date currentDate = new Date();
		Connection<Post> feedConnection = facebookClient.fetchConnection(userId + "/feed", Post.class, Parameter.with("limit", 100));

		List<Post> feedsList = feedConnection.getData();
		if (feedsList != null && feedsList.size() > 0) {
			Post lastFeed = feedsList.get(0);
			long duration = ChronoUnit.DAYS.between(lastFeed.getCreatedTime().toInstant(), currentDate.toInstant());

			List<Post> posts = loadFeeds(feedConnection, 30);
			addKPI(userInformation, DataboxKeys.FACEBOOK.DAYS_LAST_POST, duration);
			addKPI(userInformation, DataboxKeys.FACEBOOK.POSTS_LAST_30_DAYS, posts.size());
		} else {
			addKPI(userInformation, DataboxKeys.FACEBOOK.DAYS_LAST_POST, 365);
			addKPI(userInformation, DataboxKeys.FACEBOOK.POSTS_LAST_30_DAYS, 0);
		}
	}

	/**
	 * Recursively loads additional feeds in order to obtain the full list of feeds in selected numDays period
	 */

	private List<Post> loadFeeds(Connection<Post> feedConnection, long numDays) {
		List<Post> feedsList = new ArrayList<>();
		Date currentDate = new Date();
		for (List<Post> myFeedPage : feedConnection) {
			for (Post post : myFeedPage) {
				feedsList.add(post);
				if (ChronoUnit.DAYS.between(post.getCreatedTime().toInstant(), currentDate.toInstant()) > 120) {
					return feedsList;
				}
			}
		}
		return feedsList;
	}

}
