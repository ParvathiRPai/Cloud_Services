import React, { Component } from 'react'
import ListEmployees from '../components/ListEmployees'

export default class Manager extends Component {
    render() {
        return (
            <div>
                <ListEmployees isManager="1"/>
            </div>
        )
    }
}
