import React,{useState, useEffect} from 'react'
import './PastEventDetail.css'

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

import { format } from "date-fns";
import MyBarChart from '../../Utils/MyBarChart'

import { useNavigate, useLocation } from 'react-router-dom';

import { IoMdSquare } from "react-icons/io";
import { TbAxisX, TbAxisY } from "react-icons/tb";

import {donorList} from '../../UserDashboard/DummyData'

import { useOrgAuthToken } from '../../../Logic/AuthKey';
import {api, localhost} from '../../../Logic/api'

function EventDetail() {
  const orgAuthToken = useOrgAuthToken();

  const [toggleScreeningResultModal, setToggleScreeningResultModal] = useState(false);

  const [bloodPressure, setBloodPressure] = useState("")
  const [pulseRate, setPulseRate] = useState("")
  const [temperature, setTemperature] = useState("")
  const [hemoglobin, setHemoglobin] = useState("")
  const [sugarLevel, setSugarLevel] = useState("")

  const handleSubmit = (e) => {
      e.preventDefault();
      
    };

  const loc = useLocation()
  const eventId = loc.state?.eventId
  const eventName = loc.state?.name
  const eventDate = loc.state?.date
  const donorNumber = loc.state?.donorNumber
  const volunteerNumber = loc.state?.volunteerNumber
  const organizer = loc.state?.organizer
  const location = loc.state?.location
  const slug = loc.state?.slug
  console.log(slug)

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [checkedInDonorList, setCheckedInDonorList] = useState([]);
  const [checkedInVolunteerList, setCheckedInVolunteerList] = useState([]);
  

  //fetching scan gareko user haru(checkedin user list)
    useEffect(() => {
      if(!orgAuthToken){
        setError('No auth token found. Please log in');
        setLoading(false);
        return;
      }
  
      const fetchData = async () => {
        try {
          const response = await fetch(api+'events/'+slug+'/checkin/list/',{
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
          console.log(result)
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
    },[slug, orgAuthToken]);
  
  
     //fetching scan gareko volunteer haru(checkedin volunteer list)
     useEffect(() => {
      if(!orgAuthToken){
        setError('No auth token found. Please log in');
        setLoading(false);
        return;
      }
  
      const fetchData = async () => {
        try {
          const response = await fetch(api+'events/'+slug+'/volunteer/checkin/list/',{
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
    },[slug, orgAuthToken]);
  
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
              <span>Event Name: <span style={{fontWeight:"bold"}}>{eventName}</span></span>
              <span>Event Date: <span style={{fontWeight:"bold"}}>{format(eventDate, "MMMM dd, yyyy")}</span></span>
              <span>Donor Count: <span style={{fontWeight:"bold"}}>{donorNumber}</span></span>
              <span>Volunteer Count: <span style={{fontWeight:"bold"}}>{volunteerNumber}</span></span>
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
                  {checkedInDonorList.map((donor, index) => (
                    <tr key={index}>
                      <td>{index+1}</td> {/* Format date */}
                      <td  className='table-data'>{donor.name}</td>
                      <td  className='table-data'>{donor.blood_group}</td>
                      <td>{donor.contact}</td>
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