import React, { useState } from 'react'
import { Link, useNavigate } from "react-router-dom";

export const NavbarRightLeft = () => {
    return(
      <div className="navbar-right-left">
          <Link to="/blood-request">Request Blood Now</Link>
      </div>
    )
  }
  
export const NavbarRightRight = () => {
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