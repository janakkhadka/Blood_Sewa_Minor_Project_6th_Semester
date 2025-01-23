import { useState, useEffect } from 'react';

export const useUserAuthToken = () => {
  const [userAuthToken, setUserAuthToken] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const userToken = localStorage.getItem('userAuthToken') || sessionStorage.getItem('userAuthToken');
    setUserAuthToken(userToken);
    setIsLoading(false);
  }, []);

  if(isLoading) {
    // console.log('userTokenAuthKey.js:'+userAuthToken);
    return null
  }

    // console.log('userToken:'+userAuthToken);
    return userAuthToken;

};

export const useOrgAuthToken = () => {
  const [orgAuthToken, setOrgAuthToken] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const orgToken = localStorage.getItem('orgAuthToken') || sessionStorage.getItem('orgAuthToken');
    setOrgAuthToken(orgToken);
    setIsLoading(false);
  }, []);

  if(isLoading) {
    return null;
  }
    // console.log('orgToken:'+orgAuthToken);
    return orgAuthToken;
};
