package turf.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import turf.store.entity.TurfStore;

public interface TurfStoreDao extends JpaRepository<TurfStore, Long> {

}
