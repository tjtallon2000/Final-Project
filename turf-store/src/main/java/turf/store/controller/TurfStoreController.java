package turf.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import turf.store.controller.model.TurfStoreCustomer;
import turf.store.controller.model.TurfStoreData;
import turf.store.controller.model.TurfStoreEmployee;
import turf.store.service.TurfStoreService;


@RestController
@RequestMapping("/turf_store")
@Slf4j
public class TurfStoreController {
	@Autowired
	private TurfStoreService turfStoreService;
	
	@PostMapping("/turf_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TurfStoreData saveTurfStore(@RequestBody TurfStoreData turfStoreData) {
		log.info("Creating Turf Store {}", turfStoreData);
		return turfStoreService.saveTurfStore(turfStoreData);
	}
	
	@PutMapping("/turf_store/{turfStoreId}")
	public TurfStoreData updateTurfStore(@PathVariable Long turfStoreId, @RequestBody TurfStoreData turfStoreData) {
		turfStoreData.setTurfStoreId(turfStoreId);
		log.info("Updating turf Store {}", turfStoreData);
		return turfStoreService.saveTurfStore(turfStoreData);
	}
	
	@PostMapping("/turf_store/{turfStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TurfStoreEmployee insertEployee(@PathVariable Long turfStoreId,
			@RequestBody TurfStoreEmployee turfStoreEmployee) {
		log.info("Adding employee {} for turf store with ID= {}", turfStoreEmployee, turfStoreId);
		
		return turfStoreService.saveEmployee(turfStoreId, turfStoreEmployee);
	}
	@PutMapping("/turf_store/{turfStoreId}/employee/{employeeId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TurfStoreEmployee updateEployee(@PathVariable Long turfStoreId, @PathVariable Long employeeId,
			@RequestBody TurfStoreEmployee turfStoreEmployee) {
		turfStoreEmployee.setEmployeeId(employeeId);
		log.info("Updating employee {} for turf store with ID= {}", turfStoreEmployee, turfStoreId);
		
		return turfStoreService.saveEmployee(turfStoreId, turfStoreEmployee);
	}
	
	@PostMapping("/turf_store/{turfStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TurfStoreCustomer insertCustomer(@PathVariable Long turfStoreId,
			@RequestBody TurfStoreCustomer turfStoreCustomer) {
		log.info("Adding customer {} for turf store with ID={}", turfStoreCustomer, turfStoreId);

		return turfStoreService.saveCustomer(turfStoreId, turfStoreCustomer);
	}
	
	@GetMapping("/turf_store")
	public List<TurfStoreData> retrieveAllTurfStores1() {
		log.info("Retrieving all turf stores");
		return turfStoreService.retrieveAllTurfStores();
	}
	
	@GetMapping("/turf_store/{turfStoreId}")
	public TurfStoreData TurfStoreData (@PathVariable Long turfStoreId) {
		log.info("Retrieving turf store with ID = {}", turfStoreId);
		return turfStoreService.retrieveTurfStoreById(turfStoreId);
	}

	@DeleteMapping("/turf_store/{turfStoreId}")
	public Map<String, String>  deleteTurfStoreById(@PathVariable Long turfStoreId) {
		log.info("Deleting turf store with ID={}", turfStoreId);
		turfStoreService.deleteTurfStoreById(turfStoreId);
		return Map.of("message", "Deletion of turf store with ID=" + turfStoreId + " was sucessful.");
	}

}
