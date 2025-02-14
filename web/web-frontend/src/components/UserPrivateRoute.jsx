import React from 'react';
import { Navigate } from 'react-router-dom';
import { useUserAuthToken } from '../Logic/AuthKey';

const UserPrivateRoute = ({ children }) => {
  const userAuthToken = useUserAuthToken();
  if (userAuthToken === null) {
    return null;
  }
  return userAuthToken ? children : <Navigate to="/" replace />;
};

export default UserPrivateRoute;
