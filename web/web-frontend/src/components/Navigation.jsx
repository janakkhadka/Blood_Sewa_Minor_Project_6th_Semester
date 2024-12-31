
import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Login from './LoginRegistration/Login';
import RegistrationOrg from './LoginRegistration/RegistrationOrg';
import RegistrationUser from './LoginRegistration/RegistrationUser';
import LandingPage from './homepage/LandingPage';
import Why from './homepage/Why';
import AboutUs from './homepage/AboutUs';
import BloodDonorDashboard from './UserDashboard/UserDashboard'
import NavigationBar from './homepage/NavigationBar';
import BloodRequestForm from './BloodRequestForm';
import ScheduleDonation from './UserDashboard/ScheduleDonation';
import UserOrganizeEvent from './UserDashboard/UserOrganizeEvent';
import UserBloodAvailability from './UserDashboard/BloodAvailability';
import UserProfile from './UserDashboard/UserProfile';
import UserProfileUpdate from './UserDashboard/UserProfileUpdate';

const Navigation = () => {
    return (
        <Router>
          <Routes>
            <Route path="/land" element={<LandingPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/registrationorg" element={<RegistrationOrg />} />
            <Route path="/registrationuser" element={<RegistrationUser />} />
            <Route path="/aboutus" element={<AboutUs />} />
            <Route path="/why" element={<Why />} />

            <Route path ="/" element={<BloodDonorDashboard/>}/>
            <Route path ="/blood-request-form" element = {<BloodRequestForm/>}/>
            <Route path ="/schedule-donation" element = {<ScheduleDonation/>}/>
            <Route path ="/user-organize-event" element = {<UserOrganizeEvent/>}/>
            <Route path ="/user-blood-availability" element = {<UserBloodAvailability/>}/>
            <Route path ="/user-profile" element = {<UserProfile/>}/>
            <Route path ="/user-profile-update" element = {<UserProfileUpdate/>}/>
            {/* <Route path="/navigationbar" element={<NavigationBar />} /> */}
          </Routes>
        </Router>
      );
}

export default Navigation