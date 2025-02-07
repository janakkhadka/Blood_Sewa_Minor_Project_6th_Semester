
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

import SearchDonor from './Common/SearchDonor.jsx';

import OrgDashboard from './OrgDashboard/OrgDashboard.jsx';
import Events from './OrgDashboard/Events/Events.jsx';
import TodaysEvent from './OrgDashboard/Events/TodaysEvent.jsx';
import OrgOrganizeEvent from './OrgDashboard/Events/OrgOrganizeEvent.jsx';
import CollabRequest from './OrgDashboard/Events/CollabRequest.jsx';
import ScheduledDonation from './OrgDashboard/ScheduledDonation.jsx';
import PastEventDetail from './OrgDashboard/Events/PastEventDetail.jsx';
import UserPrivateRoute from './UserPrivateRoute.jsx';
import OrgPrivateRoute from './OrgPrivateRoute.jsx';

const Navigation = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/registrationorg" element={<RegistrationOrg />} />
        <Route path="/registrationuser" element={<RegistrationUser />} />
        <Route path="/blood-request-form" element={<BloodRequestForm />} />
        <Route path="/search-donor" element={ <SearchDonor />}/>

        {/* User Protected Routes */}
        <Route
          path="/user-dashboard"
          element={
            <UserPrivateRoute>
              <UserDashboard />
            </UserPrivateRoute>
          }
        />
        <Route
          path="/schedule-donation"
          element={
            <UserPrivateRoute>
              <ScheduleDonation />
            </UserPrivateRoute>
          }
        />
        <Route
          path="/user-organize-event"
          element={
            <UserPrivateRoute>
              <UserOrganizeEvent />
            </UserPrivateRoute>
          }
        />
        <Route
          path="/user-blood-availability"
          element={
            <UserPrivateRoute>
              <UserBloodAvailability />
            </UserPrivateRoute>
          }
        />
        <Route
          path="/user-profile"
          element={
            <UserPrivateRoute>
              <UserProfile />
            </UserPrivateRoute>
          }
        />
        <Route
          path="/user-profile-update"
          element={
            <UserPrivateRoute>
              <UserProfileUpdate />
            </UserPrivateRoute>
          }
        />
        

        {/* Organization Protected Routes */}
        <Route
          path="/org-dashboard"
          element={
            <OrgPrivateRoute>
              <OrgDashboard />
            </OrgPrivateRoute>
          }
        />
        <Route
          path="/events"
          element={
            <OrgPrivateRoute>
              <Events />
            </OrgPrivateRoute>
          }
        />
        <Route
          path="/todays-event"
          element={
            <OrgPrivateRoute>
              <TodaysEvent />
            </OrgPrivateRoute>
          }
        />
        <Route
          path="/org-organize-event"
          element={
            <OrgPrivateRoute>
              <OrgOrganizeEvent />
            </OrgPrivateRoute>
          }
        />
        <Route
          path="/collab-request"
          element={
            <OrgPrivateRoute>
              <CollabRequest />
            </OrgPrivateRoute>
          }
        />
        <Route
          path="/past-event-detail"
          element={
            <OrgPrivateRoute>
              <PastEventDetail />
            </OrgPrivateRoute>
          }
        />
        <Route
          path="/scheduled-donation"
          element={
            <OrgPrivateRoute>
              <ScheduledDonation />
            </OrgPrivateRoute>
        }
        />
        
        <Route
          path="/user-blood-availability-org"
          element={
            <OrgPrivateRoute>
              <UserBloodAvailability />
            </OrgPrivateRoute>
          }
        />
      
      </Routes>
    </Router>
  );
};

export default Navigation;