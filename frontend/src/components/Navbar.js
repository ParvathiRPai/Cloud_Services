import React from 'react';
import { Link, NavLink, withRouter } from 'react-router-dom'

const Navbar = (props) => {
  return (
    <nav className="nav-wrapper">
    <a className="brand-logo right" href="#">SmartSalary</a>
     <ul id="nav-mobile" class="left hide-on-med-and-down">
          <li><NavLink exact to="/">Home</NavLink></li>
          <li><NavLink to='/engineer'>Engineer</NavLink></li>
          <li><NavLink to='/manager'>Manager</NavLink></li>
          <li><NavLink to='/hr'>HR</NavLink></li>
        </ul>
    </nav> 
  )
}

export default withRouter(Navbar)