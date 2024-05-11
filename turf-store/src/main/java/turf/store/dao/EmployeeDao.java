package turf.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import turf.store.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
