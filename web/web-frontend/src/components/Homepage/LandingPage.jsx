import React, { useState } from 'react'

import './LandingPage.css'
import '../Common/Variables.css'

import Home from './Home'
import NavigationBar from '../Common/NavigationBar'
import BackThreeD from '../LoginRegistration/3d'
import { NavbarRightLeft, NavbarRightRight } from '../Common/CommonNavBarComponent'
import { UserDashboardNavbarRightLeft, UserDashboardNavbarRightRight } from '../UserDashboard/UserNavbarComponent';



function LandingPage() {
  const userAuthToken1 = localStorage.getItem('userAuthToken');
  const userAuthToken2 = sessionStorage.getItem('userAuthToken');
  return (
    <div className='landing-page-wrapper'>
        <div className="syringe">
          <BackThreeD/>
        </div>
        {userAuthToken1!==null || userAuthToken2!==null?
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






