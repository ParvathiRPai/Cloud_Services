import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import { useOktaAuth } from '@okta/okta-react';
import manager from '../pages/manager';
import { container } from 'aws-amplify';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },

}));

function NavBar() {
  const classes = useStyles();
  const { oktaAuth, authState } = useOktaAuth();
  const [userInfo, setUserInfo] = useState(null);
  const login = () => { oktaAuth.signInWithRedirect(); }
  const logout = () => { oktaAuth.signOut(); }

  const userText = authState.isAuthenticated
    ? <button onClick={ logout }>SignOut</button>
    : <button onClick={ login }>SignIn</button>;

    useEffect(() => {
      if (!authState.isAuthenticated) {
        // When user isn't authenticated, forget any user info
        setUserInfo(null);
      } else {
        oktaAuth.getUser().then(setUserInfo);
      }
    }, [oktaAuth, authState]);
      
    return(
      <div>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" className={classes.title}>
              SmartSalary Portal
            </Typography>
            <Button component={Link} to="/">Home</Button>
            {authState.isAuthenticated && userInfo && userInfo.groups.includes('HR') &&
              <Button component={Link} to="/hr">Salary Insights</Button>
            }
            {authState.isAuthenticated && userInfo && userInfo.groups.includes('Manager') &&
              <Button component={Link} to="/manager">Performance Dashboard</Button>
            }
            {userText}
          </Toolbar>
        </AppBar>
      </div>
    );
}

export default NavBar;