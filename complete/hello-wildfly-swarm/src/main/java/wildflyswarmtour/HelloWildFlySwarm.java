package wildflyswarmtour;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Yoshimasa Tanabe
 */
@Path("/hello")
public class HelloWildFlySwarm {

  @GET
  public String hello() {
    return "Hello, WildFly Swarm!";
  }

}
