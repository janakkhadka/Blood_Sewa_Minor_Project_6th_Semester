import React from 'react'

import NavigationBar from '../Common/NavigationBar'
import './OrgDashboard.css'
import { OrgDashboardNavbarRightLeft, OrgDashboardNavbarRightRight } from './OrgNavbarComponent'
import BackThreeD from '../LoginRegistration/3d'


import { format } from "date-fns";
import { useNavigate } from 'react-router-dom';
import MyBarChart from '../Utils/MyBarChart'

function OrgDashboard() {
    const navigate = useNavigate();
  return (
    <div className="org-dashboard-wrapper">
        <div className="syringe">
          <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgDashboardNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />

        <div className="org-dashboard">
            <div className="left-section">
                <section className='quick-actions'>
                <h1>Quick Actions!</h1>
                    <button className="action-button" onClick={() => navigate("/blood-request-form")}>Urgent Blood Request</button>
                    <button className="action-button" onClick={() => navigate("/schedule-donation")}>Schedule a donation</button>
                    <button className="action-button" onClick={() => navigate("/user-organize-event")}>Organize an event</button>
                    <button className="action-button" onClick={() => navigate("/user-blood-availability")}>Blood Inventory</button>
                    <button className="action-button" onClick={() => navigate("/search-donor")}>Find Donor</button>
                </section>
            </div>
            <div className="bar-chart">
                <MyBarChart/>
            </div>
        </div>
    </div>
  )
}

export default OrgDashboard