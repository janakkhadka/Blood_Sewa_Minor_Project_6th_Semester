import React,{useState} from 'react';
import './ScreeningResultModal.css';

import { Link } from "react-router-dom";


import { FaUser } from "react-icons/fa";




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
        <div className="screening-form">
            <form onSubmit={handleSubmit}>
                <h1>Screening Result</h1>
                <span style={{fontSize:"14px"}}>Enter the info carefully.</span>
                <br/>

                <div className="input-box" style={{marginTop:"7px"}}>
                    <input type="text"
                    value = {bloodPressure}
                    onChange={(e) => setBloodPressure(e.target.value)}
                    placeholder='Blood Pressure'/>
                    <FaUser className="icon"/>
                </div>
                <div className="input-box" style={{marginTop:"7px"}}>
                    <input type="text"
                    value = {pulseRate}
                    onChange={(e) => setPulseRate(e.target.value)}
                    placeholder='Pulse Rate'/>
                    <FaUser className="icon"/>
                </div>
                <div className="input-box" style={{marginTop:"7px"}}>
                    <input type="text"
                    value = {temperature}
                    onChange={(e) => setTemperature(e.target.value)}
                    placeholder='Body Temperature'/>
                    <FaUser className="icon"/>
                </div>
                <div className="input-box" style={{marginTop:"7px"}}>
                    <input type="text"
                    value = {hemoglobin}
                    onChange={(e) => setHemoglobin(e.target.value)}
                    placeholder='Hemoglobin'/>
                    <FaUser className="icon"/>
                </div>
                <div className="input-box" style={{marginTop:"7px"}}>
                    <input type="text"
                    value = {sugarLevel}
                    onChange={(e) => setSugarLevel(e.target.value)}
                    placeholder='Blood Sugar Level'/>
                    <FaUser className="icon"/>
                </div>


                <div className="button">
                    <button type="submit">Add</button>
                </div>

                
            </form>
        </div>
    </div>
  )
}

export default ScreeningResultModal