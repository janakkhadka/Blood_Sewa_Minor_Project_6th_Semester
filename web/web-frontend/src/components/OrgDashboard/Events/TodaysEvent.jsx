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

import { format } from "date-fns";

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

  const [todayEventData, setTodayEventData] = useState();
  const [todayEventList, setTodayEventList] = useState([]);
  const [singleData, setSingleData] = useState({
    id: "1",
    title: "Default Event Title",
    date: "2025-01-01",
    location: "Default Location",
    description: "Default Description",
    organizer: "Default Organizer",
    startTime: "10:00 AM",
    endTime: "5:00 PM",
    slug: "default-slug",
    qrCode: "default-qr-code-url",
  });
  //inventory ko data taneko server bata
  useEffect(() => {
    if (!orgAuthToken) {
        setError('No auth token found. Please log in');
        setLoading(false);
        console.log('No auth token found. Please log in');
        return;
      }
    const fetchData = async () => {
      try {
        const response = await fetch(api+'todayevents/', {
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
        const event = result[0] || {}; // Use the first event or an empty object

      const transformedEvents = {
        id: event.id ,
        title: event.name,
        date: event.date,
        location: event.location,
        description: event.description,
        organizer: event.organizer,
        startTime: event.start_time,
        endTime: event.end_time,  
        slug: event.slug,
        qrCode: localhost + (event.qr_code),
      };
        
        console.log('Transformed Events:', JSON.stringify(transformedEvents, null, 2));
        // setTodayEventList(transformedEvents);
        // console.log('Fetched Result:', todayEventList);
        setTodayEventData(result);
        setSingleData(transformedEvents);
        console.log('Data Upcoming:', todayEventData);

        
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  },[orgAuthToken]);



  //aako data lai rakheko
  useEffect(() => {
    if (todayEventData) {
      // Assuming `dataUpcoming` is the response
      const transformedEvents = {
        id: todayEventData.id,
        title: todayEventData.name,
        date: todayEventData.date,
        location: todayEventData.location,
        description: todayEventData.description,
        organizer: todayEventData.organizer,
        startTime: todayEventData.start_time,
        endTime: todayEventData.end_time,
        slug: todayEventData.slug,
        qrCode: localhost + (todayEventData.qr_code),
      };
      console.log('Transformed Events:', JSON.stringify(transformedEvents, null, 2));
      // setTodayEventList(transformedEvents);
      // console.log('Fetched Result:', todayEventList);
      setSingleData(transformedEvents);
      console.log(singleData)
    }
  }, [todayEventData]);

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
            <h1>Ongoing Event</h1>
            <section className='event-details'>
              <h3>Event Details</h3>
              <div className="icon-info-wrapper">
                <MdEvent/>
                <span>Event: <span style={{fontWeight:"bold"}}>{singleData.title}</span></span>
              </div>
              <div className="icon-info-wrapper">
                <MdDateRange/>
                <span>Date: <span style={{fontWeight:"bold"}}>{format(getTodayDate(), "MMMM dd, yyyy")}</span></span>
              </div>
              <div className="icon-info-wrapper">
                <IoMdTime/>
                <span>Time: <span style={{fontWeight:"bold"}}>{singleData.startTime || "00:00"+"-"+singleData.endTime || "00:00"}</span></span>
              </div>
              <div className="icon-info-wrapper">
                <IoLocationOutline/>
                <span>Location: <span style={{fontWeight:"bold"}}>{singleData.location}</span></span>
              </div>
              <div className="icon-info-wrapper">
                <MdOutlineCountertops/>
                <span>Expected Donor Count: <span style={{fontWeight:"bold"}}>35</span></span>
              </div>
            </section>
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
                  {donorList.map((donor, index) => (
                    <tr key={index}>
                      <td>{donor.sn}</td> {/* Format date */}
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
              <div className="qr-wrapper" style={{width:"800px"}}>
                <img className='qr-image' src={singleData.qrCode} alt="qr-code"/>
              </div>
            </section>
          </div>
        
      </div>
    </div>
  )
}

export default TodaysEvent