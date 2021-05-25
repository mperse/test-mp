package databox.importer.services.parcel;

import java.util.HashMap;

import databox.importer.utils.ConnectionQueryParams;

public class ParcelQueryParams implements ConnectionQueryParams {

	private String url;
	private HashMap<String, String> queryParams;

	public ParcelQueryParams(String url, HashMap<String, String> queryParams) {
		this.url = url;
		this.queryParams = queryParams;
	}

	public String buildRequestPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(url);

		if (queryParams != null && !queryParams.isEmpty()) {
			sb.append(sb.toString().contains("?") ? "&" : "?");
			queryParams.entrySet().forEach(e -> sb.append(e.getKey() + "=" + e.getValue() + "&"));
		}
		return sb.toString();
	}

}
