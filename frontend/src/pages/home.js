import React, { useState, useEffect } from "react";
import { useOktaAuth } from "@okta/okta-react";

const Home = () => {
  const { oktaAuth, authState } = useOktaAuth();
  const [userInfo, setUserInfo] = useState(null);

  useEffect(() => {
    if (!authState.isAuthenticated) {
      // When user isn't authenticated, forget any user info
      setUserInfo(null);
    } else {
      oktaAuth.getUser().then(info => {
        setUserInfo(info);
      });
    }
  }, [authState, oktaAuth]); // Update if authState changes

  return (
    <div>
      {userInfo && (
        <div>
          <br/>
          <h3>Welcome, {userInfo.name}!</h3>
        </div>
      )}
    </div>
  );
};

export default Home;