import React from 'react'

import './CollabRequest.css'
import {events, pastEvents} from '../../UserDashboard/DummyData'
import { format } from "date-fns";

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

function CollabRequest() {
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
        <section className='upcoming-events'>
          <div className="h1">
            <h1>Request for Collaboration</h1>
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
                <th>Event Date</th>
                <th>Event Name</th>
                <th>Requested By</th>
                <th>Location</th>
                <th>Accept/Reject</th>
              </tr>
            </thead>
            <tbody>
              {events.map((event, index) => (
                <tr key={index}>
                  <td>{format(event.date, "MMMM dd, yyyy")}</td> {/* Format date */}
                  <td  className='table-data'>{event.title}</td>
                  <td>Janak Khadka</td>
                  <td  className='table-data'>{event.location}</td>
                  <td><button className="notify-button" onClick={() => navigate("/")}>Detail</button></td>
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