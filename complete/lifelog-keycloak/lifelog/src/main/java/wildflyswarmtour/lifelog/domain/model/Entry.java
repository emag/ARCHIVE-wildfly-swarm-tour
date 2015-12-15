package wildflyswarmtour.lifelog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yoshimasa Tanabe
 */
@Entity
@Table(name = "entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  @Column(nullable = false)
  private String description;

}
