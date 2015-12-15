package wildflyswarmtour.lifelog.domain.repository;

import wildflyswarmtour.lifelog.domain.model.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationScoped
@Transactional
public class EntryRepository {

  @PersistenceContext
  private EntityManager em;

  public List<Entry> findAll() {
    return em
      .createQuery("SELECT e FROM Entry e ORDER BY  e.timestamp DESC", Entry.class)
      .getResultList();
  }

  public Entry find(Integer id) {
    return em.find(Entry.class, id);
  }

  public Entry save(Entry entry) {
    if (entry.getId() == null) { // new entry
      entry.setTimestamp(LocalDateTime.now());
      em.persist(entry);
      return entry;
    } else { // existing entry
      return em.merge(entry);
    }
  }

  public void deleteAll() {
    findAll().forEach(this::delete);
  }

  public void delete(Integer id) {
    delete(em.find(Entry.class, id));
  }

  private void delete(Entry entry) {
    em.remove(entry);
  }

}
