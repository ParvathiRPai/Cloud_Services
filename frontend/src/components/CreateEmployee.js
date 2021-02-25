import React, { Component } from 'react'
import EmployeeServies from '../services/EmployeeServies';

export default class CreateEmployee extends Component {
    constructor(props)
    {
        super(props)
        this.state={
            managerId:'',
            firstName:'',
            lastName: '',
            newSalary: ''
        }
        this.changeManagerIdHandler=this.changeManagerIdHandler.bind(this);
        this.changeFirstNameHandler=this.changeFirstNameHandler.bind(this);
        this.changeLastNameHandler=this.changeLastNameHandler.bind(this);
        this.changeSalaryHandler=this.changeSalaryHandler.bind(this);
        this.saveEmployee=this.saveEmployee.bind(this);

    }
    changeManagerIdHandler=(event)=>{
        this.setState({managerId:event.target.value}); 
    }
    changeFirstNameHandler=(event)=>{
        this.setState({firstName:event.target.value});
    }
    changeLastNameHandler=(event)=>{
        this.setState({lastName:event.target.value});
    }
    changeSalaryHandler=(event)=>{
        this.setState({newSalary:event.target.value});
    }
    saveEmployee=(e)=>{
        e.preventDefault();
        let employee={firstName: this.state.firstName, lastName:this.state.lastName, managerId:this.state.managerId,newSalary:this.state.newSalary}
        console.log('employee =>'+JSON.stringify(employee));
        EmployeeServies.createEmployeee(employee).then(res => {
            this.props.history.push('/manager');
        })
    }
    cancel(){
        this.props.history.push(`/manager`);
    }


    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                        <h3 className="text-center">Add Employee </h3>
                        <div className="card-body">
                            <form>
                            <div className="form-group">
                                <label>Manager Id:</label>
                                <input placeholder="Manager Id" name="managerId" className="form-control"
                                value={this.state.managerId} onChange={this.changeManagerIdHandler}/>
                            </div>
                            <div className="form-group">
                                <label>First Name:</label>
                                <input placeholder="First Name" name="firstName" className="form-control"
                                value={this.state.firstName} onChange={this.changeFirstNameHandler}/>
                            </div>
                            <div className="form-group">
                                <label>Last Name:</label>
                                <input placeholder="Last Name" name="lastName" className="form-control"
                                value={this.state.lastName} onChange={this.changeLastNameHandler}/>
                            </div>
                            <div className="form-group">
                                <label>New Salary: </label>
                                <input placeholder="New Salary" name="newSalary" className="form-control"
                                value={this.state.newSalary} onChange={this.changeSalaryHandler}/>
                            </div>
                            <button className="btn btn-success" onClick={this.saveEmployee}>Save</button>
                            <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft:"10px"}}>Cancel</button>
                            </form>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
