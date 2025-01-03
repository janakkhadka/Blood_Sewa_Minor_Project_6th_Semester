import React, { useState } from 'react'

import NavigationBar from '../Common/NavigationBar'

import { FaHome } from "react-icons/fa";
import { MdNotificationsActive, MdNotifications } from "react-icons/md";

import { Link, useNavigate } from "react-router-dom";

//user login vaye paxi homepage maa dekhine navbar ko lagi
export const HomeNavbarRightLeft = () => {
    return(
      <div className="navbar-right-left">
          <Link to="/blood-request">Request Blood Now</Link>
      </div>
    )
  }
  
export const HomeNavbarRightRight = () => {
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



//   user dashboard ko lagi
export const DashboardNavbarRightLeft = () => {
    const [toggle, setToggle] = useState(false)
    const setToggleChange = () => {
      setToggle(prevToggle => !prevToggle)
    }
    return(
      <div className="navbar-right-left">
          <div className="icons-wrapper">
            <FaHome/>
            <button className="login-button" onClick={setToggleChange}>
            <MdNotifications/>
            </button>
            {toggle && (
            <div className="notification-option">
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
  
export const DashboardNavbarRightRight = () => {
    return (
      <div className="navbar-right-right">
        Welcome, Janak Khadka
        <button className="login-button">
          Logout
        </button>
      </div>
    );
  };



  //user ko different components haruko lagi
export const ComponentNavbarRightLeft = () => {
    return(
      <div className="navbar-right-left">
          <Link to="/blood-request">Request Blood Now</Link>
      </div>
    )
  }
  
export const ComponentNavbarRightRight = () => {
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


  

  