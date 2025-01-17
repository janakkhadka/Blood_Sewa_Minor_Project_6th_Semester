import React,{useState} from 'react';
import './ScreeningResultModal.css';

import { Link } from "react-router-dom";


import { FaUser, FaLock, FaFile} from "react-icons/fa";

import BackThreeD from './3d'



const ScreeningResultModal = () => {

    const [bloodPressure, setBloodPressure] = useState("")
    const [pulseRate, setPulseRate] = useState("")
    const [temperature, setTemperature] = useState("")
    const [hemoglobin, setHemoglobin] = useState("")
    const [sugarLevel, setSugarLevel] = useState("")

    const handleSubmit = (e) => {
        e.preventDefault();
       
      };


  return (
    <div className="screening-result-wrapper">
        <div className="background">
            <BackThreeD/>
        </div>
        <div className="form-box-user-registration">
            <div className="user-form">
                <form onSubmit={handleSubmit}>
                    <h1>Screening Result</h1>
                    <span style={{fontSize:"14px"}}>Fill up the form carefully to register your account with Blood Sewa.</span>
                    <br/>

                    <div className="input-box" style={{marginTop:"7px"}}>
                        <input type="text"
                        value = {bloodPressure}
                        onChange={(e) => setBloodPressure(e.target.value)}
                        placeholder='Full Name'/>
                        <FaUser className="icon"/>
                    </div>
                    <div className="input-box" style={{marginTop:"7px"}}>
                        <input type="text"
                        value = {pulseRate}
                        onChange={(e) => setPulseRate(e.target.value)}
                        placeholder='Full Name'/>
                        <FaUser className="icon"/>
                    </div>
                    <div className="input-box" style={{marginTop:"7px"}}>
                        <input type="text"
                        value = {temperature}
                        onChange={(e) => setTemperature(e.target.value)}
                        placeholder='Full Name'/>
                        <FaUser className="icon"/>
                    </div>
                    <div className="input-box" style={{marginTop:"7px"}}>
                        <input type="text"
                        value = {hemoglobin}
                        onChange={(e) => setHemoglobin(e.target.value)}
                        placeholder='Full Name'/>
                        <FaUser className="icon"/>
                    </div>
                    <div className="input-box" style={{marginTop:"7px"}}>
                        <input type="text"
                        value = {sugarLevel}
                        onChange={(e) => setSugarLevel(e.target.value)}
                        placeholder='Full Name'/>
                        <FaUser className="icon"/>
                    </div>


                    <div className="button">
                        <button type="submit">Add</button>
                    </div>

                   
                </form>
            </div>
        </div>
    </div>
  )
}

export default ScreeningResultModal