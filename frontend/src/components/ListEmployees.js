import React,  { Component } from 'react'
import ManagerServices from '../services/ManagerServices'
import {withRouter} from 'react-router-dom';
import CreateUpdateEmployee from './CreateUpdateEmployee';
import { Link } from 'react-router-dom';

class ListEmployees extends React.Component {
    constructor(props){
        super(props)
 
        this.state={
            empEmail: props.empEmail,
            employees: [],
            isHr: (props.isHr === "1"),
            isManager: (props.isManager === "1"),
            isMyInfo: (props.isMyInfo === "1"),
            isCreateView: 0,
            createViewType: "create",
            createViewId: -1,
        }
        this.addEmployee=this.addEmployee.bind(this);
        this.editEmployee=this.editEmployee.bind(this);
        this.deleteEmployee=this.deleteEmployee.bind(this);
        this.handleCreateUpdateCompletion=this.handleCreateUpdateCompletion.bind(this);
        this.loadEmployees=this.loadEmployees.bind(this);
    }

    handleCreateUpdateCompletion()
    {
        this.setState({
            isCreateView: 0,
        });
        this.loadEmployees();
    }

    editEmployee(id){
        this.setState({
            createViewType: "update",
            createViewId: id,
            isCreateView: 1,
        });
        // this.props.history.push(`/add-employee/${id}`);

    }
    deleteEmployee(id)
    {
        ManagerServices.deleteEmployee(id).then(res => {
            this.setState({employees:this.state.employees.filter(employee => employee.id != id)});

        });

    }
    loadEmployees()
    {
        if(this.state.isMyInfo)
        {
            ManagerServices.getEmployeeByEmail(this.state.empEmail).then((res)=>{
                this.setState({employees: [res.data]});
            });    
        }
        else if(this.state.isManager)
        {
            ManagerServices.getEmployeeByManager(this.state.empEmail).then((res)=>{
                this.setState({employees: res.data});
            });
        }
        else if(this.state.isHr)
        {
            ManagerServices.getEmployees().then((res)=>{
                this.setState({employees: res.data});
            });
        }
    }
    componentDidMount()
    {
        this.loadEmployees();
    }
    addEmployee()
    {
        this.setState({
            createViewType: "create",
            isCreateView: 1,
        });
        // this.props.history.push('/add-employee/-1')
    }
    addEmployeeButton()
    {
        if (this.state.isHr)
        {
            return (
                <div>
                    <button style={{marginLeft: "60px"}} className="btn-btn-primary" onClick={this.addEmployee}>Add Employee</button>
                    <button className= "btn btn-primary" style={{marginLeft: "60px"}}><a style={{color: "white"}} href="https://community.cloud.databricks.com/?o=8041837329188487#notebook/1509371316070823/dashboard/3190104837882576/present">Link to HR Dashboard</a></button>
                </div>
            )
        }
    }
    render() {
        let renderedView;
        if(this.state.isCreateView)
        {
            renderedView = <CreateUpdateEmployee
                type={this.state.createViewType}
                id={this.state.createViewId}
                handleCompletion={this.handleCreateUpdateCompletion}/>;
        }
        else
        {
            renderedView = (
                <div>
                    <h2 className ="text-center">Employees List</h2>
                    <div className="row">
                    {/* <button style={{marginLeft: "60px"}} className="btn-btn-primary" onClick={this.addEmployee}>Add Employee</button> */}
                    {
                        this.addEmployeeButton()
                    }
                    </div>
                    <div className="row">
                        <table className="table table-striped" style={{marginLeft: "40px", marginRight:"60px"}} >
                            <thead>
                                <tr>
                                    <th>Employee First Name</th>
                                    <th>Employee Last Name</th>
                                    <th>Email</th>
                                    <th>Manager Email</th>
                                    <th>Salary</th>

                                    {this.state.isHr &&
                                        <th>Actions</th>
                                    }
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
                                    <th>{employee.managerEmail}</th>
                                    <th>{employee.salary}</th>

                                    {this.state.isHr &&
                                        <td>
                                            <button style={{marginLeft: "150px"}} onClick={()=> this.editEmployee(employee.id)} className="btn btn-info">Update</button>
                                            <button style={{marginLeft: "10px"}} onClick={()=> this.deleteEmployee(employee.id)} className="btn btn-danger">Delete</button>
                                        </td>
                                    }
                                    </tr>
                                )
                            }
                            </tbody>
                        </table>
                    </div>
                </div>
            );
        }
        return (
            <div>
            {renderedView}
            </div>
            );
    }
}
export default withRouter(ListEmployees);