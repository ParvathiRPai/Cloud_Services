import React, { Component } from 'react'
import ListEmployees from '../components/ListEmployees'
export default class hr extends Component {
    render() {
        return (
          <ListEmployees isMyInfo="1" empEmail={this.props.myEmail}/>
        )
    }
}
