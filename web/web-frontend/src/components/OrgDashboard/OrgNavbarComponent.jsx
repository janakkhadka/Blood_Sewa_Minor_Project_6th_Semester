import React, { useState } from 'react'


import { FaHome } from "react-icons/fa";
import { MdNotificationsActive, MdNotifications, MdDashboard } from "react-icons/md";
import { IoPersonCircle } from "react-icons/io5";

import { Link, Navigate, useNavigate } from "react-router-dom";


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
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem('orgAuthToken');
    sessionStorage.removeItem('orgAuthToken');
    navigate("/login", {
      state: { accountType: "organization" }
    });
  };    
    return (
      <div className="navbar-right-right">
        Red Cross Nepal
        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
      </div>
    );
};


  //different component ko lagi rightleft hai ta
  export const OrgComponentNavbarRightLeft = () => {
    const [toggle, setToggle] = useState(false)
    const setToggleChange = () => {
      setToggle(prevToggle => !prevToggle)
    }
    return(
      <div className="navbar-right-left">
          <div className="icons-wrapper">
            <Link to= "/org-dashboard" state={{ loginState: true }}>
                <MdDashboard className='icon'/>
            </Link>
            
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