import React from 'react'

import './Events.css'

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

import { format } from "date-fns";

import { useNavigate } from 'react-router-dom';

import {events, pastEvents} from '../../UserDashboard/DummyData'


function Events() {
  const navigate = useNavigate()
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
                        <h1>Today's Event</h1>
                        <button className="top-left-button" onClick={() => navigate("/todays-event")}>Manage</button>
                      </section>
                      <section className='create-event-wrapper'>
                        <h1>Organize New Event</h1>
                        <button className="top-left-button" onClick={() => navigate("/org-organize-event")}>Create New Event</button>
                      </section>
                      <div className="collaboration-request-wrapper">
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
                            {events.map((event, index) => (
                              <tr key={index}>
                                <td>{format(event.date, "MMMM dd, yyyy")}</td> {/* Format date */}
                                <td  className='table-data'>{event.title}</td>
                                <td  className='table-data'>{event.location}</td>
                                <td><button className="notify-button" onClick={() => navigate("/")}>Detail</button></td>
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
                    <col style={{ width: "15%" }} />
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
                    {pastEvents.map((event, index) => (
                      <tr key={index}>
                        <td>{format(event.date, "MMMM dd, yyyy")}</td> {/* Format date */}
                        <td  className='table-data'>{event.title}</td>
                        <td style={{paddingLeft:"40px"}}>{event.donorNumber}</td>
                        <td  className='table-data'>{event.location}</td>
                        <td><button className="notify-button" onClick={() => navigate("/")}>Detail</button></td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </section>
            </div>
          </div>
        </div>
    </div>
  )
}

export default Events