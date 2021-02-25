import React, { Component } from 'react'
import EmployeeServices from '../services/EmployeeServies'
import { withRouter } from "react-router-dom";

class ListEmployees extends Component {
    constructor(props){
        super(props)
        this.state={
            employees: []
        }
        this.addEmployee=this.addEmployee.bind(this);
    }
    componentDidMount()
    {
        EmployeeServices.getEmployees().then((res)=>{
            this.setState({employees: res.data});

        });
    }
    addEmployee()
    {
        this.props.history.push(`/addemployee`)
    }
    render() {
        return (
            <div>                
            <div className="row">
                <button className="btn btn-primary" onClick={this.addEmployee}>Add Employee</button>
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Manager Id</th>
                                <th>Employee First Name</th>
                                <th>Employee Last Name</th>
                                <th>Salary</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.employees.map(
                                employee=>
                                <tr  key={employee.emp_no}>
                                <th>{employee.manager_number}</th>
                                <th>{employee.first_name}</th>
                                <th>{employee.last_name}</th>
                                <th>{employee.salary}</th>
                                </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default withRouter(ListEmployees);