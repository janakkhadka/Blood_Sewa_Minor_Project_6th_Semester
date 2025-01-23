import React from 'react';
import { Navigate } from 'react-router-dom';
import { userAuthToken } from '../Logic/AuthKey';

const UserPrivateRoute = ({ children }) => {
  return userAuthToken ? children : <Navigate to="/" replace />;
};

export default UserPrivateRoute;
