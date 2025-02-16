import React,{useState, useEffect} from 'react'

import './UserOrganizeEvent.css'
import { useNavigate } from "react-router-dom";

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import 'react-calendar/dist/Calendar.css';
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import '../LoginRegistration/Calender.css';

import { IoIosArrowDropdownCircle } from "react-icons/io";
import { MdDateRange,MdVolunteerActivism  } from "react-icons/md";
import { IoTime } from "react-icons/io5";
import { TbTimelineEventPlus } from "react-icons/tb";
import {FaLocationDot} from "react-icons/fa6";
import { GoNote } from "react-icons/go";

import BackThreeD from '../LoginRegistration/3d'
import UserNavigationBar from './UserNavigationBar'
import NavigationBar from '../Common/NavigationBar'
import { UserComponentNavbarRightLeft, UserComponentNavbarRightRight } from './UserNavbarComponent';

import {api} from '../../Logic/api'
import { useUserAuthToken } from '../../Logic/AuthKey';

function UserOrganizeEvent() {
    const userAuthToken = useUserAuthToken();
    const navigate = useNavigate();

    const [hospitalOptions, setHospitalOptions] = useState([]);
    const [selectedHospital, setSelectedHospital] = useState("")
    const handleHospitalChange = (selectedOption) => {
        setSelectedHospital(selectedOption);
        console.log(selectedHospital)
        };
    const [loading, setLoading] = useState(true); // Loading state
    const [error, setError] = useState(null);

    const [eventName, setEventName] = useState("")
    const [selectedOrganization, setSelectedOrganization] = useState("")
    const handleOrganizationChange = (option) => {
        setSelectedOrganization(option);
    };
    const [scheduleDate, setScheduleDate] = useState("")
    const [startTime, setStartTime] = useState("")
    const [endTime, setEndTime] = useState("")
    const [venue, setVenue] = useState("")
    const [volunteer, setVolunteer] = useState("no")
    const handleChangeVolunteer = (event) => {
        setVolunteer(event.target.value);
      };
    const [volunteerNumber, setVolunteerNumber] = useState("")
    const [description, setDescription] = useState("")

      //fetching available hospitals/blood banks
      useEffect(() => {
        if(!userAuthToken){
          setError('No auth token found. Please log in');
          setLoading(false);
          return;
        }
    
        const fetchData = async () => {
          try {
            const response = await fetch(api+'organization-list/',{
              method: 'GET',
              headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${userAuthToken}`,
              },
            });
    
            if (!response.ok) {
              const errorResponse = await response.json();
              console.log(errorResponse);
            throw new Error(`Error: ${response.status}`);
            }
    
            const result = await response.json();
            //console.log(result)
            setHospitalOptions(
              Array.isArray(result.organization) 
                  ? result.organization.map(org => ({
                      value: org,
                      label: org
                    }))
                  : []
          );
    
          }catch (err) {
            setError(err.message);
          } finally {
            setLoading(false);
          }
        };
        fetchData();
      },[userAuthToken]);
    
          //create event garda ko lagi
          const handleCreateEvent = async (e) => {
              e.preventDefault();
              if (!eventName || !description || !venue || !scheduleDate || !startTime || !endTime || !selectedHospital) {
                  alert('Please fill in all required fields.');
                  return;
              } 
      
              const createEventData = {
                  "name":eventName,
                  "description":description,
                  "location":venue,
                  "date":scheduleDate,
                  "start_time":startTime,
                  "end_time":endTime,
                  "volunteer_required_count": volunteerNumber? volunteerNumber: 0,
                  "collabrator": selectedHospital.value
              }
              console.log(JSON.stringify(createEventData))
      
              try {
                  // server maa data pathauna lai update garda
                  const response = await fetch(api + 'user/event/create/', {
                      method: 'POST',
                      headers: {
                          'Content-Type': 'application/json',
                          Authorization: `Bearer ${userAuthToken}`,
                      },
                      body: JSON.stringify(createEventData),
                  });;
                  const responseData = await response.json();
      
                  if (response.ok) {
                       alert('Event created successfully!');
                       navigate('/user-dashboard');
                      console.log('Server Response:', responseData);
                  } else {
                      console.log(response)
                      console.error(responseData);
                      // alert(responseData);
                  }
              } catch (error) {
                  console.error('Catch:', error);
                  // alert('catch: ' + error);
              }
          };

    // Sample venue list
  return (
    <div className='user-organize-event-wrapper'>
        <div className="syringe">
            <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<UserComponentNavbarRightLeft/>}
          rightRightNav = {<UserComponentNavbarRightRight/>} 
        />
        <div className="user-organize-event">
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

                <div className="drop-down-box">
                        <Select
                        value = {selectedHospital}
                        onChange={handleHospitalChange}
                        options={hospitalOptions}
                        styles={customStyles()}
                        placeholder="Collaboration with Organization"
                        isSearchable={false}
                        />
                        <IoIosArrowDropdownCircle className='icon'/>
                </div>

                <div className='date-box'>
                        <label htmlFor="" className='date-label'>Event Date:</label>
                        <DatePicker 
                        onChange={(date) => {
                            const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
                            setScheduleDate(formattedDate);
                          }}
                        value={scheduleDate}  className='date-picker'
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
                <div className="input-box">
                    <input type="text"
                    value = {description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder='Description'/>
                    <GoNote className="icon"/>
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
                
                
                <div className="user-organize-event-submit-button">
                    <button type="submit" onClick={handleCreateEvent} >Create Event</button>
                </div>
                
            </form>
        </div>
    </div>
  )
}

export default UserOrganizeEvent