import React from 'react'

import './TodaysEvent.css'

import { SlUserFemale, SlUser } from "react-icons/sl";
import { MdOutlineDateRange, MdDateRange,MdCountertops,MdOutlineCountertops} from "react-icons/md";
import { BsGenderAmbiguous } from "react-icons/bs";
import { CiPhone } from "react-icons/ci";
import { IoHomeOutline } from "react-icons/io5";
import { FaLocationDot, FaDiamond } from "react-icons/fa6";
import { IoMdTime } from "react-icons/io";
import { MdEvent } from "react-icons/md";
import { IoLocationOutline } from "react-icons/io5";

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

import {donorList} from '../../UserDashboard/DummyData'

import { format } from "date-fns";

function TodaysEvent() {
  return (
    <div className='todays-event-wrapper'>
      <div className="syringe">
          <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgComponentNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />
      <div className="todays-event">
        <div className="left-section">
          <div className="left-top-section">
            <h1>Ongoing Event</h1>
            <section className='event-details'>
              <h3>Event Details</h3>
              <div className="icon-info-wrapper">
                <MdEvent/>
                <span>Event: <span style={{fontWeight:"bold"}}>Bir Hospital Donation Event</span></span>
              </div>
              <div className="icon-info-wrapper">
                <MdDateRange/>
                <span>Date: <span style={{fontWeight:"bold"}}>January 15, 2025</span></span>
              </div>
              <div className="icon-info-wrapper">
                <IoMdTime/>
                <span>Time: <span style={{fontWeight:"bold"}}>09:00 AM - 05:00 PM</span></span>
              </div>
              <div className="icon-info-wrapper">
                <IoLocationOutline/>
                <span>Location: <span style={{fontWeight:"bold"}}>Balkumari, Lalitpur</span></span>
              </div>
              <div className="icon-info-wrapper">
                <MdOutlineCountertops/>
                <span>Expected Donor Count: <span style={{fontWeight:"bold"}}>35</span></span>
              </div>
            </section>
          </div>
          <div className="left-bottom-section">
            <section className='donor-details'>
              <div className="h1">
                <h1>Donor List</h1>
              </div>
              <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
                <colgroup>
                  <col style={{ width: "20%" }} />
                  <col style={{ width: "40%" }} /> 
                  <col style={{ width: "25%" }} />
                  <col style={{ width: "15%" }} />
                </colgroup>
                <thead>
                  <tr>
                    <th>SN</th>
                    <th>Donor Name</th>
                    <th>Blood Group</th>
                  </tr>
                </thead>
                <tbody>
                  {donorList.map((donor, index) => (
                    <tr key={index}>
                      <td>{donor.sn}</td> {/* Format date */}
                      <td  className='table-data'>{donor.name}</td>
                      <td  className='table-data'>{donor.bloodGroup}</td>
                      <td><button className="notify-button" onClick={() => navigate("/")}>Add</button></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </section>
          </div>
        </div>
        <div className="right-section">
            <section className='scan-qr'>
              <div className="qr-wrapper" style={{width:"800px"}}>
                <img className='qr-image' src="https://www.qrstuff.com/images/default_qrcode.png" alt="qr-code"/>
              </div>
            </section>
          </div>
        
      </div>
    </div>
  )
}

export default TodaysEvent