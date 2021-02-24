import React from 'react';
import { useHistory } from 'react-router';
import { Route } from 'react-router-dom';
import { LoginCallback, SecureRoute, Security } from '@okta/okta-react';
import { OktaAuth } from '@okta/okta-auth-js';
import Navbar from './components/Navbar';
import Home from './pages/home';
import Login from './pages/Login';
import HR from './pages/hr';
import manager from './Employees/ListManagers';

const oktaAuth = new OktaAuth({
    issuer: process.env.REACT_APP_OKTA_ISSUER,
    clientId: process.env.REACT_APP_OKTA_CLIENT_ID,
    redirectUri: `${window.location.origin}/login/callback`,
  });


function SecuredApp() {
    const history = useHistory();

  const onAuthRequired = function() {
    history.push('/login')
  }
 

  return (
    <Security oktaAuth={oktaAuth} onAuthRequired={onAuthRequired} >
      <Navbar />
      <Route path='/' exact={true} component={Home}/>
      <Route path='/login' exact={true} component={Login} />
      <SecureRoute path='/HR' component={HR}/>
      <SecureRoute path='/manager' component={manager}/>
      <Route path='/login/callback' component={LoginCallback}/>
    </Security>
  );
}

export default SecuredApp;