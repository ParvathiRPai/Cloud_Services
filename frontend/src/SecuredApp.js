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
import CreateEmployee from './components/CreateEmployee';
import UpdateEmployee from './components/UpdateEmployee';


const oktaAuth = new OktaAuth({
    issuer: process.env.REACT_APP_OKTA_ISSUER,
    clientId: process.env.REACT_APP_OKTA_CLIENT_ID,
    redirectUri: window.location.origin + '/login/callback',
  });

  const oktaSignInConfig = {
    baseUrl: process.env.REACT_APP_OKTA_ORG_URL,
    clientId: process.env.REACT_APP_OKTA_CLIENT_ID,
    redirectUri: window.location.origin + '/login/callback',
    authParams: {
      // If your app is configured to use the Implicit Flow
      // instead of the Authorization Code with Proof of Code Key Exchange (PKCE)
      // you will need to uncomment the below line
      // pkce: false
    }
  };
  
function SecuredApp() {
    const history = useHistory();

  const onAuthRequired = function() {
    history.push('/login')
  }
 

  return (
    <Security oktaAuth={oktaAuth} onAuthRequired={onAuthRequired} >
      <Navbar />
      <Route path='/' exact={true} component={Home}/>
      <Route path='/login/callback' component={LoginCallback}/>
      {/* <Route path='/login' render={() => <Login config={oktaSignInConfig} />} /> */}
      <SecureRoute path='/HR' component={HR}/>
      <SecureRoute path='/manager' component={manager}/>
      <SecureRoute path='/add-employee/:id' component={CreateEmployee}></SecureRoute>
    </Security>
  );
}

export default SecuredApp;