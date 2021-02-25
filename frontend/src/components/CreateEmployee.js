import React, { Component } from 'react'
import EmployeeServies from '../services/EmployeeServies';

export default class CreateEmployee extends Component {
    constructor(props)
    {
        super(props)
        this.state={
            manager_number:'',
            first_name:'',
            last_name: '',
            salary: ''
        }
        this.changeManagerIdHandler=this.changeManagerIdHandler.bind(this);
        this.changeFirstNameHandler=this.changeFirstNameHandler.bind(this);
        this.changeLastNameHandler=this.changeLastNameHandler.bind(this);
        this.changeSalaryHandler=this.changeSalaryHandler.bind(this);
        this.saveEmployee=this.saveEmployee.bind(this);

    }
    changeManagerIdHandler=(event)=>{
        this.setState({manager_number:event.target.value}); 
    }
    changeFirstNameHandler=(event)=>{
        this.setState({first_name:event.target.value});
    }
    changeLastNameHandler=(event)=>{
        this.setState({last_name:event.target.value});
    }
    changeSalaryHandler=(event)=>{
        this.setState({salary:event.target.value});
    }
    saveEmployee=(e)=>{
        e.preventDefault();
        let employee={manager_number:parseInt(this.state.manager_number), first_name: this.state.first_name, last_name:this.state.last_name,salary:parseInt(this.state.salary)}
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
                                <input placeholder="Manager Id" name="manager_number" className="form-control"
                                value={this.state.manager_number} onChange={this.changeManagerIdHandler}/>
                            </div>
                            <div className="form-group">
                                <label>First Name:</label>
                                <input placeholder="First Name" name="first_name" className="form-control"
                                value={this.state.first_name} onChange={this.changeFirstNameHandler}/>
                            </div>
                            <div className="form-group">
                                <label>Last Name:</label>
                                <input placeholder="Last Name" name="last_name" className="form-control"
                                value={this.state.last_name} onChange={this.changeLastNameHandler}/>
                            </div>
                            <div className="form-group">
                                <label>New Salary: </label>
                                <input placeholder="New Salary" name="salary" className="form-control"
                                value={this.state.salary} onChange={this.changeSalaryHandler}/>
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
