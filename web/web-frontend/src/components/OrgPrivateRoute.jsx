import React from 'react';
import { Navigate } from 'react-router-dom';

const OrgPrivateRoute = ({ children }) => {
  const authToken1 = localStorage.getItem('orgAuthToken');
  const authToken2 = sessionStorage.getItem('orgAuthToken');
  if(authToken1){
    return authToken1 ? children : <Navigate to="/" replace />;
  }else if(authToken2){
    return authToken2 ? children : <Navigate to="/" replace />;
  }
};

export default OrgPrivateRoute;