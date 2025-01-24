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
    //org details taneko
    const orgDetailsString = sessionStorage.getItem('orgDetails') || localStorage.getItem('orgDetails');
    let orgDetails = null;
    
    try {
      orgDetails = orgDetailsString ? JSON.parse(orgDetailsString) : null;
      //console.log(orgDetails);
    } catch (error) {
      console.error('Failed to parse orgDetails:', error);
    }

  const [aPositive, setAPositive] = useState("");
  const [aNegative, setANegative] = useState("");
  const [bPositive, setBPositive] = useState("");
  const [bNegative, setBNegative] = useState("");
  const [abPositive, setABPositive] = useState("");
  const [abNegative, setABNegative] = useState("");
  const [oPositive, setOPositive] = useState("");
  const [oNegative, setONegative] = useState("");
  const orgAuthToken = useOrgAuthToken();
  const navigate = useNavigate();

  const [data, setData] = useState();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [updateBloodInventory, setUpdateBloodInventory] = useState(false);

  useEffect(() => {
    if (!orgAuthToken) {
        setError('No auth token found. Please log in');
        setLoading(false);
        console.log('No auth token found. Please log in');
        return;
      }
    const fetchData = async () => {
        console.log(orgAuthToken)

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

        

        const result = await response.json();
        console.log('Fetched Result:', JSON.stringify(result, null, 2));
        setData(result);
        console.log('result:', JSON.stringify(result, null, 2)); // Logs result in readable JSON format
        console.log('data:', JSON.stringify(data, null, 2));

        
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false); // Stop loading
      }
    };
    fetchData();
  }, [orgAuthToken]);
  useEffect(() => {
    if (data) {
      console.log('Updated data:', JSON.stringify(data, null, 2)); // Log data after state update
    }
  }, [data]);


//  if (loading) return <p>Loading...</p>;
//  if (error) return <p>Error: {error}</p>;  
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
                                <button className='action-button' onClick={()=>setUpdateBloodInventory(true)}>Update Inventory</button>
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
            <div className="update-inventory-wrapper">
                <div className="update-inventory">
                    <div className="close-button">
                        <button onClick={() => setUpdateBloodInventory(false)}>X</button>
                    </div>
                    <h2 style={{color:"var(--secondary-text-color)"}}>Update Inventory</h2>
                    <form>
                        <div className="blood-group-wrapper">
                            <div className="left-input-box">
                                <div className="blood-group">
                                    <label htmlFor="A+">A+</label>
                                    <div className="input-box">
                                        <input type="text"
                                        value = {aPositive}
                                        onChange={(e) => setAPositive(e.target.value)}
                                        placeholder=''/>
                                    </div>
                                </div>
                                <div className="blood-group">
                                <label htmlFor="A-">A-</label>
                                <div className="input-box">
                                    <input type="text"
                                    value = {aNegative}
                                    onChange={(e) => setANegative(e.target.value)}
                                    placeholder=''/>
                                </div>
                                </div>
                                <div className="blood-group">
                                    <label htmlFor="B+">B+</label>
                                    <div className="input-box">
                                        <input type="text"
                                        value = {bPositive}
                                        onChange={(e) => setBPositive(e.target.value)}
                                        placeholder=''/>
                                    </div>
                                </div>
                                <div className="blood-group">
                                    <label htmlFor="B-">B-</label>
                                    <div className="input-box">
                                        <input type="text"
                                        value = {bNegative}
                                        onChange={(e) => setBNegative(e.target.value)}
                                        placeholder=''/>
                                    </div>
                                </div>
                            </div> 
                            <div className="right-input-box">
                                <div className="blood-group">
                                    <label htmlFor="AB+">AB+</label>
                                    <div className="input-box">
                                        <input type="text"
                                        value = {abPositive}
                                        onChange={(e) => setABPositive(e.target.value)}
                                        placeholder=''/>
                                    </div>
                                </div>
                                <div className="blood-group">
                                <label htmlFor="AB-">AB-</label>
                                <div className="input-box">
                                    <input type="text"
                                    value = {abNegative}
                                    onChange={(e) => setABNegative(e.target.value)}
                                    placeholder=''/>
                                </div>
                                </div>
                                <div className="blood-group">
                                    <label htmlFor="O+">O+</label>
                                    <div className="input-box">
                                        <input type="text"
                                        value = {oPositive}
                                        onChange={(e) => setOPositive(e.target.value)}
                                        placeholder=''/>
                                    </div>
                                </div>
                                <div className="blood-group">
                                    <label htmlFor="O-">O-</label>
                                    <div className="input-box">
                                        <input type="text"
                                        value = {oNegative}
                                        onChange={(e) => setONegative(e.target.value)}
                                        placeholder=''/>
                                    </div>
                                </div>
                            </div>   
                        </div>
                        <div className='update-button-wrapper'>
                            <button type='submit'  className="update-button">
                                Update
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        )}
    </div>
  )
}

export default OrgDashboard