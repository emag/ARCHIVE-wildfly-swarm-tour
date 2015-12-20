package wildflyswarmtour.lifelog.api;

import wildflyswarmtour.lifelog.domain.model.Entry;
import wildflyswarmtour.lifelog.domain.service.EntryService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yoshimasa Tanabe
 */
@Path("/entries")
public class EntryController {

  @Inject
  private EntryService entryService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<EntryResponse> findALL() {
    List<Entry> allEntries = entryService.findAll();
    return allEntries.stream()
      .map(EntryResponse::from)
      .collect(Collectors.toList());
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response find(@PathParam("id") Integer id) {
    Entry entry = entryService.find(id);

    if (entry == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    return Response.ok(EntryResponse.from(entry)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(@Context UriInfo uriInfo, Entry entry) {
    Entry created = entryService.save(entry);

    return Response
      .created(
        uriInfo.getAbsolutePathBuilder()
          .path(String.valueOf(created.getId()))
          .build())
      .build();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("id") Integer id, Entry entry) {
    Entry old = entryService.find(id);

    if (old == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    old.setDescription(entry.getDescription());
    entryService.save(old);

    return Response.ok().build();
  }

  @DELETE
  public Response deleteAll() {
    entryService.deleteAll();
    return Response.noContent().build();
  }

  @DELETE
  @Path("{id}")
  public Response delete(@PathParam("id") Integer id) {
    if (entryService.find(id) == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    entryService.delete(id);

    return Response.noContent().build();
  }

}
