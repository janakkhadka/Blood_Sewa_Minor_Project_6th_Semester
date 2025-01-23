import React from 'react';
import { Navigate } from 'react-router-dom';
import { useOrgAuthToken } from '../Logic/AuthKey';


const OrgPrivateRoute = ({ children }) => {
  const orgAuthToken = useOrgAuthToken();
  console.log('orgroutetoken:'+orgAuthToken);
  if (orgAuthToken === null) {
    return <div></div>;
  }
  return orgAuthToken ? children : <Navigate to="/" replace />;
};

export default OrgPrivateRoute;
