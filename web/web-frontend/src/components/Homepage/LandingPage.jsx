import React, { useState } from 'react'

import './LandingPage.css'

import Home from './Home'
import HomepageNavigationBar from '../Common/NavigationBar'
import BackThreeD from '../LoginRegistration/3d'

import { Link, useNavigate } from "react-router-dom";

function LandingPage() {
  return (
    <div className='landing-page-wrapper'>
        <div className="syringe">
          <BackThreeD/>
        </div>
        <HomepageNavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<NavbarRightLeft/>}
          rightRightNav = {<NavbarRightRight/>} 
        />
        <Home/>
    </div>
  )
}

export default LandingPage
const NavbarRightLeft = () => {
  
  return(
    <div className="navbar-right-left">
        <Link to="/blood-request">Request Blood Now</Link>
    </div>
  )
}

const NavbarRightRight = () => {
  const [toggle, setToggle] = useState(false)
  const setToggleChange = () => {
    setToggle(prevToggle => !prevToggle)
  }
  return (
    <div className="navbar-right-right">
      <button className="login-button" onClick={setToggleChange}>
        Login
      </button>
      {toggle && (
        <div className="login-option">
          <Link
            to="/login"
              state= {{ accountType: "user" }}
          >
            Blood Donor
          </Link>
          <Link
            to="/login"
            state={{ accountType: "organization" }}
          >
            Organization
          </Link>
        </div>
      )}
    </div>
  );
};

