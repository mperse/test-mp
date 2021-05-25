package databox.importer.services.fb;

public class FbTestConstants {

	static final String authT = "EAALZCRIqyjksBAPgjoif6vedmqOkRKue74qPChvnBZBWLlBjMpTxuHic3R56kZClUuFAP77eGs9ePEmZC3dtNGb0gtjZBrZAhizvucqnZC2hnjLgo3M9WhMnc6e8YP4x1ywpxM4e7IWEI2jr7g38O0hfKaZAjYQyaCAcJ8TVTkOC1AwKGIxZAGfxzVCgJBkFhmjIZD";

	public static String getUserAuthToken() {
		String authToken = System.getProperty("fb_auth_token");
		if (isValid(authToken)) {
			return authToken;
		}

		if (isValid(authT)) {
			return authT;
		}

		throw new RuntimeException("Please active auth token as 'fb_auth_token' in gradle.properties file");
	}

	// private static AccessToken token = null;
	//
	// public static String getUserAuthToken() {
	// if (token != null && token.getExpires().compareTo(new Date()) > 0) {
	// return token.getAccessToken();
	// }
	//
	// String authToken = System.getProperty("fb_auth_token");
	// if (isValid(authToken)) {
	// return authToken;
	// }
	//
	// String appId = System.getProperty("fb_app_id");
	// String secret = System.getProperty("fb_app_secret");
	// token = new DefaultFacebookClient(Version.VERSION_10_0).obtainAppAccessToken(appId, secret);
	//
	// if (token == null) {
	// throw new RuntimeException("Please specify user 'fb_app_id' and 'fb_app_secret' or active auth token as 'fb_auth_token' in gradle.properties file");
	// }
	//
	// return token.getAccessToken();
	// }

	private static boolean isValid(String authToken) {
		return authToken != null && !authToken.isEmpty();
	}

}
