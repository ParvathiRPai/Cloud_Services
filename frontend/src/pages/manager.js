import React, { Component } from 'react'
import ListEmployees from '../Employees/ListEmployees'

export default class manager extends Component {
    render() {
   
        return (
            <div>
                <h1>Manager</h1>
                <ListEmployees/>
            </div>
        )
    }
}
