package wildflyswarmtour.lifelog.api;

import lombok.Data;
import wildflyswarmtour.lifelog.domain.model.Entry;

/**
 * @author Yoshimasa Tanabe
 */
@Data
public class EntryResponse {

  private final Integer id;
  private final String timestamp;
  private final String description;

  public static EntryResponse from(Entry entry) {
    return new EntryResponse(entry.getId(), entry.getTimestamp().toString(), entry.getDescription());
  }

}