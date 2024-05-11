package turf.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import turf.store.entity.Customer;
import turf.store.entity.Employee;

import turf.store.entity.TurfStore;

@Data
@NoArgsConstructor
public class TurfStoreData {
	
    private Long turfStoreId;
	private String turfStoreName;
	private String turfStoreAddress;
	private String turfStoreCity;
	private String turfStoreState;
	private String turfStoreZip;
	private String turfStorephone;
	private Set<TurfStoreEmployee> employees = new HashSet<>();
	private Set<TurfStoreCustomer> customers = new HashSet<>();
	
	public TurfStoreData(TurfStore turfStore) {
		turfStoreId = turfStore.getTurfStoreId();
		turfStoreName = turfStore.getTurfStoreName();
		turfStoreAddress = turfStore.getTurfStoreAddress();
		turfStoreCity = turfStore.getTurfStoreCity();
		turfStoreState = turfStore.getTurfStoreState();
		turfStoreZip = turfStore.getTurfStoreZip();
		turfStorephone = turfStore.getTurfStorephone();
		
		for (Employee employee : turfStore.getEmployees()) {
			employees.add(new TurfStoreEmployee(employee));
		}
		
		for (Customer customer : turfStore.getCustomers()) {
			customers.add(new TurfStoreCustomer(customer));
	}
}
}
