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

    getEmployeeByEmail(empEmail){
        return axios.get(Manager_List_URL+'/emails/'+empEmail);

    }

    getEmployeeByManager(mgrEmail){
        return axios.get(Manager_List_URL+'/directReports/'+ mgrEmail);

    }

    updateEmployee(employee, employeeId)
    {
        return axios.put(Manager_List_URL+'/'+employeeId, employee);

    }
    deleteEmployee(employeeId)
    {
        return axios.delete(Manager_List_URL+'/'+employeeId);
    }
}

export default new ManagerServices()