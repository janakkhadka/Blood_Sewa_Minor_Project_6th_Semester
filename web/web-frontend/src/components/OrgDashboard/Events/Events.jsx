import React,{useState, useEffect} from 'react'

import './Events.css'

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

import { format } from "date-fns";

import { useNavigate } from 'react-router-dom';

import {events, pastEvents} from '../../UserDashboard/DummyData'
import { FaDiamond } from "react-icons/fa6";

import { useOrgAuthToken } from '../../../Logic/AuthKey';
import {api} from '../../../Logic/api';


function Events() {
  const orgAuthToken = useOrgAuthToken();
  const navigate = useNavigate();

  const [comingEvent, setComingEvent] = useState(false);

  

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [upcomingEventList, setUpcomingEventList] = useState([]);
   
  const [selectedEvent, setSelectedEvent] = useState(events[0]);
  const handleEventClick = (eventId) => {
    const event = upcomingEventList.find(e => e.id === eventId);
    setSelectedEvent(event);
    setComingEvent(true);
  };

  const [pastEventData, setPastEventData] = useState();
  const [pastEventList, setPastEventList] = useState([]);

  //fetching all my events data from server and storing to specific states
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

        const filteredUpcomingEvents = result.filter(event => event.date > today);
        setUpcomingEventList(filteredUpcomingEvents);
        console.log("Upcoming Events:", filteredUpcomingEvents);

        const filteredPastEvents = result.filter(event => event.date < today);
        setPastEventList(filteredPastEvents);
        console.log("Past Events:", filteredPastEvents);

      }catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  },[orgAuthToken]);

  
  // if (loading) return <p>Loading...</p>;
  // if (error) return <p>Error: {error}</p>;



  
  return (
    <div className='events-wrapper'>
        <div className="syringe">
          <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgComponentNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />
        <div className="events">
            <div className="events-section-wrapper">
                <div className="top-section">
                    <div className="top-left-section">
                      <section className='today-event'>
                        <h1>Today Event</h1>
                        <button className="top-left-button" onClick={() => navigate("/todays-event")}>Manage</button>
                      </section>
                      <section className='create-event-wrapper'>
                        <h1>Organize New Event</h1>
                        <button className="top-left-button" onClick={() => navigate("/org-organize-event")}>Create New Event</button>
                      </section>
                      <div className="collaboration-request-section">
                        <h2>Collab Request</h2>
                        <button className="top-left-button" onClick={() => navigate("/collab-request")}>View Requests</button>
                      </div>
                    </div>
                    
                    <section className='upcoming-events'>
                      <div className="h1">
                        <h1>Upcomming Events!</h1>
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
                            <th>Date</th>
                            <th>Event Name</th>
                            <th>Location</th>
                          </tr>
                        </thead>
                        <tbody>
                          {upcomingEventList.map((event, index) => (
                            // console.log('Event:', event.date),
                            <tr key={index}>
                              <td>{format(event.date, "MMMM dd, yyyy")}</td> {/* Format date */}
                              <td  className='table-data'>{event.name}</td>
                              <td  className='table-data'>{event.location}</td>
                              <td><button className="notify-button" onClick={()=>handleEventClick(event.id)}>Detail</button></td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </section>
                    
                </div>

                <div className="bottom-section">
                  <section className='past-events'>
                    <div className="h1">
                      <h1>Past Events</h1>
                    </div>
                    <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
                      <colgroup>
                        <col style={{ width: "20%" }} />
                        <col style={{ width: "28%" }} /> 
                        <col style={{ width: "12%" }} />
                        <col style={{ width: "25%" }} /> 
                        <col style={{ width: "12%" }} />
                      </colgroup>
                      <thead>
                        <tr>
                          <th>Date</th>
                          <th>Event Name</th>
                          <th>Donor Count</th>
                          <th>Location</th>
                        </tr>
                      </thead>
                      <tbody>
                        {pastEventList.map((event, index) => (
                              // console.log('Event:', event.date),
                              <tr key={index}>
                                <td>{format(event.date, "MMMM dd, yyyy")}</td> {/* Format date */}
                                <td  className='table-data'>{event.title}</td>
                                <td style={{paddingLeft:"40px"}}>10</td>
                                <td  className='table-data'>{event.location}</td>
                                <td><button className="notify-button" onClick={() => navigate("/past-event-detail", {state: {eventId:event.id}})}>Detail</button></td>
                              </tr>
                            ))}
                      </tbody>
                    </table>
                  </section>
            </div>
          </div>
        </div>
        {comingEvent && (
            <div className="coming-event-wrapper">
                <div className="coming-event">
                    <div className="close-button">
                        <button onClick={() => setComingEvent(false)}>X</button>
                    </div>
                    <h2 style={{color:"var(--secondary-text-color)"}}>Event Detail</h2>
                    <div className="event-info-wrapper">
                      <div className="event-info">
                        <div className="info-list-wrapper">
                          <FaDiamond/>
                          <span>Event Name: <span style={{fontWeight:"bold"}}>{selectedEvent.title}</span> </span>
                        </div>
                        <div className="info-list-wrapper">
                          <FaDiamond/>
                          <span>Date: <span style={{fontWeight:"bold"}}>{format(new Date(selectedEvent.date), "MMMM dd, yyyy")}</span></span>
                        </div>
                        <div className="info-list-wrapper">
                          <FaDiamond/>
                          <span>Location: <span style={{fontWeight:"bold"}}>{selectedEvent.location}</span></span>
                        </div>
                        <div className="info-list-wrapper">
                          <FaDiamond/>
                          <span>Expected Donor Number: <span style={{fontWeight:"bold"}}>{selectedEvent.donorNumber}</span></span>
                        </div>
                        <div className="info-list-wrapper">
                          <FaDiamond/>
                          <span>Expected Volunteer Number: <span style={{fontWeight:"bold"}}>{selectedEvent.volunteerNumber}</span></span>
                        </div>
                      </div>
                      <div className='delete-button-wrapper'>
                        <button type='submit'   className="delete-button">
                            Delete Event
                        </button>
                      </div>
                    </div>   
                </div>
            </div>
        )}
    </div>
  )
}

export default Events