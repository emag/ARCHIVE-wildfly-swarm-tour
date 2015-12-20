package wildflyswarmtour.lifelog;

/**
 * @author Yoshimasa Tanabe
 */
public class App {

  public static void main(String[] args) throws Exception {
    LifeLogContainer.newContainer()
      .start()
      .deploy(LifeLogDeployment.deployment());
  }

}
