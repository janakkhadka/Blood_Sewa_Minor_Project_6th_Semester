import React, { useEffect, useState } from 'react'

import './TodaysEvent.css'

import { MdDateRange,MdCountertops,MdOutlineCountertops} from "react-icons/md";
import { IoMdTime } from "react-icons/io";
import { MdEvent } from "react-icons/md";
import { IoLocationOutline } from "react-icons/io5";
import { IoMdSquare } from "react-icons/io";

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

import {donorList} from '../../UserDashboard/DummyData'

import { format, set } from "date-fns";

import { useNavigate } from 'react-router-dom';

import { useOrgAuthToken } from '../../../Logic/AuthKey';
import {api, localhost} from '../../../Logic/api'


function TodaysEvent() {
  const orgAuthToken = useOrgAuthToken();
  const navigate = useNavigate()

  const [toggleScreeningResultModal, setToggleScreeningResultModal] = useState(false);

  const [bloodPressure, setBloodPressure] = useState("")
  const [pulseRate, setPulseRate] = useState("")
  const [temperature, setTemperature] = useState("")
  const [hemoglobin, setHemoglobin] = useState("")
  const [sugarLevel, setSugarLevel] = useState("")

  const handleSubmit = (e) => {
      e.preventDefault();
      
    };

  const getTodayDate = () => {
    const today = new Date();
  
    // Format date as YYYY-MM-DD
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Add 1 since months are 0-indexed
    const day = String(today.getDate()).padStart(2, '0');
  
    return `${year}-${month}-${day}`; // Format: YYYY-MM-DD
  };


  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [todayEventData, setTodayEventData] = useState({});
  const [checkedInDonorList, setCheckedInDonorList] = useState([]);
  const [checkedInVolunteerList, setCheckedInVolunteerList] = useState([]);

  //fetching all my events data from server and storing to specific states(today events)
  useEffect(() => {
    if(!orgAuthToken){
      setError('No auth token found. Please log in');
      setLoading(false);
      return;
    }

    const fetchData = async () => {
      try {
        const response = await fetch(api+'my-all-events/',{
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${orgAuthToken}`,
          },
        });

        if (!response.ok) {
          const errorResponse = await response.json();
          console.log(errorResponse);
        throw new Error(`Error: ${response.status}`);
        }

        const result = await response.json();

        const today = new Date().toISOString().split('T')[0];

        const filteredTodayEvents = result.filter(event => event.date == today);

        const event = filteredTodayEvents[0] || {}; // Use the first event or an empty object

        setTodayEventData(event);
        console.log('Transformed Events:', JSON.stringify(todayEventData, null, 2));
        console.log(todayEventData)


      }catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  },[orgAuthToken]);


  //fetching scan gareko user haru(checkedin user list)
  useEffect(() => {
    if(!orgAuthToken){
      setError('No auth token found. Please log in');
      setLoading(false);
      return;
    }

    const fetchData = async () => {
      try {
        const response = await fetch(api+'events/'+todayEventData.slug+'/checkin/list/',{
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${orgAuthToken}`,
          },
        });

        if (!response.ok) {
          const errorResponse = await response.json();
          console.log(errorResponse);
        throw new Error(`Error: ${response.status}`);
        }

        const result = await response.json();
        //console.log(result)
        setCheckedInDonorList(Array.isArray(result.checked_in_users ) ? result.checked_in_users  : []);
        //console.log(todayEventData.slug)

      }catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();

   // const intervalId = setInterval(fetchData, 3000);

    //return () => clearInterval(intervalId);
  },[todayEventData.slug, orgAuthToken]);


   //fetching scan gareko volunteer haru(checkedin volunteer list)
   useEffect(() => {
    if(!orgAuthToken){
      setError('No auth token found. Please log in');
      setLoading(false);
      return;
    }

    const fetchData = async () => {
      try {
        const response = await fetch(api+'events/'+todayEventData.slug+'/volunteer/checkin/list/',{
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${orgAuthToken}`,
          },
        });

        if (!response.ok) {
          const errorResponse = await response.json();
          console.log(errorResponse);
        throw new Error(`Error: ${response.status}`);
        }

        const result = await response.json();
        //console.log(result)
        setCheckedInVolunteerList(Array.isArray(result.checked_in_users ) ? result.checked_in_users  : []);
        //console.log(todayEventData.slug)

      }catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();

   // const intervalId = setInterval(fetchData, 3000);

    //return () => clearInterval(intervalId);
  },[todayEventData.slug, orgAuthToken]);



  return (
    <div className='todays-event-wrapper'>
      <div className="syringe">
          <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgComponentNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />
      <div className="todays-event">
        <div className="left-section">
          <div className="left-top-section">
            <div className="left-top-left-section">
              <h1>Ongoing Event</h1>  
              <section className='event-details'>
                <h3>Event Details</h3>
                <div className="icon-info-wrapper">
                  <MdEvent/>
                  <span>Event: <span style={{fontWeight:"bold"}}>{todayEventData.name}</span></span>
                </div>
                <div className="icon-info-wrapper">
                  <MdDateRange/>
                  <span>Date: <span style={{fontWeight:"bold"}}>{format(getTodayDate(), "MMMM dd, yyyy")}</span></span>
                </div>
                <div className="icon-info-wrapper">
                  <IoMdTime/>
                  <span>Time: <span style={{fontWeight:"bold"}}>{todayEventData.start_time +"-"+todayEventData.end_time}</span></span>
                </div>
                <div className="icon-info-wrapper">
                  <IoLocationOutline/>
                  <span>Location: <span style={{fontWeight:"bold"}}>{todayEventData.location}</span></span>
                </div>
                <div className="icon-info-wrapper">
                  <MdOutlineCountertops/>
                  <span>Expected Donor: <span style={{fontWeight:"bold"}}>35</span></span>
                </div>
              </section>
            </div>
            <div className="left-top-right-section">
            <section className='volunteer-details'>
              <div className="h1">
                <h1>Volunteer List</h1>
              </div>
              <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
                <colgroup>
                  <col style={{ width: "20%" }} />
                  <col style={{ width: "45%" }} /> 
                  <col style={{ width: "35%" }} />
                </colgroup>
                <thead>
                  <tr>
                    <th>SN</th>
                    <th>Name</th>
                    <th>Contact</th>
                  </tr>
                </thead>
                <tbody>
                  {checkedInVolunteerList.map((donor, index) => (
                    <tr key={index}>
                      <td>{index+1}</td>
                      <td  className='table-data'>{donor.name}</td>
                      <td  className='table-data'>{donor.contact}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </section>
            </div>
            
          </div>
          <div className="left-bottom-section">
            <section className='donor-details'>
              <div className="h1">
                <h1>Donor List</h1>
              </div>
              <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
                <colgroup>
                  <col style={{ width: "20%" }} />
                  <col style={{ width: "32%" }} /> 
                  <col style={{ width: "18%" }} />
                  <col style={{ width: "30%" }} />
                </colgroup>
                <thead>
                  <tr>
                    <th>SN</th>
                    <th>Donor Name</th>
                    <th>Blood Group</th>
                    <th style={{textAlign:"center"}}>Screening Result</th>
                  </tr>
                </thead>
                <tbody>
                  {checkedInDonorList.map((donor, index) => (
                    <tr key={index}>
                      <td>{index+1}</td>
                      <td  className='table-data'>{donor.name}</td>
                      <td  className='table-data'>{donor.blood_group}</td>
                      <td style={{textAlign:"center"}}><button className="notify-button" onClick={
                        () => setToggleScreeningResultModal(true)
                        }>Add</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </section>
          </div>
        </div>
        <div className="right-section">
            <section className='scan-qr'>
              <span className='donor-qr'>Donor/Volunteer QR</span>
              <div className="qr-wrapper" style={{width:"800px"}}>
                <img className='qr-image' src={localhost+todayEventData.qr_code} alt="qr-code"/>
              </div>
            </section>
          </div>
        
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
  )
}

export default TodaysEvent