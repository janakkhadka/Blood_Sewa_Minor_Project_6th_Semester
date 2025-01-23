import React, { useState,useEffect } from 'react'

import './LandingPage.css'
import '../Common/Variables.css'
import { useNavigate, useLocation } from "react-router-dom";

import Home from './Home'
import NavigationBar from '../Common/NavigationBar'
import BackThreeD from '../LoginRegistration/3d'
import { NavbarRightLeft, NavbarRightRight } from '../Common/CommonNavBarComponent'
import { UserDashboardNavbarRightLeft, UserDashboardNavbarRightRight } from '../UserDashboard/UserNavbarComponent';
import { useUserAuthToken } from '../../Logic/AuthKey';



function LandingPage() {
  const userAuthToken = useUserAuthToken();
  return (
    <div className='landing-page-wrapper'>
        <div className="syringe">
          <BackThreeD/>
        </div>
        {userAuthToken ? (
          <NavigationBar 
            titleNav="Blood Sewa" 
            rightLeftNav={<UserDashboardNavbarRightLeft />} 
            rightRightNav={<UserDashboardNavbarRightRight />} 
          />
          ) : !userAuthToken? (
            <NavigationBar 
              titleNav="Blood Sewa" 
              rightLeftNav={<NavbarRightLeft />} 
              rightRightNav={<NavbarRightRight />} 
            />
          ) : null}

      
        
        
        <Home/>
    </div>
  )
}

export default LandingPage






