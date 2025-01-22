
import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Login from './LoginRegistration/Login';
import RegistrationOrg from './LoginRegistration/RegistrationOrg';
import RegistrationUser from './LoginRegistration/RegistrationUser';

import LandingPage from './Homepage/LandingPage';

import UserDashboard from './UserDashboard/UserDashboard.jsx'
import UserDashboardHome from './UserDashboard/UserDashboardHome.jsx'
import BloodRequestForm from './Common/BloodRequestForm';
import ScheduleDonation from './UserDashboard/ScheduleDonation';
import UserOrganizeEvent from './UserDashboard/UserOrganizeEvent';
import UserBloodAvailability from './UserDashboard/BloodAvailability';
import UserProfile from './UserDashboard/UserProfile';
import UserProfileUpdate from './UserDashboard/UserProfileUpdate';
import SearchDonor from './UserDashboard/SearchDonor.jsx';

import OrgDashboard from './OrgDashboard/OrgDashboard.jsx';
import Events from './OrgDashboard/Events/Events.jsx';
import TodaysEvent from './OrgDashboard/Events/TodaysEvent.jsx';
import OrgOrganizeEvent from './OrgDashboard/Events/OrgOrganizeEvent.jsx';
import CollabRequest from './OrgDashboard/Events/CollabRequest.jsx';
import ScheduledDonation from './OrgDashboard/ScheduledDonation.jsx';
import EventDetail from './OrgDashboard/Events/EventDetail.jsx';

const Navigation = () => {
    return (
        <Router>
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/registrationorg" element={<RegistrationOrg />} />
            <Route path="/registrationuser" element={<RegistrationUser />} />

            <Route path ="/user-dashboard" element={<UserDashboard/>}/>
            <Route path ="/blood-request-form" element = {<BloodRequestForm/>}/>
            <Route path ="/schedule-donation" element = {<ScheduleDonation/>}/>
            <Route path ="/user-organize-event" element = {<UserOrganizeEvent/>}/>
            <Route path ="/user-blood-availability" element = {<UserBloodAvailability/>}/>
            <Route path ="/user-profile" element = {<UserProfile/>}/>
            <Route path ="/user-profile-update" element = {<UserProfileUpdate/>}/>
            <Route path ="/search-donor" element = {<SearchDonor/>}/>
            
            {/* organization route */}
            <Route path ="/org-dashboard" element = {<OrgDashboard/>}/>
            <Route path ="/events" element = {<Events/>}/>
            <Route path ="/todays-event" element = {<TodaysEvent/>}/>
            <Route path ="/org-organize-event" element = {<OrgOrganizeEvent/>}/>
            <Route path ="/collab-request" element = {<CollabRequest/>}/>
            <Route path ="/event-details" element = {<EventDetail/>}/>

            <Route path ="/scheduled-donation" element = {<ScheduledDonation/>}/>
            
            
          </Routes>
        </Router>
      );
}

export default Navigation