
package turf.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import turf.store.entity.Employee;

@Data
@NoArgsConstructor
public class TurfStoreEmployee {

	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;

	public TurfStoreEmployee(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeePhone = employee.getEmployeePhone();
		employeeJobTitle = employee.getEmployeeJobTitle();

	}

}
