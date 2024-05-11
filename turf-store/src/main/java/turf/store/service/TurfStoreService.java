package turf.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import turf.store.controller.model.TurfStoreCustomer;
import turf.store.controller.model.TurfStoreData;
import turf.store.controller.model.TurfStoreEmployee;
import turf.store.dao.CustomerDao;
import turf.store.dao.EmployeeDao;
import turf.store.dao.TurfStoreDao;
import turf.store.entity.Customer;
import turf.store.entity.Employee;
import turf.store.entity.TurfStore;

@Service
public class TurfStoreService {
	
	@Autowired
	private TurfStoreDao turfStoreDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public TurfStoreData saveTurfStore(TurfStoreData turfStoreData) {
		Long turfStoreId = turfStoreData.getTurfStoreId();
		TurfStore turfStore = findOrCreateTurfStore(turfStoreId);
		
		copyTurfStoreFields(turfStore, turfStoreData);
		return new TurfStoreData(turfStoreDao.save(turfStore));
	}
	
	private void copyTurfStoreFields(TurfStore turfStore, TurfStoreData turfStoreData) {
		turfStore.setTurfStoreId(turfStoreData.getTurfStoreId());
		turfStore.setTurfStoreName(turfStoreData.getTurfStoreName());
		turfStore.setTurfStoreAddress(turfStoreData.getTurfStoreAddress());
		turfStore.setTurfStoreCity(turfStoreData.getTurfStoreCity());
		turfStore.setTurfStoreState(turfStoreData.getTurfStoreState());
		turfStore.setTurfStoreZip(turfStoreData.getTurfStoreZip());
		turfStore.setTurfStorephone(turfStoreData.getTurfStorephone());
	}
	
	private TurfStore findOrCreateTurfStore(Long turfStoreId) {
		TurfStore turfStore;
		if (Objects.isNull(turfStoreId)) {
			turfStore = new TurfStore();
		} else {
			turfStore = findTurfStoreById(turfStoreId);
		}
		return turfStore;
	}
	
	private TurfStore findTurfStoreById(Long turfStoreId) {
		return turfStoreDao.findById(turfStoreId)
				.orElseThrow(() -> new NoSuchElementException("Turf Store with ID=" + turfStoreId + " was not found"));
	}
	
	@Transactional(readOnly = false)
	public TurfStoreEmployee saveEmployee(Long turfStoreId, TurfStoreEmployee turfStoreEmployee) {
		
		TurfStore turfStore = findTurfStoreById(turfStoreId);
		
		Employee employee = findOrCreateEmployee(turfStoreEmployee.getEmployeeId());
		
		setEmployeeFields(employee, turfStoreEmployee);
		employee.setTurfStore(turfStore);
		turfStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		return new TurfStoreEmployee(dbEmployee);
	}
	
	private void setEmployeeFields(Employee employee, TurfStoreEmployee turfStoreEmployee) {
		employee.setEmployeeId(turfStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(turfStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(turfStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(turfStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(turfStoreEmployee.getEmployeeJobTitle());
		
	}
	
	private Employee findOrCreateEmployee(Long employeeId) {
	Employee employee;
	
	if (Objects.isNull(employeeId)) {
		employee = new Employee();
	} else {
		employee = findEmployeeById(employeeId);
	}
	
	return employee;
	}
	
	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist."));
	}
	
	public TurfStoreCustomer saveCustomer(Long turfStoreId, TurfStoreCustomer turfStoreCustomer) {
		TurfStore turfStore = findTurfStoreById(turfStoreId);
		Long customerId = turfStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, turfStoreId);
		setCustomerFields(customer, turfStoreCustomer);
		customer.getTurfStores().add(turfStore);
		turfStore.getCustomers().add(customer);
		Customer dbCustomer = customerDao.save(customer);
		
		return new TurfStoreCustomer(dbCustomer);
	}
	
	private Customer findOrCreateCustomer(Long customerId, Long turfStoreId) {
		Customer customer;
		
		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(customerId, turfStoreId);
		}
		return customer;
	}
	
	private Customer findCustomerById(Long customerId, Long turfStoreId) {
		TurfStore turfStore = turfStoreDao.findById(turfStoreId)
				.orElseThrow(() -> new NoSuchElementException("Turf Store with ID= " + turfStoreId + " does not exist."));
		Set<Customer> customers = turfStore.getCustomers();
		
		for (Customer customer : customers) {
			if (customer.getId().equals(customerId)) {
				return customer;
			}
		}
		
		throw new IllegalArgumentException(
				"Customer with ID=" + customerId + " does not exist in the Turf Store with ID =" + turfStoreId);
	}
	
	private void setCustomerFields(Customer customer, TurfStoreCustomer turfStoreCustomer) {
		customer.setCustomerFirstName(turfStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(turfStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(turfStoreCustomer.getCustomerEmail());
	}
	@Transactional(readOnly = true)
	public List<TurfStoreData> retrieveAllTurfStores() {
		
		List<TurfStoreData> turfStoreDataList = new LinkedList<>();
		
		List<TurfStore> turfStores = turfStoreDao.findAll();
		
		for (TurfStore turfStore : turfStores) {
			TurfStoreData turfStoreData = new TurfStoreData(turfStore);
			
			turfStoreData.getCustomers().clear();
			turfStoreData.getEmployees().clear();
			
			turfStoreDataList.add(turfStoreData);
		}
		return turfStoreDataList;
		
	}
	
	public TurfStoreData retrieveTurfStoreById(Long turfStoreId) {
		TurfStore turfStore = findTurfStoreById(turfStoreId);
		return new TurfStoreData(turfStore);
	}
	
	@Transactional(readOnly = false)
	public void deleteTurfStoreById(Long turfStoreId) {
		TurfStore turfStore = findTurfStoreById(turfStoreId);
		turfStoreDao.delete(turfStore);
	}
	
}
	


