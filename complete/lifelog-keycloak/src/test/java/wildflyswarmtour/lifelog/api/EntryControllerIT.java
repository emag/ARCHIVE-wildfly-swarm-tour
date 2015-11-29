package wildflyswarmtour.lifelog.api;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.ContainerFactory;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.JPAFraction;
import wildflyswarmtour.lifelog.LifeLogDeployment;
import wildflyswarmtour.lifelog.domain.model.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * @author Yoshimasa Tanabe
 */
@RunWith(Arquillian.class)
public class EntryControllerIT implements ContainerFactory {

  @Deployment(testable = false)
  public static JAXRSArchive createDeployment() {
    return LifeLogDeployment.deployment();
  }

  @ArquillianResource
  private URI deploymentUri;

  @Override
  public Container newContainer(String... args) throws Exception {
    return newContainer();
//    return LifeLogContainer.newContainer();
  }

  @Test
  public void test() {
    UriBuilder baseUri = UriBuilder.fromUri(deploymentUri).path("entries");

    // Create a new entry
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(baseUri);

    Entry entry = new Entry();
    entry.setDescription("Test");
    Response response = target.request().post(Entity.json(entry));

    assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));

    URI newEntryLocation = response.getLocation();

    client.close();

    // Get the entry
    client = ClientBuilder.newClient();
    target = client.target(newEntryLocation);
    response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

    assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    assertThat(response.readEntity(EntryResponse.class).getDescription(), is("Test"));

    client.close();

    // Delete the entry
    client = ClientBuilder.newClient();
    target = client.target(newEntryLocation);
    response = target.request().delete();

    assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));

    client.close();

    // Check no entries
    client = ClientBuilder.newClient();
    target = client.target(baseUri);
    response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

    assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));

    List<EntryResponse> entries = response.readEntity(new GenericType<List<EntryResponse>>() {});
    assertThat(entries.size(), is(0));

    client.close();
  }

  private Container newContainer() throws Exception {
    Container container = new Container();

    container.fraction(new DatasourcesFraction()
      .jdbcDriver("h2", (d) -> {
        d.driverDatasourceClassName("org.h2.Driver");
        d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
        d.driverModuleName("com.h2database.h2");
      })
      .dataSource("lifelogDS", (ds) -> {
        ds.driverName("h2");
        ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        ds.userName("sa");
        ds.password("sa");
      })
    );

    container.fraction(new JPAFraction()
      .inhibitDefaultDatasource()
      .defaultDatasource("jboss/datasources/lifelogDS")
    );

    return container;
  }

}