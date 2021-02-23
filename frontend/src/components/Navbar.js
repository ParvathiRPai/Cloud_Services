import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import { useOktaAuth } from '@okta/okta-react';
import MonetizationOnIcon from '@material-ui/icons/MonetizationOn';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  typography: {
    flexGrow: 1,
    textAlign: 'left'
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
            <Typography variant="h5" className={classes.typography}>           
              SmartSalary Portal
              <IconButton>
                <MonetizationOnIcon/>
            </IconButton>
            </Typography>
            <Button component={Link} to="/" color="inherit">Home</Button>
            {authState.isAuthenticated && userInfo && userInfo.groups.includes('HR') &&
              <Button component={Link} to="/hr" color="inherit">Salary Insights</Button>
            }
            {authState.isAuthenticated && userInfo && userInfo.groups.includes('Manager') &&
              <Button component={Link} to="/manager" color="inherit">Employees List</Button>
            }
            {userText}
          </Toolbar>
        </AppBar>
      </div>
    );
}

export default NavBar;