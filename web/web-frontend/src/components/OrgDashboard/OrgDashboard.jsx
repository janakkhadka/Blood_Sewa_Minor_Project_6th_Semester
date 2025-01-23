import React, {useEffect, useState} from 'react'

import NavigationBar from '../Common/NavigationBar'
import './OrgDashboard.css'
import { OrgDashboardNavbarRightLeft, OrgDashboardNavbarRightRight } from './OrgNavbarComponent'
import BackThreeD from '../LoginRegistration/3d'

import { IoMdSquare } from "react-icons/io";
import { TbAxisX, TbAxisY } from "react-icons/tb";

import { useNavigate } from 'react-router-dom';
import MyBarChart from '../Utils/MyBarChart'
import { useOrgAuthToken } from '../../Logic/AuthKey';
import {api} from '../../Logic/api'

function OrgDashboard() {
    const orgAuthToken = useOrgAuthToken();
    const navigate = useNavigate();

    const [data, setData] = useState(null); // State to store fetched data
  const [loading, setLoading] = useState(true); // Loading state
  const [error, setError] = useState(null); // Error state
  console.log(orgAuthToken)

  const [updateBloodInventory, setUpdateBloodInventory] = useState(null);
  const handleUpdateBloodInventory = () => {
    setUpdateBloodInventory(true);
  }

  useEffect(() => {
    const fetchData = async () => {
      if (!orgAuthToken) {
        setError('No auth token found. Please log in.');
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(api+'my-blood-inventory/', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${orgAuthToken}`, // Attach the token
          },
        });

        if (!response.ok) {
            const errorResponse = await response.json();
            console.log(errorResponse);
          throw new Error(`Error: ${response.status}`);
        }

        const result = await response.json(); // Parse JSON response
        setData(result); // Store data in state
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false); // Stop loading
      }
    };

    fetchData();
  }, []);


// if (loading) return <p>Loading...</p>;
// if (error) return <p>Error: {error}</p>;  
  return (
    <div className="org-dashboard-wrapper">
        <div className="syringe">
          <BackThreeD/>
        </div>
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgDashboardNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />

        <div className="org-dashboard">
            <div className="top-section">
                <section className='quick-actions'>
                    <h1>Quick Actions!</h1>
                    <button className="action-button" onClick={() => navigate("/blood-request-form",{ state: { identifier: 2 }} )}>Urgent Blood Request</button>
                    <button className="action-button" onClick={() => navigate("/scheduled-donation")}>Scheduled Donation</button>
                    <button className="action-button" onClick={() => navigate("/events")}>Manage Events</button>
                    <button className="action-button" onClick={() => navigate("/user-blood-availability-org")}>Blood Inventory</button>
                    <button className="action-button" onClick={() => navigate("/search-donor-org")}>Find Donor</button>
                </section>

                <section className="blood-inventory-section">
                    <div className="h1-wrapper">
                        <h1>Blood Inventory</h1>
                        <div className="alert-wrapper">
                            <h3 className='alert'>Alert!</h3>
                        </div>
                    </div>
                    <div className="bar-chart-wrapper">
                        <div className="chart-info-wrapper">
                            <div className="chart-info">
                                <TbAxisX/>
                                <span>X-Axis: Blood Group</span>
                            </div>
                            <div className="chart-info">
                                <TbAxisY/>
                                <span>Y-Axis: Pint Value</span>
                            </div>
                            <div className="chart-info">
                                <IoMdSquare style={{color:"#8B0000"}}/>
                                <span>Pint value range 0-10</span>
                            </div>
                            <div className="chart-info">
                                <IoMdSquare style={{color:"#ff9510"}}/>
                                <span>Pint value range 11-25</span>
                            </div>
                            <div className="chart-info">
                                <IoMdSquare style={{color:"rgb(25, 160, 25)"}}/>
                                <span>Pint value greater than 25</span>
                            </div>
                            <div style={{marginTop:"20px"}}>
                                <button className='action-button' onClick={handleUpdateBloodInventory}>Update Inventory</button>
                            </div>
                        </div>
                        <MyBarChart className='bar-chart'/>
                    </div>
                </section>
            </div>
            <div className="bottom-section">
                
            </div> 
        </div>
        {updateBloodInventory && (
            <div className="modal">
                <div className="modal-content">
                    <div className="close-button">
                        <button onClick={() => setUpdateBloodInventory(false)}>X</button>
                    </div>
                    <div className="activate-button">
                    <button>
                        Update
                    </button>
                    </div>
                </div>
            </div>
        )}
        <div className="update-inventory-wrapper">
            <div className="update-inventory">

            </div>
        </div>
    </div>
  )
}

export default OrgDashboard