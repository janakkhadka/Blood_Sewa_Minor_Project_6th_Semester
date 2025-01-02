import React,{useState} from 'react'

import './BloodAvailability.css'

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import {provinceList, provinceHospitalList, hospitalBloodDataList} from '../LoginRegistration/DropDownList';

import { IoIosArrowDropdownCircle } from "react-icons/io";

import BackThreeD from '../LoginRegistration/3d'
import UserNavigationBar from './UserNavigationBar'
import MyBarChart from '../Utils/MyBarChart';


function BloodAvailability() {
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

    const [hospitalBloodDataOptions, setHospitalBloodDataOptions] = useState([]); //hospital blood data ko lagi, hospital choose garesi tesko data halna lai

    const [selectedHospital, setSelectedHospital] = useState("")
    const handleHospitalChange = (selectedOption) => {
        setSelectedHospital(selectedOption);
        // setSelectedHospital(null);
    
        const selectedHospitalData = hospitalBloodDataList.find(
          (hospital) => hospital.name === selectedOption.label
        );
    
        const updatedHospitalBloodDataOptions = selectedHospitalData
          ? selectedHospitalData.bloodType
          : [];
    
        setHospitalBloodDataOptions(updatedHospitalBloodDataOptions); // Update the district options
      };

    


  return (
    <div className='blood-availability-wrapper'>
        <div className="syringe">
            <BackThreeD/>
        </div>
        <UserNavigationBar titleBar="Blood Availability"/>
        <div className="blood-availability">
            <form action="">
                <h1>See Blood Availability</h1>
                <span>Please choose the Hospital to see Blood Availability.</span>

                <div className="drop-down-box" style={{marginTop:"18px"}}>
                    <Select
                        value = {selectedProvince}
                        onChange={handleProvinceChange}
                        options={provinceList}
                        styles={customStyles()}
                        placeholder="Koshi Province"
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
                        placeholder={
                        selectedProvince ? "Select a Hospital/Blood Bank" : "BPKIHS Dharan"
                        }
                        isDisabled={!selectedProvince}
                        isSearchable={false}
                    />
                    <IoIosArrowDropdownCircle className='icon'/>
                </div>
            </form>
            <div className="bar-chart">
                <MyBarChart barList ={hospitalBloodDataOptions}/>
            </div>
            
        </div>
        
    </div>
  )
}

export default BloodAvailability