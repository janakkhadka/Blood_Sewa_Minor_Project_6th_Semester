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


  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [todayEventData, setTodayEventData] = useState();
  const [todayEventList, setTodayEventList] = useState([]);
  const [singleData, setSingleData] = useState('');
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
        const transformedEvents = result.map((event, index) => ({
          id: (index + 1).toString() || 1,
          title: event.name || 'Event Title',
          date: event.date,
          location: event.location || 'Event Location',
          description: event.description || 'Event Description',
          organizer: event.organizer || 'Event Organizer',
          startTime: event.start_time || 'Event Start Time',
          endTime: event.end_time || 'Event End Time',
          slug: event.slug || 'Event Slug',
          qrCode: localhost+event.qr_code || 'Event QR Code',
        }));
        
        console.log('Transformed Events:', JSON.stringify(transformedEvents, null, 2));
        // setTodayEventList(transformedEvents);
        // console.log('Fetched Result:', todayEventList);
        setTodayEventData(result);
        setSingleData(transformedEvents[0]);
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
      const transformedEvents = todayEventData.map((event, index) => ({
        id: (index + 1).toString() || 1,
        title: event.name || 'Event Title',
        date: event.date,
        location: event.location || 'Event Location',
        description: event.description || 'Event Description',
        organizer: event.organizer || 'Event Organizer',
        startTime: event.start_time || 'Event Start Time',
        endTime: event.end_time || 'Event End Time',
        slug: event.slug || 'Event Slug',
        qrCode: localhost+event.qr_code || 'Event QR Code',
      }));
      console.log('Transformed Events:', JSON.stringify(transformedEvents, null, 2));
      // setTodayEventList(transformedEvents);
      // console.log('Fetched Result:', todayEventList);
      setSingleData(transformedEvents[0]);
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
                <span>Date: <span style={{fontWeight:"bold"}}>{format(singleData.date || 2025-1-25, "MMMM dd, yyyy")}</span></span>
              </div>
              <div className="icon-info-wrapper">
                <IoMdTime/>
                <span>Time: <span style={{fontWeight:"bold"}}>{singleData.startTime+"-"+singleData.endTime}</span></span>
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