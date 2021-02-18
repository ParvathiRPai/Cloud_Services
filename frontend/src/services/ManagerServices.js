import axios from 'axios';
const Manager_List_URL="http://localhost:8080/smartapp/manager";



class ManagerServices {
    getEmployees()
    {
        return axios.get(Manager_List_URL);
    }
}

export default new ManagerServices()