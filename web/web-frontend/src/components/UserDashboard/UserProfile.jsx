import React from 'react'

import './UserProfile.css'
import BackThreeD from '../LoginRegistration/3d'
import UserNavigationBar from './UserNavigationBar'
import { useNavigate } from 'react-router-dom'

import {activityHistory} from './DummyData'

import { format } from "date-fns";
import { SlUserFemale, SlUser } from "react-icons/sl";
import { MdOutlineDateRange, MdDateRange } from "react-icons/md";
import { BsGenderAmbiguous } from "react-icons/bs";
import { CiPhone } from "react-icons/ci";
import { IoHomeOutline } from "react-icons/io5";
import { MdCountertops } from "react-icons/md";
import { FaLocationDot } from "react-icons/fa6";
import { GiChoice } from "react-icons/gi";

import Medal from '../Utils/Medal'

function UserProfile() {
  const donationCount = 130;
  const navigate = useNavigate();
  return (
    <div className="user-profile-wrapper">
      <div className="syringe">
        <BackThreeD/>
      </div>
      <UserNavigationBar titleBar="User Profile"/>

      <div className="user-profile">
        <div className="top-section">
          <section className='user-basic-info'>
            <h1>User Information</h1>
            <div className="user-icon">
              <SlUser className='icon'/>
              <div className="name-email">
                <h3>Janak Khadka</h3>
                <span>janak.211717@gmail.com</span>
              </div>
            </div>
            <div className="other-info">
              <div className="icon-info-wrapper">
                <MdOutlineDateRange/>
                <span>Date of Birth: July 17, 2001</span>
              </div>
              <div className="icon-info-wrapper">
                <BsGenderAmbiguous/>
                <span>Gender: Male</span>
              </div>
              <div className="icon-info-wrapper">
                <CiPhone/>
                <span>Phone: +977-9840989641</span>
              </div>
              <div className="icon-info-wrapper">
                <IoHomeOutline/>
                <span>Address: Dhankuta, Koshi Province</span>
              </div>
            </div>
            <div className="edit-profile-button">
              <button onClick={() => navigate("/user-profile-update")}>Update Profile</button>
            </div>
          </section>
          <section className='donation-profile'>
            <h1>Donation Profile</h1>
            <span style={{fontSize:"14px"}}>Your donations have made a difference!</span>

            <div className="medal">
              <Medal donationCount={donationCount}/>
            </div>
            <h3>Donation Information</h3>
            <div className="icon-info-wrapper">
                <MdCountertops/>
                <span>Donation Count: <span style={{fontWeight:"Bold"}}>{donationCount}</span></span>
              </div>
              <div className="icon-info-wrapper">
                <MdDateRange/>
                <span>Last Donation Date: <span style={{fontWeight:"Bold"}}>December 24, 2024</span></span>
              </div>
              <div className="icon-info-wrapper">
                <FaLocationDot />
                <span>Last Donation Event: <span style={{fontWeight:"Bold"}}>Red Cross Donation Event</span></span>
              </div>
              <div className="icon-info-wrapper">
                <GiChoice />
                <span>You are eligible to donate blood</span>
              </div>
          </section>
        </div>
        <div className="bottom-section">
          <section className='user-donation-history'>
            
            <h1 className='h1-history'>User Activity History</h1>
            <table border="0" style={{ width: "100%", borderCollapse: "collapse" }}>
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Event</th>
                  <th>Activity</th>
                </tr>
              </thead>
              <tbody>
                {activityHistory.map((activity, index) => (
                  <tr key={index}>
                    <td>{format(activity.date, "MMMM dd, yyyy")}</td> {/* Format date */}
                    <td>{activity.event}</td>
                    <td>{activity.activity}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </section>
        </div>
      </div>
    </div>
  )
}

export default UserProfile
