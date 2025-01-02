import React from 'react'

import './UserDashboard.css'

import BackThreeD from '../LoginRegistration/3d'
import UserDashboardHome from './UserDashboardHome'

function UserDashboard() {
  return (
    <div className="user-dashboard-wrapper">
      <div className="syringe">
          <BackThreeD/>
        </div>
        <UserDashboardHome/> 
    </div>
  )
}

export default UserDashboard