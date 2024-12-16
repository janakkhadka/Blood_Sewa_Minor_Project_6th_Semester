import React,{useState} from 'react';
import './LoginRegistration.css';

import { FaHospitalUser, FaLock, FaFile, FaBorderNone} from "react-icons/fa";
import { IoMdMail } from "react-icons/io";
import {RiContactsBook3Fill} from "react-icons/ri";
import { FaLocationDot } from "react-icons/fa6";

import BackThreeD from './3d'



const Registration = ({switchToLogin}) => {
    
    const [orgType, setOrgType] = useState("hospital")
    const handleChangeOrgType = (event) => {
        setOrgType(event.target.value);
      };
    const [name, setName] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [confirmPassword, setConfirmPassword] = useState("")
    const [contact, setContact] = useState("")
    const [location, setLocation] = useState("")
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
        <div className="form-box-registration registration">
            <div className="registration-form">
                <form onSubmit={handleSubmit}>
                    <h1>Register your organization</h1>

                    <div className="organization-type">
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

                    <div className="input-box">
                        <input type="text"
                        value = {name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder='Name of organization' require/>
                        <FaHospitalUser className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="email"
                        value = {email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder='Email' require/>
                        <IoMdMail className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="password"
                        value = {password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder='Password' require/>
                        <FaLock className="icon"/>
                    </div>

                    <div className="input-box">
                        <input type="password"
                        value = {confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        placeholder='Confirm password' require/>
                        <FaLock className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="text"
                        value = {contact}
                        onChange={(e) => setContact(e.target.value)}
                        placeholder='Contact no' require/>
                        <RiContactsBook3Fill className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="text"
                        value = {location}
                        onChange={(e) => setLocation(e.target.value)}
                        placeholder='Location' require/>
                        <FaLocationDot className="icon"/>
                    </div>
                    <div className="input-document">
                        <label htmlFor="" className='document'>
                            Upload document:
                            <input type="file" require/>
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

                   
                </form>
            </div>
        </div>
    </div>
  )
}

export default Registration