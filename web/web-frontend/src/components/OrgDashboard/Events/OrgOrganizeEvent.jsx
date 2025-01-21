import React,{useState} from 'react'

import './OrgOrganizeEvent.css'
import '../../Common/Variables.css'

import Select from 'react-select';
import customStyles from '../../LoginRegistration/ReactSelectStyle';

import 'react-calendar/dist/Calendar.css';
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import '../../LoginRegistration/Calender.css';

import {venueList} from '../../LoginRegistration/DropDownList';

import { IoIosArrowDropdownCircle } from "react-icons/io";
import { MdDateRange,MdVolunteerActivism  } from "react-icons/md";
import { IoTime } from "react-icons/io5";
import { TbTimelineEventPlus } from "react-icons/tb";
import {FaLocationDot} from "react-icons/fa6";

import BackThreeD from '../../LoginRegistration/3d'
import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent';

function OrgOrganizeEvent() {
    const [eventName, setEventName] = useState("")
  
    const [scheduleDate, setScheduleDate] = useState("")
    const [startTime, setStartTime] = useState("")
    const [endTime, setEndTime] = useState("")
    const [venue, setVenue] = useState("")
    const [volunteer, setVolunteer] = useState("no")
    const handleChangeVolunteer = (event) => {
        setVolunteer(event.target.value);
      };
    const [volunteerNumber, setVolunteerNumber] = useState("")
  return (
    <div className='org-organize-event-wrapper'>
        <div className="syringe">
            <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgComponentNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />
        <div className="org-organize-event">
            <form action="">
                <h1>Create an Event</h1>
                <span>Please fill out the details.</span>

                <div className="input-box">
                        <input type="text"
                        value = {eventName}
                        onChange={(e) => setEventName(e.target.value)}
                        placeholder='Event Name'/>
                        <TbTimelineEventPlus className="icon"/>
                    </div>

                <div className='date-box'>
                        <label htmlFor="" className='date-label'>Event Date:</label>
                        <DatePicker onChange={setScheduleDate} value={scheduleDate}  className='date-picker'
                        placeholderText="Preferred Donation Date"
                        calendarIcon={null} 
                        clearIcon={null}/>

                        <MdDateRange className='icon'/>
                </div>
                
                <div className="time-box">
                  <label htmlFor="" className='time-label'>Start Time:</label>
                        <input type="time" className='time-picker'
                        value = {startTime}
                        onChange={(e) => setStartTime(e.target.value)}
                        placeholder=''/>
                        <IoTime className="icon"/>
                </div>

                <div className="time-box">
                  <label htmlFor="" className='time-label'>End Time:</label>
                        <input type="time" className='time-picker'
                        value = {endTime}
                        onChange={(e) => setEndTime(e.target.value)}
                        placeholder=''/>
                        <IoTime className="icon"/>
                </div>

                <div className="input-box" style={{marginTop:"-5px"}}>
                        <input type="text"
                        value = {venue}
                        onChange={(e) => setVenue(e.target.value)}
                        placeholder='Venue'/>
                        <FaLocationDot className="icon"/>
                </div>

                <div className="volunteer-wrapper">
                    <div className="volunteer">
                        <label>
                            Volunteer Needed?
                            <input type="radio"
                             name="volunteer"
                             value="yes"
                             checked={volunteer === "yes"}
                             onChange={handleChangeVolunteer}
                            />
                            <label htmlFor="yes" className='yes'>Yes</label>

                            <input type="radio"
                             name="volunteer"
                             value="no"
                             checked={volunteer === "no"}
                             onChange={handleChangeVolunteer} 
                            />
                            <label htmlFor="no" className='no'>No</label>
                        </label>
                    </div>
                    {volunteer ==='yes' && (
                        <div className="input-box">
                        <input type="text"
                        value = {volunteerNumber}
                        onChange={(e) => setVolunteerNumber(e.target.value)}
                        placeholder='Enter required Volunteer Number'/>
                        <MdVolunteerActivism className="icon"/>
                    </div>
                    )}
                    
                </div>
                
                
                <div className="org-organize-event-submit-button">
                    <button type="submit" >Create Event</button>
                </div>
                
            </form>
        </div>
    </div>
  )
}

export default OrgOrganizeEvent