import React, { useState, useEffect } from 'react'
import { useOktaAuth } from "@okta/okta-react";
import ListEmployees from '../components/ListEmployees'

const Manager = () => {
    const { oktaAuth, authState } = useOktaAuth();
    const [userInfo, setUserInfo] = useState(null);

    useEffect(() => {
        if (!authState.isAuthenticated) {
            // When user isn't authenticated, forget any user info
            setUserInfo(null);
        }
        else {
            oktaAuth.getUser().then(info => setUserInfo(info));
        }
    }, [authState, oktaAuth]); // Update if authState changes

    return (
        <div>
            {authState.isAuthenticated && userInfo && (
                <div>
                    <ListEmployees isManager="1" empEmail={userInfo.email}/>
                </div>
            )}
        </div>
    )
}

export default Manager;
