import React,{useState} from 'react';
import './LoginRegistration.css';

import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import './Calender.css';

import Select from 'react-select';
import customStyles from './ReactSelectStyle'; //custom style place gareko ho tyaa React dropdown ko

import {provinceList, bloodGroupList, ProvinceDistrictList } from './DropDownList';


import { FaUser, FaLock, FaFile} from "react-icons/fa";
import { IoMdMail } from "react-icons/io";
import {RiContactsBook3Fill} from "react-icons/ri";
import { IoIosArrowDropdownCircle } from "react-icons/io";
import { MdDateRange } from "react-icons/md";

import BackThreeD from './3d'



const RegistrationUser = ({switchToLogin}) => {


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

    const handleSubmit = (e) => {
        e.preventDefault();
       
      };


  return (
    <div className="wrapper">
        <div className="background">
            <BackThreeD/>
        </div>
        <div className="form-box-user-registration">
            <div className="user-form">
                <form onSubmit={handleSubmit}>
                    <h1>Register</h1>

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

                    <div className="input-box">
                        <input type="email"
                        value = {email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder='Email'/>
                        <IoMdMail className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="password"
                        value = {password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder='Password'/>
                        <FaLock className="icon"/>
                    </div>

                    <div className="input-box">
                        <input type="password"
                        value = {confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        placeholder='Confirm password'/>
                        <FaLock className="icon"/>
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
                    

                    
                    {/* <div className="drop-down-box">
                        <select className='drop-down'
                            value={selectedBloodGroup}
                            onChange={(e) => setSelectedBloodGroup(e.target.value)}
                        >
                            <option value="" disabled className='option' style={{color:'red',background:'transparent'}}>Select your Blood Group</option>
                            {bloodGroupList.map((item, index) => (
                            <option key={index} value={item} className='option'
                            
                            >
                                {item}
                            </option>
                            ))}
                        </select>
                        <IoIosArrowDropdownCircle className='icon'/>
                    </div> */}

                    
                    <div className="input-box">
                        <input type="text"
                        value = {phone}
                        onChange={(e) => setPhone(e.target.value)}
                        placeholder='Phone no'/>
                        <RiContactsBook3Fill className="icon"/>
                    </div>
    
                    <div className="input-document">
                        <label htmlFor="" className='document'>
                            Upload document:
                            <input type="file"/>
                            <FaFile className="icon" />
                        </label>
                        
                    </div>

                    <div className="terms-and-conditions">
                        <label>
                            <input type="checkbox"
                            checked={isTermsChecked}
                            onChange={handleTermsCheckboxChange} />
                            I agree with the Blood Sewa's <a href="#">terms and conditions</a>.
                        </label>
                    </div>

                    <div className="button">
                        <button type="submit" disabled={!isTermsChecked}>Sign Up</button>
                    </div>

                    <div className="login-link">
                        <p> Already have an account?
                            <a href="#" onClick={switchToLogin}>Login</a>
                        </p>
                    </div>

                   
                </form>
            </div>
        </div>
    </div>
  )
}

export default RegistrationUser