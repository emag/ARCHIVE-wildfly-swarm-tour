package wildflyswarmtour.lifelog;

import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yoshimasa Tanabe
 */
class LifeLogConfig {

  private LifeLogConfig() {}

  static boolean isProduction() {
    boolean isProduction = false;

    if (System.getenv("ENVIRONMENT_PRODUCTION") != null) {
      isProduction = Boolean.parseBoolean(System.getenv("ENVIRONMENT_PRODUCTION"));
    }
    if (System.getProperty("lifelog.production") != null) {
      isProduction = Boolean.parseBoolean(System.getProperty("lifelog.production"));
    }

    return isProduction;
  }

  static String dbHost() {
    String dbHost = "localhost";

    if (System.getenv("DB_PORT_5432_TCP_ADDR") != null) {
      dbHost = System.getenv("DB_PORT_5432_TCP_ADDR");
    }
    if (System.getProperty("lifelog.db.host") != null) {
      dbHost = System.getProperty("lifelog.db.host");
    }

    return dbHost;
  }

  static int dbPort() {
    int dbPort = 5432;

    if (System.getenv("DB_PORT_5432_TCP_PORT") != null) {
      dbPort = Integer.parseInt(System.getenv("DB_PORT_5432_TCP_PORT"));
    }
    if (System.getProperty("lifelog.db.port") != null) {
      dbPort = Integer.parseInt(System.getProperty("lifelog.db.port"));
    }

    return dbPort;
  }

  static String kyeCloakJsonFromTemplate() {
    ClassLoaderAsset keycloakJsonTemplate = new ClassLoaderAsset("keycloak.json.template", App.class.getClassLoader());

    try (InputStream in = keycloakJsonTemplate.openStream()) {
      JsonObject template = Json.createReader(in).readObject();
      return Json.createObjectBuilder()
        .add("realm", template.getString("realm"))
        .add("realm-public-key", template.getString("realm-public-key"))
        .add("bearer-only", template.getBoolean("bearer-only"))
        .add("auth-server-url", String.format("http://%s:%d/auth", keycloakHost(), keycloakPort()))
        .add("ssl-required", template.getString("ssl-required"))
        .add("resource", template.getString("resource"))
        .build().toString();
    } catch (IOException e) {
      e.printStackTrace();
    }

    throw new RuntimeException("Something wrong with keycloak.json.template.");
  }

  static private String keycloakHost() {
    String keycloakHost = "localhost";

    if (System.getenv("KEYCLOAK_PORT_8080_TCP_ADDR") != null) {
      keycloakHost = System.getenv("KEYCLOAK_PORT_8080_TCP_ADDR");
    }
    if (System.getProperty("lifelog.keycloak.host") != null) {
      keycloakHost = System.getProperty("lifelog.keycloak.host");
    }

    return keycloakHost;
  }

  static private int keycloakPort() {
    int keycloakPort = 8180;

    if (System.getenv("KEYCLOAK_PORT_8080_TCP_PORT") != null) {
      keycloakPort = Integer.parseInt(System.getenv("KEYCLOAK_PORT_8080_TCP_PORT"));
    }
    if (System.getProperty("lifelog.db.port") != null) {
      keycloakPort = Integer.parseInt(System.getProperty("lifelog.db.port"));
    }

    return keycloakPort;
  }

}
