package wildflyswarmtour.lifelog;

import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jpa.JPAFraction;

/**
 * @author Yoshimasa Tanabe
 */
public class LifeLogContainer {

  public static Container newContainer() throws Exception {
    Container container = new Container();

    boolean production = Boolean.parseBoolean(System.getProperty("swarm.lifelog.production"));

    if (production) {
      container.fraction(new DatasourcesFraction()
        .jdbcDriver("org.postgresql", (d) -> {
          d.driverClassName("org.postgresql.Driver");
          d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
          d.driverModuleName("org.postgresql");
        })
        .dataSource("lifelogDS", (ds) -> {
          ds.driverName("org.postgresql");
          ds.connectionUrl("jdbc:postgresql://localhost:5432/lifelog");
          ds.userName("lifelog");
          ds.password("lifelog");
        })
      );
    } else {
      container.fraction(new DatasourcesFraction()
        .jdbcDriver("h2", (d) -> {
          d.driverClassName("org.h2.Driver");
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
    }

    container.fraction(new JPAFraction()
      .inhibitDefaultDatasource()
      .defaultDatasource("jboss/datasources/lifelogDS")
    );

    return container;
  }

}
