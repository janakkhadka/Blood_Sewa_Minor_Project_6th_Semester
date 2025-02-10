import React,{useState, useEffect} from 'react'

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
        console.log(result)
        setHospitalOptions(
          Array.isArray(result.organization) 
              ? result.organization.map(org => ({
                  value: org,
                  label: org
                }))
              : []
      );
        console.log(hospitalOptions)

      }catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  },[userAuthToken]);

  //   const handleSubmit = async (e) => {
  //     e.preventDefault();
  //     if (!name) {
  //     setError('Organization name is required.');
  //     return;
  //     }
  //     if (!email) {
  //         setError('Email is required.');
  //         return;
  //     }
  //     if (!password) {
  //         setError('Password is required.');
  //         return;
  //     }
  //     if (!confirmPassword) {
  //         setError('Confirm password is required.');
  //         return;
  //     }
  //     if (!contact) {
  //         setError('Contact number is required.');
  //         return;
  //     }
  //     if (!selectedProvince) {
  //         setError('Province is required.');
  //         return;
  //     }
  //     if (!selectedDistrict) {
  //         setError('District is required.');
  //         return;
  //     }
  //     if (!city) {
  //         setError('City/Village is required.');
  //         return;
  //     }
  //     if (!localAddress) {
  //         setError('Local address is required.');
  //         return;
  //     }
  //     if (!isTermsChecked) {
  //         setError('You must accept the terms and conditions.');
  //         return;
  //     }


  //     if (password !== confirmPassword) {
  //         setError('Passwords do not match.');
  //         return;
  //     }

  //     const signupData = {
  //         name: name,
  //         email: email,
  //         password: password,
  //         phone_number: contact,
  //         org_type: orgType,
  //         province: selectedProvince.label,
  //         district: selectedDistrict.label,
  //         city: city,
  //         local_address: localAddress,
  //     };

  //     try {
  //         const response = await fetch(api+'organization/register/', {
  //             method: 'POST',
  //             headers: {
  //                 'Content-Type': 'application/json',
  //             },
  //             body: JSON.stringify(signupData),
  //         });
  //         console.log(JSON.stringify(signupData))

  //         if (!response.ok) {
  //             const errorResponse = await response.json();
  //             console.log('Signup failed:', errorResponse);
  //             throw new Error(errorResponse.message || 'Signup failed!');
  //         }
  //         if(response.ok){
  //             setActivateAccountModal(true);
  //             setName("")
  //             setEmail("")
  //             setPassword("")
  //             setConfirmPassword("")
  //             setContact("")
  //             setSelectedProvince("")
  //             setSelectedDistrict("")
  //             setCity("")
  //             setLocalAddress("")
  //             setTerms(false)
  //         }

  //         const data = await response.json();
  //         console.log('Signup successful:', data);
  //     } catch (err) {
  //         console.error(err.message);
  //         setError(err.message || 'An error occurred during signup.');
  //     }
  // };
  

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
                    <button type="submit" >Schedule</button>
                </div>
            </form>
        </div>
    </div>
  )
}

export default ScheduleDonation