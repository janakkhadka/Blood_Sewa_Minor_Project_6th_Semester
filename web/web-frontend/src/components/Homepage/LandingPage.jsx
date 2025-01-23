import React, { useState,useEffect } from 'react'

import './LandingPage.css'
import '../Common/Variables.css'

import Home from './Home'
import NavigationBar from '../Common/NavigationBar'
import BackThreeD from '../LoginRegistration/3d'
import { NavbarRightLeft, NavbarRightRight } from '../Common/CommonNavBarComponent'
import { UserDashboardNavbarRightLeft, UserDashboardNavbarRightRight } from '../UserDashboard/UserNavbarComponent';
import { userAuthToken } from '../../Logic/AuthKey'



function LandingPage() {
  const [authToken, setAuthToken] = useState('');
  const [isLoading, setIsLoading] = useState(true); 

  useEffect(() => {
    const token = userAuthToken; 
    setTimeout(() => {
      setAuthToken(token);
      setIsLoading(false); 
    }, 1000);
  }, []);

  if (isLoading) {
    return (
      <div className="loading-screen">
        <h2></h2>
      </div>
    );
  }
  return (
    <div className='landing-page-wrapper'>
        <div className="syringe">
          <BackThreeD/>
        </div>
        {authToken?
          <NavigationBar 
            titleNav = "Blood Sewa" 
            rightLeftNav = {<UserDashboardNavbarRightLeft/>}
            rightRightNav = {<UserDashboardNavbarRightRight/>} 
          />:
          <NavigationBar 
            titleNav = "Blood Sewa" 
            rightLeftNav = {<NavbarRightLeft/>}
            rightRightNav = {<NavbarRightRight/>} 
          />
        }
      
        
        
        <Home/>
    </div>
  )
}

export default LandingPage






