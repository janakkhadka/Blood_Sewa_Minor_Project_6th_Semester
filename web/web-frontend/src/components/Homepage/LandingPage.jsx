import React, { useState } from 'react'

import './LandingPage.css'
import '../Common/Variables.css'

import Home from './Home'
import NavigationBar from '../Common/NavigationBar'
import BackThreeD from '../LoginRegistration/3d'
import { NavbarRightLeft, NavbarRightRight } from '../Common/CommonNavBarComponent'



function LandingPage() {
  return (
    <div className='landing-page-wrapper'>
        <div className="syringe">
          <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<NavbarRightLeft/>}
          rightRightNav = {<NavbarRightRight/>} 
        />
        <Home/>
    </div>
  )
}

export default LandingPage






