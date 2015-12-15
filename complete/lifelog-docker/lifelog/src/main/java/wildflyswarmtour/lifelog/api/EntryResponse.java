package wildflyswarmtour.lifelog.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wildflyswarmtour.lifelog.domain.model.Entry;

import java.io.Serializable;

/**
 * @author Yoshimasa Tanabe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntryResponse implements Serializable {

  private Integer id;
  private String timestamp;
  private String description;

  public static EntryResponse from(Entry entry) {
    return new EntryResponse(entry.getId(), entry.getTimestamp().toString(), entry.getDescription());
  }

}
