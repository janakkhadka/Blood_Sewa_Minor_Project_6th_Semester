import React from 'react';
import { Navigate } from 'react-router-dom';
import { orgAuthToken } from '../Logic/AuthKey';


const OrgPrivateRoute = ({ children }) => {
  console.log(orgAuthToken);
  return orgAuthToken ? children : <Navigate to="/" replace />;
};

export default OrgPrivateRoute;
