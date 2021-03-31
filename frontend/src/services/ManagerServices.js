import axios from 'axios';
const Manager_List_URL="http://localhost:8080/smartapp/employee";



class ManagerServices {
    getEmployees()
    {
        return axios.get(Manager_List_URL);
    }

    createEmployee(employee)
    {
        return axios.post(Manager_List_URL, employee);
    }
    getEmployeeById(employeeId){
        return axios.get(Manager_List_URL+'/'+employeeId);

    }
    updateEmployee(employee, employeeId)
    {
        return axios.put(Manager_List_URL+'/'+employeeId, employee);

    }
}

export default new ManagerServices()