import React, { useState } from 'react'


import { FaHome } from "react-icons/fa";
import { MdNotificationsActive, MdNotifications, MdDashboard } from "react-icons/md";
import { IoPersonCircle } from "react-icons/io5";

import { Link, useNavigate } from "react-router-dom";

//user login vaye paxi homepage maa dekhine navbar ko lagi --kaam garnai baaki xa
export const UserNavbarRightLeft = () => {
    return(
      <div className="navbar-right-left">
          <Link to="/blood-request">Request Blood Now</Link>
      </div>
    )
  }
  
export const UserNavbarRightRight = () => {
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



//   user dashboard ko lagi  -completed
export const UserDashboardNavbarRightLeft = () => {
    const [toggle, setToggle] = useState(false)
    const setToggleChange = () => {
      setToggle(prevToggle => !prevToggle)
    }
    return(
      <div className="navbar-right-left">
          <div className="icons-wrapper">
            <Link to= "/" state={{ loginState: true }}>
                <FaHome className='icon'/>
            </Link>
            <Link to= "/user-profile" state={{ loginState: true }}>
                <IoPersonCircle className='icon'/>
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
  
export const UserDashboardNavbarRightRight = () => {
    const navigate = useNavigate();
    const handleLogout = () => {
      localStorage.removeItem('userAuthToken');
      sessionStorage.removeItem('userAuthToken');
      navigate('/');
    };
    return (
      <div className="navbar-right-right">
        Welcome, Janak
        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
      </div>
    );
  };





  //user ko different components haruko lagi
  export const UserComponentNavbarRightLeft = () => {
    const [toggle, setToggle] = useState(false)
    const setToggleChange = () => {
      setToggle(prevToggle => !prevToggle)
    }
    return(
      <div className="navbar-right-left">
          <div className="icons-wrapper">
            <Link to= "/" state={{ loginState: true }}>
                <FaHome className='icon'/>
            </Link>
            <Link to= "/user-dashboard" state={{ loginState: true }}>
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
  
export const UserComponentNavbarRightRight = () => {
    return (
      <div className="navbar-right-right">
        Welcome, Janak
        <button className="logout-button">
          Logout
        </button>
      </div>
    );
  };