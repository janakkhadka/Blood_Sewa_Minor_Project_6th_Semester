import React,{useState} from 'react';
import './UserProfileUpdate.css';
import { useNavigate } from 'react-router-dom';

import 'react-calendar/dist/Calendar.css';
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import '../LoginRegistration/Calender.css';

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle'; //custom style place gareko ho tyaa React dropdown ko

import {provinceList, bloodGroupList, ProvinceDistrictList } from '../LoginRegistration/DropDownList';


import { FaUser, FaLock, FaFile} from "react-icons/fa";
import { IoMdMail } from "react-icons/io";
import {RiContactsBook3Fill} from "react-icons/ri";
import { IoIosArrowDropdownCircle } from "react-icons/io";
import { MdDateRange } from "react-icons/md";


import BackThreeD from '../LoginRegistration/3d'
import UserNavigationBar from './UserNavigationBar'



const UserProfileUpdate = () => {
    const navigate = useNavigate();


    const [gender, setGender] = useState("male")
    const handleChangeGender = (event) => {
        setGender(event.target.value);
      };
    const [name, setName] = useState("")
    const [selectedBloodGroup, setSelectedBloodGroup] = useState("")
    const handleBloodGroupChange = (option) => {
        setSelectedBloodGroup(option);
    };

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
    const [phone, setPhone] = useState("")
    const [dob, setDob] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [confirmPassword, setConfirmPassword] = useState("")

    const [isTermsChecked, setTerms] = useState(false)
    const handleTermsCheckboxChange = (event) => {
        setTerms(event.target.checked); // Update state with checkbox status
      };

      async function handleSubmit(event){
        try{
            event.preventDefault();
    
        // Data to send to the server
        //const data = { email,password,name,phone,selectedBloodGroup,selectedDistrict,selectedProvince };

        const jsonData = {
            "name": name.toString(),
            "email": email.toString(),
            "password": password.toString(),
            "blood_group": selectedBloodGroup.value,
            "district": selectedDistrict.value,
            "province": selectedProvince.value,
            "phone_number": phone.toString()
        }

        console.log('Name:', name);
        console.log('Email:', email);
        console.log('Password:', password);
        console.log('Blood Group:', selectedBloodGroup.value);
        console.log('District:', selectedDistrict.value);
        console.log('Province:', selectedProvince.value);
        console.log('Phone:', phone);
    
        // Replace with your API URL
        fetch('http://127.0.0.1:8000/api/user/register/', {
          method: 'POST', // or 'PUT' depending on the API action
          headers: {
            'Content-Type': 'application/json', // Sending JSON data
          },
          body: JSON.stringify(jsonData), // Convert JavaScript object to JSON string
        })
          .then(response => response.json()) // Parse the response as JSON
          .then(result => {
            console.log('Success:', result);
          })
          .catch(error => {
            console.error('Error:', error);
          });

        }catch(error){
                console.error("error:", error)
        }
        
      };


  return (
    <div className="user-profile-update-wrapper">
        <div className="syringe">
            <BackThreeD/>
        </div>
        <UserNavigationBar titleBar="Update User Profile"/>
            <div className="user-profile-update">
                <form onSubmit={handleSubmit}>
                    <h1>Update Profile</h1>
                    <div className="input-box">
                        <input type="text"
                        value = {name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder='Full Name'/>
                        <FaUser className="icon"/>
                    </div>

                    <div className='dob-box'>
                        <label htmlFor="" className='dob-label'>Date of Birth:</label>
                        <DatePicker onChange={setDob} value={dob}  className='date-picker'
                        placeholderText="Date of Birth"
                        calendarIcon={null} 
                        clearIcon={null}/>

                        <MdDateRange className='icon'/>
                    </div>

                    <div className="gender">
                        <label>
                            Gender:
                            <input type="radio"
                             name="gender"
                             value="male"
                             checked={gender === "male"}
                             onChange={handleChangeGender} 
                            />
                            <label htmlFor="male" className='male'>Male</label>

                            <input type="radio"
                             name="gender"
                             value="female"
                             checked={gender === "female"}
                             onChange={handleChangeGender}
                            />
                            <label htmlFor="female" className='female'>Female</label>

                            <input type="radio"
                             name="gender"
                             value="others"
                             checked={gender === "others"}
                             onChange={handleChangeGender}
                            />
                            <label htmlFor="others">Others</label>

                        </label>
                    </div>


                    <div className="drop-down-box">
                        <Select
                            value = {selectedBloodGroup}
                            onChange={handleBloodGroupChange}
                            options={bloodGroupList}
                            styles={customStyles}
                            placeholder="Blood Group"
                            isSearchable={false}
                        />
                        <IoIosArrowDropdownCircle className='icon'/>
                    </div>

                    <div className="drop-down-box">
                        <Select
                            value = {selectedProvince}
                            onChange={handleProvinceChange}
                            options={provinceList}
                            styles={customStyles}
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
                            styles={customStyles}
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
                        value = {phone}
                        onChange={(e) => setPhone(e.target.value)}
                        placeholder='Phone no'/>
                        <RiContactsBook3Fill className="icon"/>
                    </div>
                    <div className="button">
                        <button type="submit"  onClick={handleSubmit}>Update</button>
                    </div>
                </form>
            </div>
    </div>
  )
}

export default UserProfileUpdate