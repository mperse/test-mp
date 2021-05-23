package databox.importer.services.core.fb;

import org.junit.Test;

import com.restfb.types.User;

import databox.importer.entity.fb.PageData;
import databox.importer.entity.fb.PagesResponse;
import databox.importer.services.fb.FbQueryParams;
import databox.importer.utils.ConnectionUtil;
import databox.importer.utils.JsonObjectMapper;

public class FbConnectionTest {

	private JsonObjectMapper mapper = new JsonObjectMapper();

	ConnectionUtil util = new ConnectionUtil();

	/**
	 * Test that clearing of the report file
	 */
	@Test
	public void test1() {
		fetchUserData(FbTestConstants.getUserAuthToken());
	}

	void fetchUserData(String token) {
		System.out.println("* Fetching single objects *");

		User user = util.executeGetRequest(new FbQueryParams("me", token), User.class);

		System.out.println("User name: " + user.getName());
		System.out.println("User likes: " + user.getLikes());
		System.out.println("User apps: " + user.getIdsForApps());

		PagesResponse pages = util.executeGetRequest(new FbQueryParams("me/accounts", token), PagesResponse.class);

		// Page page = facebookClient.fetchObject("PGD Spodnja Polskava", Page.class);
		PageData page = pages.getData().get(0);
		System.out.println("Page name: " + page.getName());

	}

}
