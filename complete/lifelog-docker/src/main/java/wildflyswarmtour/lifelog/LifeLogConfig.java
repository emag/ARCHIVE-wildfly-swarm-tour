package wildflyswarmtour.lifelog;

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

}
