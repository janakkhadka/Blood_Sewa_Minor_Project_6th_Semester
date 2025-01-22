import React from 'react';
import { Navigate } from 'react-router-dom';

const UserPrivateRoute = ({ children }) => {
  const authToken = localStorage.getItem('userAuthToken') || sessionStorage.getItem('userAuthToken');

  return authToken ? children : <Navigate to="/" replace />;
};

export default UserPrivateRoute;
