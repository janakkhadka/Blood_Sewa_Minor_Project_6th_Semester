import React, { useEffect, useState, useRef } from 'react'



import { MdNotifications, MdDashboard } from "react-icons/md";

import { Link,useNavigate } from "react-router-dom";


//organization dashboard ko lagi
export const OrgDashboardNavbarRightLeft = () => {
    const [toggle, setToggle] = useState(false)
    const [number, setNumber] = useState(0)
    const socketRef = useRef(null);
    const setToggleChange = () => {
      setToggle(prevToggle => !prevToggle)
    }

    useEffect(() => {
      if (!socketRef.current) {
        socketRef.current = new WebSocket("ws://172.16.12.242:8000/ws/notifications/");
        
        socketRef.current.onopen = () => {
          console.log("WebSocket connection established");
        };
  
        socketRef.current.onclose = () => {
          console.log("WebSocket connection closed");
        };
  
        socketRef.current.onmessage = (event) => {
          const data = JSON.parse(event.data);
          console.log("Notification received:", data.message);
          setNumber((prevNumber) => prevNumber + 1);
        };
      }
  
      return () => {
        if (socketRef.current) {
          socketRef.current.close();
          socketRef.current = null;
        }
      };
    }, []);

  
    return(
      <div className="navbar-right-left">
          <div className="icons-wrapper">
            
          <div className="notification-wrapper" onClick={setToggleChange}>
              <div className="circle">
                {number}
              </div>
              <button className="notification-button">
                  <MdNotifications className='icon'/>
              </button>
            </div>
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
  const orgDetailsString = sessionStorage.getItem('orgDetails') || localStorage.getItem('orgDetails');
  // console.log('Raw orgDetailsString:', orgDetailsString);

  let orgDetails = null;
  let orgName = null;
  
  try {
    if (orgDetailsString && orgDetailsString.trim().length > 0) {
        orgDetails = JSON.parse(orgDetailsString);
        orgName = orgDetails?.name || null;
    } else {
        orgDetails = null;
        orgName = null;
    }
} catch (error) {
    console.error('Failed to parse orgDetails:', error);
    orgDetails = null;
    orgName = null; // Default to null if parsing fails
}
  const handleLogout = () => {
    localStorage.removeItem('orgAuthToken');
    sessionStorage.removeItem('orgAuthToken');
    localStorage.removeItem('orgDetails');
    sessionStorage.removeItem('orgDetails');
    navigate("/login", {
      state: { accountType: "organization" }
    });
  };    
    return (
      <div className="navbar-right-right">
        {orgName}
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
            <div className="notification-wrapper"  onClick={setToggleChange}>
              <div className="circle">
                0
              </div>
              <button className="notification-button">
                  <MdNotifications className='icon'/>
              </button>
            </div>
            
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