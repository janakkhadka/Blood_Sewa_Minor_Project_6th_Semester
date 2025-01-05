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
        <div className="top-section">
          <h1>Live Event</h1>
          <section className='event-details'>
            <h3>Event Details</h3>
            <div className="icon-info-wrapper">
              <MdEvent/>
              <span>Event: <span>Bir Hospital Donation Event</span></span>
            </div>
            <div className="icon-info-wrapper">
              <MdDateRange/>
              <span>Date: <span>January 15, 2025</span></span>
            </div>
            <div className="icon-info-wrapper">
              <IoMdTime/>
              <span>Time: <span>09:00 AM - 05:00 PM</span></span>
            </div>
            <div className="icon-info-wrapper">
              <IoLocationOutline/>
              <span>Location: <span>Balkumari, Lalitpur</span></span>
            </div>
            <div className="icon-info-wrapper">
              <MdOutlineCountertops/>
              <span>Expected Donor Count: <span>35</span></span>
            </div>
          </section>
        </div>
      </div>
    </div>
  )
}

export default TodaysEvent