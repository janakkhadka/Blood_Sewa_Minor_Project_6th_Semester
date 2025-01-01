import React from 'react'

import './LandingPage.css'

import Home from './Home'
import HomepageNavigationBar from './HomepageNavigationBar'
import BackThreeD from '../LoginRegistration/3d'

function LandingPage() {
  return (
    <div className='landing-page-wrapper'>
        <div className="syringe">
          <BackThreeD/>
        </div>
        <HomepageNavigationBar/>
        <Home/>
    </div>
  )
}

export default LandingPage