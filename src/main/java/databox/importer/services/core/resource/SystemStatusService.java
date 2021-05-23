package databox.importer.services.core.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Path(ResourceBase.SYSTEM_PATH)
public interface SystemStatusService {

	@GET
	@Path("/sso/")
	public Response signedResponseRedirect();

	@GET
	@Path("/status/")
	public Response status();

}
