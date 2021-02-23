import React from 'react';
import { Redirect } from 'react-router-dom';
import { useOktaAuth } from '@okta/okta-react';
import OktaSignInWidget from '../components/OktaSignInWidget';

function Login() {
  const { oktaAuth, authState } = useOktaAuth();

  const onSuccess = function(res) {
    if (res.status === 'SUCCESS') {
      return oktaAuth.signInWithRedirect({
        sessionToken: res.session.token
      });
    }
  }

  const onError = function(err) {
    console.log('error logging in', err);
  }

  return authState.isAuthenticated ?
    <Redirect to={{ pathname: '/' }}/> :
    <OktaSignInWidget
      baseUrl={process.env.REACT_APP_OKTA_ORG_URL}
      onSuccess={onSuccess}
      onError={onError}/>;
}

export default Login;