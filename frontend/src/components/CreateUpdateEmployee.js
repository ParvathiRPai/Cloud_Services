import React, { Component } from 'react'
import ManagerServices from '../services/ManagerServices';

export default class CreateUpdateEmployee extends Component {
    constructor(props)
    {
        super(props)
        this.state={
            emailId: '',
            firstName: '',
            lastName: '',
            managerEmail: '',
            salary: 0.0,
            id: this.props.id,
            isCreate: this.props.type === "create",
            isUpdate: this.props.type === "update",
        }
        this.changeFirstNameHandler=this.changeFirstNameHandler.bind(this);
        this.changeLastNameHandler=this.changeLastNameHandler.bind(this);
        this.changeEmailId=this.changeEmailId.bind(this);
        this.changeManagerEmail=this.changeManagerEmail.bind(this);
        this.changeSalary=this.changeSalary.bind(this);

        this.saveEmployee=this.saveEmployee.bind(this);
        
    }
    componentDidMount(){
        if(this.state.isCreate)
        {
            return;
        }
        else
        {
            ManagerServices.getEmployeeById(this.state.id)
                .then((res)=> {
                    let employee=res.data;
                    this.setState(
                    {
                        firstName: employee.first_name,
                        lastName: employee.last_name, 
                        emailId: employee.emailid,
                        managerEmail: employee.managerEmail,
                        salary: employee.salary,
                    });
                });
        }
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
    changeManagerEmail=(event)=> {
        this.setState({managerEmail: event.target.value});
    }
    changeSalary=(event)=> {
        this.setState({salary: event.target.value});
    }

    saveEmployee=(e)=>{
        e.preventDefault();
        let employee={
            emailid: this.state.emailId,
            first_name: this.state.firstName,
            last_name: this.state.lastName,
            salary: this.state.salary,
            managerEmail: this.state.managerEmail};
        console.log('employee'+ JSON.stringify(employee));
    

            if(this.state.isCreate)
            {
                ManagerServices.createEmployee(employee)
                .then(res => {
                    this.props.handleCompletion();
                    // this.props.history.push('/hr'); 
                });
            }
            else
            {
                ManagerServices.updateEmployee(employee, this.state.id)
                .then(res => {
                    this.props.handleCompletion();
                    // this.props.history.push('/hr');
                });
            }
    }

    cancel(){
        this.props.handleCompletion();
        // this.props.history.push('/hr')
    }

    getTitle()
    {
        if(this.state.isCreate)
        {
            return <h3 className="text-center">Add Employee</h3>
        }else
        {
            return <h3 className="text-center">Update Employee</h3>
        }
    }
    
    render() {
        return (
            <div>
                <div className="container">
                <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                {
                    this.getTitle()
                }
                <div className="card-body">
                <form>
                    <div className="form-group">
                        <label>First Name</label>
                        <input placeholder="First Name" name="firstName" className="form-control"
                        value={this.state.firstName} onChange={this.changeFirstNameHandler}/>
                    </div>
                    <div className="form-group">
                        <label>Last Name</label>
                        <input placeholder="First Name" name="lastName" className="form-control"
                        value={this.state.lastName} onChange={this.changeLastNameHandler}/>
                    </div>
                    <div className="form-group">
                        <label>Email Address</label>
                        <input placeholder="Email Address" name="emailId" className="form-control"
                        value={this.state.emailId} onChange={this.changeEmailId}/>
                    </div>
                    <div className="form-group">
                        <label>Manager Email</label>
                        <input placeholder="Manager Email" name="managerEmail" className="form-control"
                        value={this.state.managerEmail} onChange={this.changeManagerEmail}/>
                    </div>
                    <div className="form-group">
                        <label>Salary</label>
                        <input placeholder="Salary" name="salary" className="form-control"
                        value={this.state.salary} onChange={this.changeSalary}/>
                    </div>
                    <button className="btn btn-success" onClick={this.saveEmployee}>Save</button>
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
