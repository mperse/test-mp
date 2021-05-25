package databox.importer.services.fb;

import org.junit.Test;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.DebugTokenInfo;
import com.restfb.Version;
import com.restfb.types.Post;
import com.restfb.types.User;

import databox.importer.utils.ConnectionUtil;
import databox.importer.utils.JsonObjectMapper;

public class FbConnectionTest {

	private JsonObjectMapper mapper = new JsonObjectMapper();

	ConnectionUtil util = new ConnectionUtil();

	// TODO: is it possible to generate auth token programmatically?
	/*
	 * @Test public void test1() { // AccessToken accessToken = new DefaultFacebookClient(Version.VERSION_10_0).obtainAppAccessToken("843619803237963", "a7c3d1e3260247612422354b0aaad7af");
	 * 
	 * FacebookClient.AccessToken token; try { String code = getFacebookUserTokenCode("http://localhost", "{user:mp}"); token = getFacebookUserToken(code, "http://localhost"); String t = token.getAccessToken(); Date expires = token.getExpires();
	 * 
	 * System.out.println(token.getAccessToken());
	 * 
	 * User user = util.executeGetRequest(new FbQueryParams("me", token.getAccessToken()), User.class);
	 * 
	 * System.out.println("User name: " + user.getName()); System.out.println("User likes: " + user.getLikes()); System.out.println("User apps: " + user.getIdsForApps()); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * private String getFacebookUserTokenCode(String redirectUrl, String state) throws IOException { String appId = "843619803237963";
	 * 
	 * WebRequestor wr = new DefaultWebRequestor(); WebRequestor.Response accessTokenResponse = wr.executeGet("https://www.facebook.com/v10.0/dialog/oauth?client_id=" + appId + "&redirect_uri=" + redirectUrl + "&state=" + state);
	 * 
	 * System.out.println(accessTokenResponse.getBody());
	 * 
	 * return accessTokenResponse.getBody(); }
	 * 
	 * private FacebookClient.AccessToken getFacebookUserToken(String code, String redirectUrl) throws IOException { String appId = "843619803237963"; String secretKey = "a7c3d1e3260247612422354b0aaad7af";
	 * 
	 * WebRequestor wr = new DefaultWebRequestor(); WebRequestor.Response accessTokenResponse = wr .executeGet("https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl + "&client_secret=" + secretKey + "&code=" + code);
	 * 
	 * wr.executePost(url, parameters, headerAccessToken)
	 * 
	 * return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody()); }
	 */

	@Test
	public void test1() {
		String token = FbTestConstants.getUserAuthToken();
		System.out.println("* Fetching single objects *");

		DefaultFacebookClient facebookClient = new DefaultFacebookClient(token, Version.VERSION_10_0);

		DebugTokenInfo tokenInfo = facebookClient.debugToken(token);

		System.out.println(tokenInfo);

		User user1 = facebookClient.fetchObject("me", User.class);
		System.out.println("User name: " + user1.getName());
		System.out.println("User likes: " + user1.getLikes());
		System.out.println("User apps: " + user1.getIdsForApps());

		Connection<Post> myFeeds = facebookClient.fetchConnection("me/feed", Post.class);

		System.out.println(myFeeds.getTotalCount());
	}

}
