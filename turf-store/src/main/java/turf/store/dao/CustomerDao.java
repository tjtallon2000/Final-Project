package turf.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import turf.store.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
