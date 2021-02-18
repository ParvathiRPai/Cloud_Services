import React, { Component } from 'react'
import ManagerServices from '../services/ManagerServices'
export default class ListManagers extends Component {
    constructor(props){
        super(props)
        this.state={
            managers: []
        }
    }
    componentDidMount()
    {
        ManagerServices.getEmployees().then((res)=>{
            this.setState({managers: res.data});

        });
    }
    render() {
        return (
            <div>                
            <div className="row">
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Manager First Name</th>
                                <th>Manager Last Name</th>
                                <th>Gender</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.managers.map(
                                manager=>
                                <tr  key={manager.emp_no}>
                                <th>{manager.first_name}</th>
                                <th>{manager.last_name}</th>
                                <th>{manager.gender}</th>
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
