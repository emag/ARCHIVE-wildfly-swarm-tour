package wildflyswarmtour.lifelog;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.keycloak.Secured;

/**
 * @author Yoshimasa Tanabe
 */
public class LifeLogDeployment {

  public static JAXRSArchive deployment() {
    JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);

    deployment.addPackages(true, App.class.getPackage());

    deployment.addAsWebInfResource(
      new ClassLoaderAsset("META-INF/persistence.xml", App.class.getClassLoader()), "classes/META-INF/persistence.xml");

    deployment.as(Secured.class)
      .protect("/entries/*")
        .withMethod("POST", "PUT", "DELETE")
        .withRole("author");

    return deployment;
  }

}
