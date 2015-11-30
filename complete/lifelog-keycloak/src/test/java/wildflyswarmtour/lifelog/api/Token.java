package wildflyswarmtour.lifelog.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Yoshimasa Tanabe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Token {

  @JsonProperty("access_token")
  private String accessToken;

}
