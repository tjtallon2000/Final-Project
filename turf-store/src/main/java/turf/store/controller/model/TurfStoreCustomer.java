package turf.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import turf.store.entity.Customer;

@Data
@NoArgsConstructor
public class TurfStoreCustomer {
	
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private Set<Long> turfStoreIds = new HashSet<>();
	
	public TurfStoreCustomer(Customer customer) {
		customerId = customer.getCustomerId();
		customerFirstName = customer.getCustomerFirstName();
		customerLastName = customer.getCustomerLastName();
		customerEmail = customer.getCustomerEmail();
		
	}

	
	}

