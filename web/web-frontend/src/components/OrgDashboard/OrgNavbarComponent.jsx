import React, { useState } from 'react'


import { FaHome } from "react-icons/fa";
import { MdNotificationsActive, MdNotifications, MdDashboard } from "react-icons/md";
import { IoPersonCircle } from "react-icons/io5";

import { Link, useNavigate } from "react-router-dom";


//organization dashboard ko lagi
export const OrgDashboardNavbarRightLeft = () => {
    const [toggle, setToggle] = useState(false)
    const setToggleChange = () => {
      setToggle(prevToggle => !prevToggle)
    }
    return(
      <div className="navbar-right-left">
          <div className="icons-wrapper">
            
            <button className="notification-button" onClick={setToggleChange}>
                <MdNotifications className='icon'/>
            </button>
            {toggle && (
            <div className="notification-list">
                <Link
                to="/event-detail"
                state= {{ nofiticationId: "user" }}
                >
                Notification 1
                </Link>
                <Link
                to="/event-detail"
                state={{ notificationId: "organization" }}
                >
                Notification 2
                </Link>
            </div>
            )}
          </div>
      </div>
    )
  }



export const OrgDashboardNavbarRightRight = () => {
    return (
      <div className="navbar-right-right">
        Red Cross Nepal
        <button className="logout-button">
          Logout
        </button>
      </div>
    );
  };