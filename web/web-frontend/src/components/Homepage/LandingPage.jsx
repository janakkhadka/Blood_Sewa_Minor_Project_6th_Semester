import React, { useState } from 'react'

import './LandingPage.css'

import Home from './Home'
import HomepageNavigationBar from '../Common/NavigationBar'
import BackThreeD from '../LoginRegistration/3d'

import { Link } from "react-router-dom";
import { HashLink } from "react-router-hash-link";

function LandingPage() {
  const [titleNav, setTitleNav] = useState('')
  const [rightLeftNav, setRightLeftNav] = useState(null)
  const [rightRightNav, setRightRightNav] = useState(null)
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
        <Link to='/request-blood'>Request Blood Now</Link>
    </div>
  )
}

const NavbarRightRight = () => {
  
  return (
    <div className="navbar-right-right">
      <Link to="/login">Login</Link>
    </div> 
  )
}

