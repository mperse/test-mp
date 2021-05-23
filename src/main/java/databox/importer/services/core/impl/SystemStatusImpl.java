package databox.importer.services.core.impl;

import java.net.Proxy;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import databox.importer.services.core.resource.SystemStatusService;

public class SystemStatusImpl implements SystemStatusService {

	@Context
	ServletContext context;

	@Context
	protected HttpServletRequest request;

	ObjectMapper mapper = new ObjectMapper();
	Proxy proxy = null;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public SystemStatusImpl() {
	}

	@Override
	public Response signedResponseRedirect() {
		return null;
	}

	@Override
	public Response status() {
		return Response.ok().entity("Server running.").build();
	}

}
