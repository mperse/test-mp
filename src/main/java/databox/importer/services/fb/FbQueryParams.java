package databox.importer.services.fb;

import databox.importer.constants.MainConstants;
import databox.importer.utils.ConnectionQueryParams;

public class FbQueryParams implements ConnectionQueryParams {

	String token;
	String path;

	public FbQueryParams(String path, String token) {
		this.path = path;
		this.token = token;
	}

	public String buildRequestPath() {
		StringBuilder sb = new StringBuilder();
		if (!path.startsWith(MainConstants.facebookApiBaseUrl)) {
			sb.append(MainConstants.facebookApiBaseUrl);
		}
		sb.append(path);
		if (token != null) {
			sb.append(sb.toString().contains("?") ? "&" : "?");
			sb.append("access_token=");
			sb.append(token);
		}
		return sb.toString();
	}

}
