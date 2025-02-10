import React,{useEffect,useState} from 'react'

import './UserDashboardHome.css'

import { format } from "date-fns";
import { useNavigate } from 'react-router-dom';

import { MdOutlineDateRange }from "react-icons/md";
import { IoMdTime } from "react-icons/io";
import { IoLocationOutline } from "react-icons/io5";
import { IoMdOptions } from "react-icons/io";

// import { getGreeting } from '../Utils/Greeting'
import {events, urgentBlood} from './DummyData'
import NavigationBar from '../Common/NavigationBar'
import { UserDashboardNavbarRightLeft, UserDashboardNavbarRightRight } from './UserNavbarComponent';
import {greetingMessage} from '../Utils/GreetingMessage'

import {api} from '../../Logic/api'
import { useUserAuthToken } from '../../Logic/AuthKey';

function UserDashboardHome() {
  const userAuthToken = useUserAuthToken();
  //console.log(userAuthToken)


  const navigate = useNavigate();
  const [data, setData] = useState(null); // State to store fetched data
  const [loading, setLoading] = useState(true); // Loading state
  const [error, setError] = useState(null);

  //user details taneko
  const userDetailsString = sessionStorage.getItem('userDetails') || localStorage.getItem('userDetails');
  let userDetails = null;
  let userName = "";
  
  try {
    userDetails = userDetailsString ? JSON.parse(userDetailsString) : null;
    console.log(userDetails);
    userName = userDetails.name;
  } catch (error) {
    console.error('Failed to parse userDetails:', error);
  }


  useEffect(() => {
    const fetchData = async () => {

      if (!userAuthToken) {
        setError('No auth token found. Please log in.');
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(api+'user/all/', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${userAuthToken}`, // Attach the token
          },
        });

        if (!response.ok) {
          throw new Error(`Error: ${response.status}`);
        }

        const result = await response.json(); // Parse JSON response
        setData(result); // Store data in state
        console.log(result)
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false); // Stop loading
      }
    };

    fetchData();
  }, [userAuthToken]);

  // if (loading) return <p>Loading...</p>;
  // if (error) return <p>Error: {error}</p>;
  return (
    <div className='user-dashboard-home-wrapper'>
      <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<UserDashboardNavbarRightLeft/>}
          rightRightNav = {<UserDashboardNavbarRightRight/>} 
        />
      <div className="user-dashboard-top-section">
        <div className="left-section">
          <section className="user-greeting-message">
                <h1>{greetingMessage()+", Janak Khadka!"}</h1>
                <div className="user-typing-effect">
                  <span>Happy to see you.</span>
                </div>
          </section>
          <section className="eligibility-message">
            <h1>Donation Eligibility</h1>
            <span>You are eligible to donate</span>
          </section>
          <section className='quick-actions'>
            <h1>Quick Actions!</h1>
            <button className="action-button" onClick={() => navigate("/blood-request-form", { state: { identifier: 1 }})}>Urgent Blood Request</button>
            <button className="action-button" onClick={() => navigate("/schedule-donation")}>Schedule a donation</button>
            <button className="action-button" onClick={() => navigate("/user-organize-event")}>Organize an event</button>
            <button className="action-button" onClick={() => navigate("/user-blood-availability")}>Blood Inventory</button>
            <button className="action-button" onClick={() => navigate("/search-donor")}>Find Donor</button>
          </section>
        </div>
        <div className="right-section">

          <section className='user-reminder-wrapper'>
            <h1>Quick Reminder!</h1>
              <div className="user-reminder">
                <div className="next-scheduled-donation">
                  <h3>Next Scheduled Donation</h3>
                  <div className="reminder-info">
                    <div className="icon-info-wrapper">
                      <MdOutlineDateRange/>
                      <span>Scheduled Date: <span style={{fontWeight:"Bold"}}>December 24, 2024</span></span>
                    </div>
                    <div className="icon-info-wrapper">
                      <IoLocationOutline/>
                      <span>Venue: <span style={{fontWeight:"Bold"}}>BPKHIS Dharan</span></span>
                    </div>
                    <div className="icon-info-wrapper">
                      <IoMdTime/>
                      <span>Time: <span style={{fontWeight:"Bold"}}>12:00pm-3:00pm</span></span>
                    </div>
                  </div>
                </div>
                <div className="nearest-interested-event">
                  <h3>Upcoming Event</h3>
                  <div className="reminder-info">
                    <div className="icon-info-wrapper">
                      <MdOutlineDateRange/>
                      <span>Event Date: <span style={{fontWeight:"Bold"}}>December 24, 2024</span></span>
                    </div>
                    <div className="icon-info-wrapper">
                      <IoLocationOutline/>
                      <span>Venue: <span style={{fontWeight:"Bold"}}>BPKHIS Dharan</span></span>
                    </div>
                    <div className="icon-info-wrapper">
                      <IoMdOptions/>
                      <span>For: <span style={{fontWeight:"Bold"}}>Donating/Volunteering</span></span>
                    </div>
                  </div>
                </div>
            </div>
          </section>
          
          <section className='urgent-blood'>
            <div className="h1-urgent">
              <h1>Urgent Blood Needed!</h1>
            </div>
            <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "40%" }} /> 
                <col style={{ width: "17%" }} />
                <col style={{ width: "23%" }} /> 
              </colgroup>
              <thead>
                <tr>
                  <th>Requested on</th>
                  <th>Location</th>
                  <th style={{textAlign:"center"}}>Blood Type</th>
                  <th style={{textAlign:"center"}}>Wants to donate?</th>
                </tr>
              </thead>
              <tbody>
                {urgentBlood.map((urgentB, index) => (
                  <tr key={index}>
                    <td>{format(urgentB.date, "MMMM dd, yyyy")}</td> {/* Format date */}
                    <td  className='table-data'>{urgentB.location}</td>
                    <td style={{textAlign: 'center'}}>{urgentB.bloodType}</td>
                    <td style={{textAlign:"center"}}><button className="notify-button" onClick={() => navigate("/")}>Notify</button></td>
                    
                  </tr>
                ))}
              </tbody>
            </table>
          </section>
        </div>
      </div>
      <div className="user-dashboard-bottom-section">
        <section className='donation-events'>
            <div className="h1-events">
              <h1>Upcoming Blood Donation Events</h1>
            </div>
            <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
              <colgroup>
                <col style={{ width: "15%" }} /> 
                <col style={{ width: "27%" }} /> 
                <col style={{ width: "20%" }} /> 
                <col style={{ width: "20%" }} /> 
                <col style={{ width: "17%" }} /> 
              </colgroup>
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Event</th>
                  <th>Location</th>
                  <th style={{textAlign:"center"}}>Wants to volunteering?</th>
                  <th style={{textAlign:"center"}}>Wants to donate?</th>
                </tr>
              </thead>
              <tbody>
                {events.map((event, index) => (
                  <tr key={index}>
                    <td>{format(event.date, "MMMM dd, yyyy")}</td> {/* Format date */}
                    <td className='table-data'>{event.title}</td>
                    <td className='table-data'>{event.location}</td>
                    <td style={{textAlign:"center"}}><button className="notify-button" onClick={() => navigate("/")}>Notify</button></td>
                    <td style={{textAlign:"center"}}><button className="notify-button" onClick={() => navigate("/")}>Notify</button></td>
                    
                  </tr>
                ))}
              </tbody>
            </table>
          </section>
      </div>
    </div>
  )
}

export default UserDashboardHome