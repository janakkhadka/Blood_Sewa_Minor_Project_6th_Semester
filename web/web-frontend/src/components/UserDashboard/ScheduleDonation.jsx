import React,{useState, useEffect} from 'react'

import './ScheduleDonation.css'

import 'react-calendar/dist/Calendar.css';
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import '../LoginRegistration/Calender.css';

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import {timeShiftOptions} from '../LoginRegistration/DropDownList';

import { IoIosArrowDropdownCircle } from "react-icons/io";
import { MdDateRange } from "react-icons/md";

import BackThreeD from '../LoginRegistration/3d'
import UserNavigationBar from './UserNavigationBar'
import NavigationBar from '../Common/NavigationBar'
import { UserComponentNavbarRightLeft, UserComponentNavbarRightRight } from './UserNavbarComponent';

import {api} from '../../Logic/api'
import { useUserAuthToken } from '../../Logic/AuthKey';


function ScheduleDonation() {
    const userAuthToken = useUserAuthToken();

    const [data, setData] = useState(null); // State to store fetched data
    const [loading, setLoading] = useState(true); // Loading state
    const [error, setError] = useState(null);

    const [hospitalOptions, setHospitalOptions] = useState([]);//list of hospital accordance to province hai

    const [selectedHospital, setSelectedHospital] = useState("")
    const handleHospitalChange = (selectedOption) => {
        setSelectedHospital(selectedOption);
        console.log(selectedHospital)
        // setSelectedHospital(null);
      };
    const [scheduleDate, setScheduleDate] = useState("")
    const [timeShift, setTimeShift] = useState("")
    const handleTimeShiftChange = (selectedOption) => {
      setTimeShift(selectedOption)
    }

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


  //submit garda schedule garko lagi
    const handleSubmit = async (e) => {
      e.preventDefault();
      if (!selectedHospital.value) {
      setError('select hospital.');
      return;
      }
      if (!timeShift.value) {
          setError('select shift');
          return;
      }
      if (!scheduleDate) {
          setError('Password is required.');
          return;
      }
    
      const schedulingData = {
          organization: selectedHospital.value,
          booking_date: scheduleDate.toISOString().split('T')[0], //formatting date
          shift: timeShift.value,
      };

      try {
          const response = await fetch(api+'make-bookings/', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
                  Authorization: `Bearer ${userAuthToken}`,
              },
              body: JSON.stringify(schedulingData),
          });
          console.log(JSON.stringify(schedulingData))

          if (!response.ok) {
              const errorResponse = await response.json();
              console.log('schedule failed:', errorResponse);
              throw new Error(errorResponse.message || 'Schedule failed!');
          }
          if(response.ok){
              //setActivateAccountModal(true);
              setSelectedHospital("")
              setTimeShift("")
              setScheduleDate("")
          }

          const data = await response.json();
          console.log('scheduled successfully:', data);
      } catch (err) {
          console.error(err.message);
          setError(err.message || 'An error occurred during scheduling.');
      }
  };
  

  return (
    <div className='schedule-donation-wrapper'>
        <div className="syringe">
            <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<UserComponentNavbarRightLeft/>}
          rightRightNav = {<UserComponentNavbarRightRight/>} 
        />
        <div className="schedule-donation">
            <form action="">
                <h1>Schedule Donation Form</h1>
                <span>Please choose the Hospital and your preferred time shift.</span>

                {/* <div className="drop-down-box" style={{marginTop:"18px"}}>
                    <Select
                        value = {selectedProvince}
                        onChange={handleProvinceChange}
                        options={provinceList}
                        styles={customStyles()}
                        placeholder="Select Province"
                        isSearchable={false}
                    />
                    <IoIosArrowDropdownCircle className='icon'/>
                </div> */}

                <div className="drop-down-box">
                    <Select
                        value = {selectedHospital}
                        onChange={handleHospitalChange}
                        options={hospitalOptions}
                        styles={customStyles()}
                        placeholder={"Select a Hospital/Blood Bank"}
                        isSearchable={false}
                    />
                    <IoIosArrowDropdownCircle className='icon'/>
                </div>

                <div className='date-box'>
                        <label htmlFor="" className='date-label'>Preferred Donation Date:</label>
                        <DatePicker onChange={setScheduleDate} value={scheduleDate}  className='date-picker'
                        placeholderText="Preferred Donation Date"
                        calendarIcon={null} 
                        clearIcon={null}/>

                        <MdDateRange className='icon'/>
                </div>
                
                <div className="drop-down-box" style={{marginTop:"18px"}}>
                    <Select
                        value = {timeShift}
                        onChange={handleTimeShiftChange}
                        options={timeShiftOptions}
                        styles={customStyles()}
                        placeholder="Select Time Shift"
                        isSearchable={false}
                    />
                    <IoIosArrowDropdownCircle className='icon'/>
                </div>

                <div className="schedule-donation-submit-button">
                    <button type="submit" onClick={handleSubmit} >Schedule</button>
                </div>
            </form>
        </div>
    </div>
  )
}

export default ScheduleDonation