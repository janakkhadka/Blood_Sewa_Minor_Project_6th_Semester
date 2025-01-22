import React from 'react';
import { Navigate } from 'react-router-dom';


const OrgPrivateRoute = ({ children }) => {
  const authToken = localStorage.getItem('orgAuthToken') || sessionStorage.getItem('orgAuthToken');

  // Check if an auth token exists, if not, redirect to the homepage
  return authToken ? children : <Navigate to="/" replace />;
};

export default OrgPrivateRoute;
