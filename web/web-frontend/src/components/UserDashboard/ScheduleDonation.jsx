import React,{useState} from 'react'

import './ScheduleDonation.css'

import 'react-calendar/dist/Calendar.css';
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import '../LoginRegistration/Calender.css';

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import {provinceList, provinceHospitalList, timeShiftOptions} from '../LoginRegistration/DropDownList';

import { IoIosArrowDropdownCircle } from "react-icons/io";
import { MdDateRange } from "react-icons/md";
import { IoTime } from "react-icons/io5";

import BackThreeD from '../LoginRegistration/3d'
import UserNavigationBar from './UserNavigationBar'


function ScheduleDonation() {
    const [hospitalOptions, setHospitalOptions] = useState([]);//list of hospital accordance to province hai
    const [selectedProvince, setSelectedProvince] = useState('')
    const handleProvinceChange = (selectedOption) => {
        setSelectedProvince(selectedOption);
        setSelectedHospital(null);
    
        const selectedProvinceData = provinceHospitalList.find(
          (province) => province.label === selectedOption.label
        );
    
        const updatedHospitalOptions = selectedProvinceData
          ? selectedProvinceData.options
          : [];
    
        setHospitalOptions(updatedHospitalOptions); // Update the district options
      };


    const [selectedHospital, setSelectedHospital] = useState("")
    const handleHospitalChange = (selectedOption) => {
        setSelectedHospital(selectedOption);
      };
    const [scheduleDate, setScheduleDate] = useState("")
    const [timeShift, setTimeShift] = useState("")
    const handleTimeShiftChange = (selectedOption) => {
      setTimeShift(selectedOption)
    }

    


  return (
    <div className='schedule-donation-wrapper'>
        <div className="syringe">
            <BackThreeD/>
        </div>
        <UserNavigationBar titleBar="Blood Availability"/>
        <div className="schedule-donation">
            <form action="">
                <h1>Schedule Donation Form</h1>
                <span>Please choose the Hospital to see Blood Availability.</span>

                <div className="drop-down-box" style={{marginTop:"18px"}}>
                    <Select
                        value = {selectedProvince}
                        onChange={handleProvinceChange}
                        options={provinceList}
                        styles={customStyles()}
                        placeholder="Select Province"
                        isSearchable={false}
                    />
                    <IoIosArrowDropdownCircle className='icon'/>
                </div>

                <div className="drop-down-box">
                    <Select
                        value = {selectedHospital}
                        onChange={handleHospitalChange}
                        options={hospitalOptions}
                        styles={customStyles()}
                        placeholder={"Select a Hospital/Blood Bank"}
                        isDisabled={!selectedProvince}
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
                    <button type="submit" >Schedule</button>
                </div>
            </form>
        </div>
        
    </div>
  )
}

export default ScheduleDonation