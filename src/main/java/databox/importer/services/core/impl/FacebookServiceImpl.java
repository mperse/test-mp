package databox.importer.services.core.impl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.MainControllerHolder;
import databox.importer.services.core.AbstractServiceController;
import databox.importer.services.core.resource.FacebookService;
import databox.importer.services.fb.FbServiceController;

public class FacebookServiceImpl implements FacebookService {

	private String okRedirectUri = "http://localhost:8080/databox-importer/facebookLoginOk.jsp";
	private String errRedirectUri = "http://localhost:8080/databox-importer/facebookLoginFailed.jsp";

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public FacebookServiceImpl() {
	}

	@Override
	public Response addUserDataUpdater(String userId, String authToken) {
		if (authToken == null || authToken.isEmpty()) {
			throw new BadRequestException("Parameter 'authToken' must be specified!");
		}

		try {
			AbstractServiceController existingController = MainControllerHolder.getInstance().getConroller(FbServiceController.TYPE, userId);
			if (existingController != null) {
				((FbServiceController) existingController).updateAuthToken(authToken);
			} else {
				logger.info("Adding new controller.");
				MainControllerHolder.getInstance().addControler(new FbServiceController(userId, authToken));
			}
			return Response.temporaryRedirect(new URI(okRedirectUri)).build();
		} catch (Exception e) {
			try {
				return Response.temporaryRedirect(new URI(errRedirectUri)).build();
			} catch (URISyntaxException e1) {
				logger.error("Failed to create uri: " + errRedirectUri, e);
				throw new InternalServerErrorException("Failed to create uri");
			}
		}
	}

}
