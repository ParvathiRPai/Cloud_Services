import React, { Component } from 'react'
import ListEmployees from '../components/ListEmployees'
export default class hr extends Component {
    render() {
        return (
          <ListEmployees isHr="1"/>
        )
    }
}
