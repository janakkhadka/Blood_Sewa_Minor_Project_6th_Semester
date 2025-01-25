import React,{useState, useEffect} from 'react'

import './ScheduledDonation.css'
import {events, pastEvents} from '../UserDashboard/DummyData'
import { format } from "date-fns";

import { TiTick, TiTimes } from "react-icons/ti";

import NavigationBar from '../Common/NavigationBar'
import { OrgComponentNavbarRightLeft, OrgDashboardNavbarRightRight } from './OrgNavbarComponent'
import BackThreeD from '../LoginRegistration/3d'

import { useOrgAuthToken } from '../../Logic/AuthKey';
import {api} from '../../Logic/api';

function ScheduledDonation() {
  const orgAuthToken = useOrgAuthToken();

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [scheduledDonationData, setScheduledDonationData] = useState();
  const [scheduledDonationList, setScheduledDonationList] = useState([]);
  
  //scheduled donation data tanna ko lagi
  useEffect(() => {
    if (!orgAuthToken) {
        setError('No auth token found. Please log in');
        setLoading(false);
        console.log('No auth token found. Please log in');
        return;
      }
    const fetchData = async () => {
      try {
        const response = await fetch(api+'my-organization-bookings/', {
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
        const transformedBookings = result.map((data, index) => ({
          id: (index + 1).toString(),
          scheduledBy: data.user_name,
          date: data.booking_date,
          shift: data.shift,
          phoneNumber: data.user_phone_number,
          bloodGroup: data.user_blood_group,
        }));
        console.log('Transformed Events:', JSON.stringify(transformedBookings, null, 2));
  
    
        setScheduledDonationList(transformedBookings);
        console.log('Fetched Result:', scheduledDonationList);
        setScheduledDonationData(result);
        // console.log('result:', JSON.stringify(result, null, 2));
        // console.log('data:', JSON.stringify(dataUpcoming, null, 2));

        
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  },[orgAuthToken]);



  //aako data lai rakheko
  const shiftMapping = {
    evening: 'Evening (3:00 PM - 6:00 PM)',
    afternoon: 'Afternoon (12:00 PM - 3:00 PM)',
    morning: 'Morning (9:00 AM - 12:00 PM)',
  };
  useEffect(() => {
    if (scheduledDonationData) {
      // Assuming `dataUpcoming` is the response
      const transformedBookings = scheduledDonationData.map((data, index) => ({
        id: (index + 1).toString(),
        scheduledBy: data.user_name,
        date: data.booking_date,
        shift: shiftMapping[data.shift] || 'Unknown shift',
        phoneNumber: data.user_phone_number,
        bloodGroup: data.user_blood_group,
      }));
      console.log('Transformed Events:', JSON.stringify(transformedBookings, null, 2));

  
      setScheduledDonationList(transformedBookings);
      console.log('Fetched Result:', scheduledDonationList);
    }
  }, [scheduledDonationData]);

  // if (loading) return <p>Loading...</p>;
  // if (error) return <p>Error: {error}</p>;




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
            <h1>Scheduled Donation</h1>
          </div>
          <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
            <colgroup>
              <col style={{ width: "16%" }} />
              <col style={{ width: "20%" }} />
              <col style={{ width: "22%" }} />  
              <col style={{ width: "18%" }} />
              <col style={{ width: "18%" }} />
              {/* <col style={{ width: "12%" }} /> */}
            </colgroup>
            <thead>
              <tr>
                <th>Date</th>
                <th>Scheduled By</th>
                <th>Time Shift</th>
                <th>Blood Group</th>
                <th>Phone</th>
                {/* <th>Is Donated?</th> */}
              </tr>
            </thead>
            <tbody>
              {scheduledDonationList.map((data, index) => (
                <tr key={index}>
                  <td>{format(data.date, "MMMM dd, yyyy")}</td>
                  <td>{data.scheduledBy}</td>
                  <td>{data.shift}</td> {/* Format date */}
                  <td>{data.bloodGroup}</td>
                  <td  className='table-data'>{data.phoneNumber}</td>
                  {/* <td>
                    <div className="decesion-button">
                      <button><TiTick/></button>
                      <button><TiTimes /></button>
                    </div>
                  </td> */}
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </div>
    </div>
  )
}


export default ScheduledDonation