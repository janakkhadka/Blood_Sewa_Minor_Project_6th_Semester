import React, { useEffect, useState } from 'react'

import './TodaysEvent.css'

import { MdDateRange,MdCountertops,MdOutlineCountertops} from "react-icons/md";
import { IoMdTime } from "react-icons/io";
import { MdEvent } from "react-icons/md";
import { IoLocationOutline } from "react-icons/io5";

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

        const filteredTodayEvents = result.filter(event => event.date = today);

        const event = filteredTodayEvents[0] || {}; // Use the first event or an empty object

        setTodayEventData(event);
        console.log('Transformed Events:', JSON.stringify(todayEventData, null, 2));


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
        console.log(result)
        setCheckedInDonorList(Array.isArray(result) ? result : []);

      }catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
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
                  <span>Expected Donor Count: <span style={{fontWeight:"bold"}}>35</span></span>
                </div>
              </section>
            </div>
            <div className="left-top-right-section">
              <section className='scan-qr-volunteer'>
                <span className='volunteer-qr'>Volunteer QR</span>
                <div className="qr-wrapper-volunteer" style={{width:"100px"}}>
                  <img className='qr-image-volunteer' src={localhost+todayEventData.qr_code} alt="qr-code"/>
                </div>
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
                  <col style={{ width: "40%" }} /> 
                  <col style={{ width: "25%" }} />
                  <col style={{ width: "15%" }} />
                </colgroup>
                <thead>
                  <tr>
                    <th>SN</th>
                    <th>Donor Name</th>
                    <th>Blood Group</th>
                  </tr>
                </thead>
                <tbody>
                  {checkedInDonorList.map((donor, index) => (
                    <tr key={index}>
                      <td>{donor.sn}</td>
                      <td  className='table-data'>{donor.name}</td>
                      <td  className='table-data'>{donor.bloodGroup}</td>
                      <td><button className="notify-button" onClick={() => navigate("/")}>Add</button></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </section>
          </div>
        </div>
        <div className="right-section">
            <section className='scan-qr'>
              <span className='donor-qr'>Donor QR</span>
              <div className="qr-wrapper" style={{width:"800px"}}>
                <img className='qr-image' src={localhost+todayEventData.qr_code} alt="qr-code"/>
              </div>
            </section>
          </div>
        
      </div>
    </div>
  )
}

export default TodaysEvent