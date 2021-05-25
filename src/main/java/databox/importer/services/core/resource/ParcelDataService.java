package databox.importer.services.core.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Path(ResourceBase.PARCEL_PATH)
public interface ParcelDataService {

	@GET
	@Path("/value/{kosifko}/{parcel}")
	public String getParcelValue(@PathParam("kosifko") String kosifko, @PathParam("parcel") String parcel);

}
