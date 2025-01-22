import React from 'react';
import { Navigate } from 'react-router-dom';

const UserPrivateRoute = ({ children }) => {
  const authToken1 = localStorage.getItem('userAuthToken');
  const authToken2 = sessionStorage.getItem('userAuthToken');
  if(authToken1){
    return authToken1 ? children : <Navigate to="/" replace />;
  }else if(authToken2){
    return authToken2 ? children : <Navigate to="/" replace />;
  }
};

export default UserPrivateRoute;