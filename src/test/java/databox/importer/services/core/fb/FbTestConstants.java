package databox.importer.services.core.fb;

public class FbTestConstants {

	private static String activeUserAuthToken = "EAALZCRIqyjksBABLBvPor65oUoZAX3tQWci8KZApwiYztbo73bCAZBLbkg8ldVfb1GEcsIzxY6QkJvIjGTQsp5nNuIXEpftEL5jKasW8e5pJ7023LjOYBsxa2DQq465TLR7a9Fz0PakS9r37wCiP6xylcBXFuKZAKzf8Y6BZCShBP2IJeZBCwijiETPEstc0l04U8aFKPnoEEwuZCyuXZBRiGBwNNEY12XogZBCEHhw5ygwZBEuxKuk3h09";

	public static String getUserAuthToken() {
		String envToken = System.getenv("USER_AUTH_TOKEN");
		return envToken == null ? activeUserAuthToken : envToken;
	}

}
