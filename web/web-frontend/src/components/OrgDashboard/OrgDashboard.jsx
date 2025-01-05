import React from 'react'

import NavigationBar from '../Common/NavigationBar'
import './OrgDashboard.css'
import { OrgDashboardNavbarRightLeft, OrgDashboardNavbarRightRight } from './OrgNavbarComponent'
import BackThreeD from '../LoginRegistration/3d'

import { IoMdSquare } from "react-icons/io";
import { TbAxisX, TbAxisY } from "react-icons/tb";

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
            <div className="top-section">
                <section className='quick-actions'>
                    <h1>Quick Actions!</h1>
                    <button className="action-button" onClick={() => navigate("/blood-request-form")}>Urgent Blood Request</button>
                    <button className="action-button" onClick={() => navigate("/schedule-donation")}>Scheduled Donation</button>
                    <button className="action-button" onClick={() => navigate("/events")}>Manage Events</button>
                    <button className="action-button" onClick={() => navigate("/user-blood-availability")}>Blood Inventory</button>
                    <button className="action-button" onClick={() => navigate("/search-donor")}>Find Donor</button>
                </section>

                <section className="blood-inventory-section">
                    <div className="h1-wrapper">
                        <h1>Blood Inventory</h1>
                        <div className="alert-wrapper">
                            <h3 className='alert'>Alert!</h3>
                        </div>
                    </div>
                    <div className="bar-chart-wrapper">
                        <div className="chart-info-wrapper">
                            <div className="chart-info">
                                <TbAxisX/>
                                <span>X-Axis: Blood Group</span>
                            </div>
                            <div className="chart-info">
                                <TbAxisY/>
                                <span>Y-Axis: Pint Value</span>
                            </div>
                            <div className="chart-info">
                                <IoMdSquare style={{color:"#8B0000"}}/>
                                <span>Pint value range 0-10</span>
                            </div>
                            <div className="chart-info">
                                <IoMdSquare style={{color:"#ff9510"}}/>
                                <span>Pint value range 11-25</span>
                            </div>
                            <div className="chart-info">
                                <IoMdSquare style={{color:"rgb(25, 160, 25)"}}/>
                                <span>Pint value greater than 25</span>
                            </div>
                        </div>
                        <MyBarChart className='bar-chart'/>
                    </div>
                </section>
            </div>
            <div className="bottom-section">
                
            </div> 
        </div>
    </div>
  )
}

export default OrgDashboard