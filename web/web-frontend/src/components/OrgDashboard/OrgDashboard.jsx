import React from 'react'

import NavigationBar from '../Common/NavigationBar'
import './OrgDashboard.css'
import { OrgDashboardNavbarRightLeft, OrgDashboardNavbarRightRight } from './OrgNavbarComponent'

function OrgDashboard() {
  return (
    <div className="org-dashboard-wrapper">
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgDashboardNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />
    </div>
  )
}

export default OrgDashboard