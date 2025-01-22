import React,{useState, useEffect} from 'react';
import './LoginRegistration.css';

import { Link } from "react-router-dom";

import Select from 'react-select';
import customStyles from './ReactSelectStyle';

import { FaHospitalUser, FaLock, FaFile, FaBorderNone} from "react-icons/fa";
import { IoMdMail } from "react-icons/io";
import {RiContactsBook3Fill} from "react-icons/ri";
import { FaLocationDot } from "react-icons/fa6";
import { IoIosArrowDropdownCircle } from "react-icons/io";

import BackThreeD from './3d'

import NavigationBar from '../Common/NavigationBar'
import { NavbarRightLeft } from '../Common/CommonNavBarComponent'
import {provinceList, ProvinceDistrictList } from './DropDownList';



const RegistrationOrg = () => {
    const [activateAccountModal, setActivateAccountModal] = useState(false);
    
    const [orgType, setOrgType] = useState("hospital")
    const handleChangeOrgType = (event) => {
        setOrgType(event.target.value);
        console.log(event.target.value)
      };
    const [name, setName] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [confirmPassword, setConfirmPassword] = useState("")
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
    const [localAddress, setLocalAddress] = useState("")
    const [isTermsChecked, setTerms] = useState(false)
    const handleTermsCheckboxChange = (event) => {
        setTerms(event.target.checked);
      };


    const [error, setError] = useState('* All fields must be filled.');
    useEffect(() => {
        if (error) {
            console.log(error);
        }
    }, [error]);

    const handleSignup = async (e) => {
        e.preventDefault();
        if (!name) {
        setError('Organization name is required.');
        return;
        }
        if (!email) {
            setError('Email is required.');
            return;
        }
        if (!password) {
            setError('Password is required.');
            return;
        }
        if (!confirmPassword) {
            setError('Confirm password is required.');
            return;
        }
        if (!contact) {
            setError('Contact number is required.');
            return;
        }
        if (!selectedProvince) {
            setError('Province is required.');
            return;
        }
        if (!selectedDistrict) {
            setError('District is required.');
            return;
        }
        if (!city) {
            setError('City/Village is required.');
            return;
        }
        if (!localAddress) {
            setError('Local address is required.');
            return;
        }
        if (!isTermsChecked) {
            setError('You must accept the terms and conditions.');
            return;
        }


        if (password !== confirmPassword) {
            setError('Passwords do not match.');
            return;
        }

        const signupData = {
            name: name,
            email: email,
            password: password,
            phone_number: contact,
            org_type: orgType,
            province: selectedProvince.label,
            district: selectedDistrict.label,
            city: city,
            local_address: localAddress,
        };

        try {
            const response = await fetch('http://172.16.12.229:8000/api/organization/register/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(signupData),
            });
            console.log(JSON.stringify(signupData))

            if (!response.ok) {
                const errorResponse = await response.json();
                console.log('Signup failed:', errorResponse);
                throw new Error(errorResponse.message || 'Signup failed!');
            }
            if(response.ok){
                setActivateAccountModal(true);
                setName("")
                setEmail("")
                setPassword("")
                setConfirmPassword("")
                setContact("")
                setSelectedProvince("")
                setSelectedDistrict("")
                setCity("")
                setLocalAddress("")
                setTerms(false)
            }

            const data = await response.json();
            console.log('Signup successful:', data);
        } catch (err) {
            console.error(err.message);
            setError(err.message || 'An error occurred during signup.');
        }
    };
    

  return (

    
    
    <div className="wrapper">
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<NavbarRightLeft/>}
          rightRightNav = {null} 
        />
        <div className="background">
            <BackThreeD/>
        </div>
        {activateAccountModal && (
            <div className="modal">
                <div className="modal-content">
                    <div className="close-button">
                        <button onClick={() => setActivateAccountModal(false)}>X</button>
                    </div>
                    <div className="activate-button">
                    <button onClick={() => window.open('https://mail.google.com/mail/u/0/#inbox', '_blank')}>
                        Activate your Account
                    </button>
                    </div>
                </div>
            </div>
        )}
        <div className="form-box-registration registration">
            <div className="registration-form">
                <form>
                    <h1>Register your organization</h1>
                    <span style={{fontSize:"14px"}}>Fill up the form carefully to register Hospital/Blood Bank.</span>
                    <br/>
                    <span style={{fontSize:"12px",marginLeft:"20px",marginTop:"22px"}}>{error}</span>

                    <div className="organization-type"  style={{marginTop:"2px"}}>
                        <label>
                            Organization Type:

                            <input type="radio"
                             name="org-type"
                             value="hospital"
                             checked={orgType === "hospital"}
                             onChange={handleChangeOrgType} 
                            />
                            <label htmlFor="hospital" className='hospital'>Hospital</label>

                            <input type="radio"
                             name="org-type"
                             value="blood-bank"
                             checked={orgType === "blood-bank"}
                             onChange={handleChangeOrgType}
                            />
                            <label htmlFor="blood-bank">Blood Bank</label>

                        </label>
                    </div>

                    <div className="input-box" style={{marginTop:"15px"}}>
                        <input type="text"
                        value = {name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder='Name of organization'/>
                        <FaHospitalUser className="icon"/>
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
                    <div className="input-box">
                        <input type="text"
                        value = {contact}
                        onChange={(e) => setContact(e.target.value)}
                        placeholder='Contact no'/>
                        <RiContactsBook3Fill className="icon"/>
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
                        value = {localAddress}
                        onChange={(e) => setLocalAddress(e.target.value)}
                        placeholder='Local Address'/>
                        <FaLocationDot className="icon"/>
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
                        <button type="submit" onClick={handleSignup}>Sign Up</button>
                    </div>

                    <div className="login-link">
                        <p> Already have an account?
                            <Link to = {'/login'}></Link>
                        </p>
                    </div>

                   
                </form>
            </div>
        </div>
    </div>
  )
}

export default RegistrationOrg