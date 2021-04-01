import React, { Component } from 'react'
import ManagerServices from '../services/ManagerServices';

export default class UpdateEmployee extends Component {
    constructor(props)
    {
        super(props)
        this.state={
            emailId: '',
            firstName: '',
            lastName: '',
            empId: this.props.match.params.id
        }
        this.changeFirstNameHandler=this.changeFirstNameHandler.bind(this);
        this.changeLastNameHandler=this.changeLastNameHandler.bind(this);
        this.changeEmailId=this.changeEmailId.bind(this);
        this.updateEmployee=this.updateEmployee.bind(this);
    }
    componentDidMount(){
        ManagerServices.getEmployeeById(this.state.empId).then((res)=> {
            let employee=res.data;
            this.setState({firstName: employee.firstName,
            lastName: employee.lastName, 
        emailId: employee.emailId});
        } )
    }
    changeFirstNameHandler=(event)=>{
        this.setState({firstName: event.target.value});
    }
    changeLastNameHandler=(event)=>{
        this.setState({lastName: event.target.value});
    }
    changeEmailId=(event)=> {
        this.setState({emailId: event.target.value});
    }
    updateEmployee=(e)=>{
        e.preventDefault();
        let employee={emailid: this.state.emailId, first_name: this.state.firstName, last_name: this.state.lastName, id: this.state.empId};
        console.log('employee'+ JSON.stringify(employee));
        ManagerServices.updateEmployee(employee, this.state.id).then(res => {
            this.props.history.push('/hr');
        });
    }
    cancel(){
        this.props.history.push('/hr')
    }
    render() {
        return (
            <div>
                <div className="container">
                <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                <h3 className="text-center">Update Employee</h3>
                <div className="card-body">
                <form>
                <div className="form-group">
                    <label>First Name</label>
                    <input placeholder="First Name" name="first_name" className="form-control"
                    value={this.state.firstName} onChange={this.changeFirstNameHandler}/>
                </div>
                <div className="form-group">
                    <label>Last Name</label>
                    <input placeholder="First Name" name="last_name" className="form-control"
                    value={this.state.lastName} onChange={this.changeLastNameHandler}/>
                </div>
                <div className="form-group">
                    <label>Email Address</label>
                    <input placeholder="Email Address" name="emailid" className="form-control"
                    value={this.state.emailId} onChange={this.changeEmailId}/>
                </div>
                <button className="btn btn-success" onClick={this.updateEmployee}>Save</button>
                <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}> Cancel</button>
                </form>

                </div>
                </div>
                </div>
                </div>
            </div>
        )
    }
}
