import axios from 'axios';
const Employee_List_URL="http://localhost:8080/smartapp/employee";



class  EmployeeServices {
    getEmployees()
    {
        return axios.get(Employee_List_URL);
    }
    createEmployeee(employee)
    {
        return axios.post(Employee_List_URL, employee);
    }
}

export default new EmployeeServices()