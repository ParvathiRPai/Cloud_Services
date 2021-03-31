import React,  { Component } from 'react'
import ManagerServices from '../services/ManagerServices'
import {withRouter} from 'react-router-dom';

class ListManagers extends React.Component {
    constructor(props){
        super(props)
        this.state={
            employees: []
        }
        this.addEmployee=this.addEmployee.bind(this);
        this.editEmployee=this.editEmployee.bind(this);
    }
    editEmployee(id){
        this.props.history.push('/update-employee/${id}');

    }
    componentDidMount()
    {
        ManagerServices.getEmployees().then((res)=>{
            this.setState({employees: res.data});

        });
    }
    addEmployee()
    {
        this.props.history.push('/add-employee')
    }
    render() {
        return (
            <div>
            <h2 className ="text-center">Employees List</h2>
            <div className="row">
                <button className="btn-btn-primary" onClick={this.addEmployee}>Add Employee</button>
            </div>

            <div className="row">
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Employee First Name</th>
                                <th>Employee Last Name</th>
                                <th>Email</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.employees.map(
                                employee=>
                                <tr  key={employee.emp_no}>
                                <th>{employee.first_name}</th>
                                <th>{employee.last_name}</th>
                                <th>{employee.emailid}</th>
                                <td>
                                    <button onClick={()=> this.editEmployee(employee.id)} className="btn btn-info">Update</button>
                                </td>
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
export default withRouter(ListManagers);