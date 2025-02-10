import React,{useState} from 'react'

import './BloodRequestForm.css'

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import 'react-calendar/dist/Calendar.css';
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import '../LoginRegistration/Calender.css';
import NavigationBar from '../Common/NavigationBar';
import {NavbarRightRight } from '../Common/CommonNavBarComponent'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgDashboard/OrgNavbarComponent';
import { UserComponentNavbarRightLeft, UserComponentNavbarRightRight } from './UserNavbarComponent';

import {provinceList, ProvinceDistrictList, bloodGroupList} from '../LoginRegistration/DropDownList';

import { useLocation } from "react-router-dom";

import { IoIosArrowDropdownCircle } from "react-icons/io";
import { TbTimelineEventPlus } from "react-icons/tb";
import {FaLocationDot} from "react-icons/fa6";
import {RiContactsBook3Fill} from "react-icons/ri";

import BackThreeD from '../LoginRegistration/3d'

import {api} from '../../Logic/api'
import { useUserAuthToken } from '../../Logic/AuthKey';

function BloodRequestForm() {
    const userAuthToken = useUserAuthToken();

    const [data, setData] = useState(null); // State to store fetched data
    const [loading, setLoading] = useState(true); // Loading state
    const [error, setError] = useState(null);

    const locationDom = useLocation();
    const { identifier } = locationDom.state || { identifier: 0 }; //kei aayena vane by defaylt 0 hunxa
    const [patinetName, setPatientName] = useState("")
    const [bloodType, setBloodType] = useState("")
    const handleBloodTypeChange = (option) => {
      setBloodType(option);
    }
    const [contact, setContact] = useState("")
    const [districtOptions, setDistrictOptions] = useState([]);
    const [selectedProvince, setSelectedProvince] = useState('')
    const handleProvinceChange = (selectedOption) => {
        setSelectedProvince(selectedOption);
        setSelectedDistrict(null);
    
        const selectedProvinceData = ProvinceDistrictList.find(
          (province) => province.label === selectedOption.label
        );
    
        const updatedDistrictOptions = selectedProvinceData
          ? selectedProvinceData.options
          : [];
    
        setDistrictOptions(updatedDistrictOptions); // Update the district options
      };

      const [selectedDistrict, setSelectedDistrict] = useState("")
      const handleDistrictChange = (option) => {
          setSelectedDistrict(option);
      };
      const [city, setCity] = useState("")
      const [location, setLocation] = useState("")



      //submit garda schedule garko lagi
    const handleSubmit = async (e) => {
      e.preventDefault();
      if (!patinetName) {
      setError('select hospital.');
      return;
      }
      if (!selectedProvince.value) {
          setError('select shift');
          return;
      }
      if (!selectedDistrict.value) {
          setError('District is required.');
          return;
      }

      if (!location) {
        setError('Local Address is required.');
        return;
      }
      if (!contact) {
        setError('Contact Number is required.');
        return;
      }
      if (!city) {
        setError('City is required.');
        return;
      }
    
      const schedulingData = {
        patient_name: patinetName,
        contact: contact,
        blood_group: bloodType.value,
        location: location,
      };

      try {
          const response = await fetch(api+'create/blood-request/', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
                  //Authorization: `Bearer ${userAuthToken}`,
              },
              body: JSON.stringify(schedulingData),
          });
          console.log(JSON.stringify(schedulingData))

          if (!response.ok) {
              const errorResponse = await response.json();
              console.log('Request failed:', errorResponse);
              throw new Error(errorResponse.message || 'Request failed!');
          }
          if(response.ok){
              //setActivateAccountModal(true);
              setPatientName("")
              setBloodType("")
              setContact("")
              setSelectedDistrict("")
              setSelectedProvince("")
              setCity("")
              setLocation("")
          }

          const data = await response.json();
          console.log('Request done:', data);
      } catch (err) {
          console.error(err.message);
          setError(err.message || 'An error occurred during requesting.');
      }
  };
  
  return (
    <div className='blood-request-wrapper'>
        {/* user login navako bela */}
        {identifier == 0 && (  
            <NavigationBar 
            titleNav = "Blood Sewa" 
            rightLeftNav = {null}
            rightRightNav = {<NavbarRightRight/>} 
          />
        )}
        {/* user login gareko bela */}
        {identifier == 1 && (  
            <NavigationBar 
            titleNav = "Blood Sewa" 
            rightLeftNav = {<UserComponentNavbarRightLeft/>}
            rightRightNav = {<UserComponentNavbarRightRight/>} 
          />
        )}
        {/* orgle login gareko bela */}
        {identifier == 2 && (  
            <NavigationBar 
            titleNav = "Blood Sewa" 
            rightLeftNav = {<OrgComponentNavbarRightLeft/>}
            rightRightNav = {<OrgDashboardNavbarRightRight/>} 
          />
        )}


        <div className="syringe">
            <BackThreeD/>
        </div>
        
        <div className="blood-request">
            <form action="">
                <h1>Blood Request Form</h1>
                <span>Please fill out the details.</span>

                <div className="input-box">
                        <input type="text"
                        value = {patinetName}
                        onChange={(e) => setPatientName(e.target.value)}
                        placeholder='Patient Name'/>
                        <TbTimelineEventPlus className="icon"/>
                </div>
                <div className="drop-down-box">
                <Select
                  value = {bloodType}
                  onChange={handleBloodTypeChange}
                  options={bloodGroupList}
                  styles={customStyles()}
                  placeholder="Select Blood Group"
                  isSearchable={false}
                />
                <IoIosArrowDropdownCircle className='icon'/>
              </div>
                <div className="drop-down-box">
                        <Select
                            value = {selectedProvince}
                            onChange={handleProvinceChange}
                            options={provinceList}
                            styles={customStyles()}
                            placeholder="Province"
                            isSearchable={false}
                        />
                        <IoIosArrowDropdownCircle className='icon'/>
                    </div>

                    <div className="drop-down-box">
                        <Select
                            value = {selectedDistrict}
                            onChange={handleDistrictChange}
                            options={districtOptions}
                            styles={customStyles()}
                            placeholder={
                            selectedProvince ? "Select a District" : "Select a Province first"
                            }
                            isDisabled={!selectedProvince}
                            isSearchable={false}
                        />
                        <IoIosArrowDropdownCircle className='icon'/>
                    </div>
                    <div className="input-box">
                        <input type="text"
                        value = {city}
                        onChange={(e) => setCity(e.target.value)}
                        placeholder='City/Village'/>
                        <FaLocationDot className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="text"
                        value = {location}
                        onChange={(e) => setLocation(e.target.value)}
                        placeholder='Local Address'/>
                        <FaLocationDot className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="text"
                        value = {contact}
                        onChange={(e) => setContact(e.target.value)}
                        placeholder='Contact no'/>
                        <RiContactsBook3Fill className="icon"/>
                    </div>
                

                
                
                <div className="blood-request-submit-button">
                    <button type="submit" onClick={handleSubmit} >Request Blood</button>
                </div>
                
            </form>
        </div>
    </div>
  )
}

export default BloodRequestForm