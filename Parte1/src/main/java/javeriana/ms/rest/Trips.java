package javeriana.ms.rest;

import java.sql.SQLException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javeriana.ms.rest.controller.Db;
import javeriana.ms.rest.models.Trip;

@Path("paseos")
public class Trips {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllAvailableTrips() throws SQLException {
    Db db = new Db("jdbc:mariadb://" + System.getenv("DB_IP") + "/ms", "root", "root");
    return Response.ok(db.getAvailableTrips()).build();
  }

  @Path("paseo/{id}")
  @DELETE
  public Response deleteTrip(@PathParam("id") String id) {
    try {
      Db db = new Db("jdbc:mariadb://" + System.getenv("DB_IP") + "/ms", "root", "root");
      int code = db.deleteTrip(id);
      if (code == 200) {
        return Response.ok("Resource deleted").build();
      } else if (code == 404) {
        return Response.noContent().build();
      }
      return Response.serverError().build();
    } catch (SQLException ex) {
      return Response.serverError().build();
    }
  }

  @Path("paseo/{id}")
  @PUT
  public Response update(@PathParam("id") String id, @QueryParam("origen") String origen,
      @QueryParam("destino") String destino) {
    try {
      Db db = new Db("jdbc:mariadb://" + System.getenv("DB_IP") + "/ms", "root", "root");
      int code = db.update(id, origen, destino);
      if (code == 200) {
        return Response.ok().build();
      }
      return Response.notModified().build();
    } catch (SQLException ex) {
      return Response.serverError().build();
    }
  }

  @Path("paseo/")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createTrip(Trip trip) {
    try {
      Db db = new Db("jdbc:mariadb://" + System.getenv("DB_IP") + "/ms", "root", "root");
      int code = db.createTrip(trip);
      if (code == 201) {
        return Response.created(null).build();
      }
      return Response.notModified().build();
    } catch (SQLException ex) {
      return Response.serverError().build();
    }
  }

}
