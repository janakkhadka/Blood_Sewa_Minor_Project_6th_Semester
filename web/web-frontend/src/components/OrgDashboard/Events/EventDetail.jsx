import React from 'react'
import './EventDetail.css'

import NavigationBar from '../../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgNavbarComponent'
import BackThreeD from '../../LoginRegistration/3d'

import { format } from "date-fns";

import { useNavigate } from 'react-router-dom';

import {events, pastEvents} from '../../UserDashboard/DummyData'

function EventDetail() {
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

        </div>
    </div>
  )
}

export default EventDetail