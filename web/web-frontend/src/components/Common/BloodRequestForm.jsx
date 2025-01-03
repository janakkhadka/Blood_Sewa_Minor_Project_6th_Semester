import React,{useState} from 'react'

import './BloodRequestForm.css'

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import 'react-calendar/dist/Calendar.css';
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import '../LoginRegistration/Calender.css';

import {provinceList, ProvinceDistrictList, bloodGroupList} from '../LoginRegistration/DropDownList';

import { useLocation } from "react-router-dom";

import { IoIosArrowDropdownCircle } from "react-icons/io";
import { TbTimelineEventPlus } from "react-icons/tb";
import {FaLocationDot} from "react-icons/fa6";
import {RiContactsBook3Fill} from "react-icons/ri";

import BackThreeD from '../LoginRegistration/3d'

function BloodRequestForm() {
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
  return (
    <div className='blood-request-wrapper'>
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
                    <button type="submit" >Request Blood</button>
                </div>
                
            </form>
        </div>
    </div>
  )
}

export default BloodRequestForm