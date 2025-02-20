import React,{useState, useEffect} from 'react'


import {events, pastEvents} from '../../UserDashboard/DummyData'
import { format } from "date-fns";

import { TiTick, TiTimes } from "react-icons/ti";

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

import { useOrgAuthToken } from '../../../Logic/AuthKey';
import {api} from '../../../Logic/api';


function CollabRequest() {
  const orgAuthToken = useOrgAuthToken();

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [collabRequest, setCollabRequest] = useState([]);

   //fetching all my events data from server and storing to specific states
    useEffect(() => {
      if(!orgAuthToken){
        setError('No auth token found. Please log in');
        setLoading(false);
        return;
      }
  
      const fetchData = async () => {
        try {
          const response = await fetch(api+'pending/request',{
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
  
          setCollabRequest(result.pending_requests);
          console.log(result);
  
        }catch (err) {
          setError(err.message);
        } finally {
          setLoading(false);
        }
      };
      fetchData();
    },[orgAuthToken]);
    

    const handleAction = async ( slug, isAccepted) => {
      //e.preventDefault();
      var createEventData = {}
      if(isAccepted){
        createEventData = {
          "action": "approve"
      }
      }
      if(!isAccepted){
        createEventData = {
          "action": "reject"
      }
      }
      
      console.log(JSON.stringify(createEventData))

      try {
          // server maa data pathauna lai update garda
          const response = await fetch(api + 'event/'+slug+'/manage/', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
                  Authorization: `Bearer ${orgAuthToken}`,
              },
              body: JSON.stringify(createEventData),
          });;
          const responseData = await response.json();

          if (response.ok) {
              console.log('Server Response:', responseData);
          } else {
              console.log(response)
              console.error(responseData);
              // alert(responseData);
          }
      } catch (error) {
          console.error('Catch:', error);
          // alert('catch: ' + error);
      }
    }
  return (
    <div className="collaboration-request-wrapper">
      <div className="syringe">
          <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgComponentNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />
      <div className="collaboration-request">
        <section className='collaboration-events'>
          <div className="h1">
            <h1>Request for Collaboration</h1>
          </div>
          <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
            <colgroup>
              <col style={{ width: "14%" }} />
              <col style={{ width: "14%" }} />  
              <col style={{ width: "20%" }} />
              <col style={{ width: "18%" }} />
              <col style={{ width: "12%" }} />
            </colgroup>
            <thead>
              <tr>
                <th>Event Date</th>
                <th>Requested By</th>
                <th>Event Name</th>
                <th>Location</th>
                <th>Accept/Reject</th>
              </tr>
            </thead>
            <tbody>
              {collabRequest.map((request, index) => (
                <tr key={index}>
                  <td>{format(request.event_date, "MMMM dd, yyyy")}</td>
                  <td>{request.organizer}</td>
                  <td  className='table-data'>{request.event_name}</td>
                  <td  className='table-data'>{request.event_location}</td>
                  <td>
                    <div className="decesion-button">
                      <button  onClick = {() => handleAction(request.slug, true)}><TiTick/></button>
                      <button  onClick = {() => handleAction(request.slug, false)}><TiTimes /></button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </div>
    </div>
  )
}

export default CollabRequest

import './CollabRequest.css'