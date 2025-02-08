import React,{useState} from 'react'
import './PastEventDetail.css'

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

import { format } from "date-fns";
import MyBarChart from '../../Utils/MyBarChart'

import { useNavigate, useLocation } from 'react-router-dom';

import { IoMdSquare } from "react-icons/io";
import { TbAxisX, TbAxisY } from "react-icons/tb";

import {events, pastEvents} from '../../UserDashboard/DummyData'
import {donorList} from '../../UserDashboard/DummyData'
import ScreeningResultModal from './ScreeningResultModal'

function EventDetail() {
  const [toggleScreeningResultModal, setToggleScreeningResultModal] = useState(false);

  const [bloodPressure, setBloodPressure] = useState("")
  const [pulseRate, setPulseRate] = useState("")
  const [temperature, setTemperature] = useState("")
  const [hemoglobin, setHemoglobin] = useState("")
  const [sugarLevel, setSugarLevel] = useState("")

  const handleSubmit = (e) => {
      e.preventDefault();
      
    };

  const location = useLocation()
  const eventId = location.state?.eventId;
  console.log(eventId)
  return (
    <div className="event-details-wrapper">
        <div className="syringe">
          <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgComponentNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />
        <div className="event-details">
          <div className="top-section">
            <section className='top-left'>
              <h3>Event Details</h3>
              <span>Event Name: <span style={{fontWeight:"bold"}}>Bir Hospital Donation Event</span></span>
              <span>Event Date: <span style={{fontWeight:"bold"}}>12 June, 2024</span></span>
              <span>Donor Count: <span style={{fontWeight:"bold"}}>79</span></span>
              <span>Volunteer Count: <span style={{fontWeight:"bold"}}>18</span></span>
            </section>
            <section className='top-right'>
              <div className="h1-wrapper">
                  <h1>Blood Collection</h1>
              </div>
              <div className="bar-chart-wrapper">
                <div className="chart-info-wrapper">
                    <div className="chart-info">
                        <TbAxisX/>
                        <span>X-Axis: Blood Group</span>
                    </div>
                    <div className="chart-info">
                        <TbAxisY/>
                        <span>Y-Axis: Pint Value</span>
                    </div>
                    <div className="chart-info">
                        <IoMdSquare style={{color:"#8B0000"}}/>
                        <span>Pint value range 0-10</span>
                    </div>
                    <div className="chart-info">
                        <IoMdSquare style={{color:"#ff9510"}}/>
                        <span>Pint value range 11-25</span>
                    </div>
                    <div className="chart-info">
                        <IoMdSquare style={{color:"rgb(25, 160, 25)"}}/>
                        <span>Pint value greater than 25</span>
                    </div>
                </div>
                <MyBarChart width={700} height={220} className='bar-chart'/>
              </div>
            </section>
          </div>
          <div className="bottom-section">
            <section className='bottom-left'>
              <div className="h1">
                <h1>Donor List</h1>
              </div>
              <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
                <colgroup>
                  <col style={{ width: "4%" }} />
                  <col style={{ width: "22%" }} /> 
                  <col style={{ width: "15%" }} />
                  <col style={{ width: "20%" }} />
                  <col style={{ width: "20%" }} />
                </colgroup>
                <thead>
                  <tr>
                    <th>SN</th>
                    <th>Donor Name</th>
                    <th>Blood Group</th>
                    <th>Phone Number</th>
                    <th>Screening Result</th>
                  </tr>
                </thead>
                <tbody>
                  {donorList.map((donor, index) => (
                    <tr key={index}>
                      <td>{donor.sn}</td> {/* Format date */}
                      <td  className='table-data'>{donor.name}</td>
                      <td  className='table-data'>{donor.bloodGroup}</td>
                      <td>9840989641</td>
                      <td style={{textAlign:"center"}}><button className="notify-button" onClick={
                        () => setToggleScreeningResultModal(true)
                        }>Add</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </section>
            <section className='bottom-right'>
              <div className="h1">
                <h1>Volunteer List</h1>
              </div>
              <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
                <colgroup>
                  <col style={{ width: "15%" }} />
                  <col style={{ width: "45%" }} /> 
                  <col style={{ width: "40%" }} />
                </colgroup>
                <thead>
                  <tr>
                    <th>SN</th>
                    <th>Volunteer Name</th>
                    <th>Phone Number</th>
                  </tr>
                </thead>
                <tbody>
                  {donorList.map((donor, index) => (
                    <tr key={index}>
                      <td>{donor.sn}</td>
                      <td className='table-data'>{donor.name}</td>
                      <td>9840989641</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </section>
          </div>
          
            {toggleScreeningResultModal && (
              <div className="screening-modal">
                <div className="screening-result-wrapper">
                  <div className="screening-form">
                      <div className="close-button">
                        <button onClick={() => setToggleScreeningResultModal(false)}>X</button>
                      </div>
                      <form onSubmit={handleSubmit}>
                          
                          <h1>Screening Result</h1>
                          <span style={{fontSize:"14px"}}>Enter the info carefully.</span>
                          <br/>

                          <div className="input-box" style={{marginTop:"7px"}}>
                              <input type="text"
                              value = {bloodPressure}
                              onChange={(e) => setBloodPressure(e.target.value)}
                              placeholder='Blood Pressure'/>
                              <IoMdSquare className="icon"/>
                          </div>
                          <div className="input-box" style={{marginTop:"7px"}}>
                              <input type="text"
                              value = {pulseRate}
                              onChange={(e) => setPulseRate(e.target.value)}
                              placeholder='Pulse Rate'/>
                              <IoMdSquare className="icon"/>
                          </div>
                          <div className="input-box" style={{marginTop:"7px"}}>
                              <input type="text"
                              value = {temperature}
                              onChange={(e) => setTemperature(e.target.value)}
                              placeholder='Body Temperature'/>
                              <IoMdSquare className="icon"/>
                          </div>
                          <div className="input-box" style={{marginTop:"7px"}}>
                              <input type="text"
                              value = {hemoglobin}
                              onChange={(e) => setHemoglobin(e.target.value)}
                              placeholder='Hemoglobin'/>
                              <IoMdSquare className="icon"/>
                          </div>
                          <div className="input-box" style={{marginTop:"7px"}}>
                              <input type="text"
                              value = {sugarLevel}
                              onChange={(e) => setSugarLevel(e.target.value)}
                              placeholder='Blood Sugar Level'/>
                              <IoMdSquare className="icon"/>
                          </div>


                          <div className="button">
                              <button type="submit">Add</button>
                          </div>
                      </form>
                  </div>
                </div>
              </div>
            )}
        </div>
    </div>
  )
}

export default EventDetail