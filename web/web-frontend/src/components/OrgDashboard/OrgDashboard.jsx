import React, {useEffect, useState} from 'react'

import NavigationBar from '../Common/NavigationBar'
import './OrgDashboard.css'
import { OrgDashboardNavbarRightLeft, OrgDashboardNavbarRightRight } from './OrgNavbarComponent'
import BackThreeD from '../LoginRegistration/3d'

import { IoMdSquare } from "react-icons/io";
import { TbAxisX, TbAxisY } from "react-icons/tb";
import { IoIosArrowDropdownCircle } from "react-icons/io";

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';
import {bloodTypeList, } from "../Utils/DataList";

import { useNavigate } from 'react-router-dom';
import MyBarChart from '../Utils/MyBarChart'
import { useOrgAuthToken } from '../../Logic/AuthKey';
import {api} from '../../Logic/api'

import { format, set } from "date-fns";

function OrgDashboard() {
    //org details taneko
    const orgDetailsString = sessionStorage.getItem('orgDetails') || localStorage.getItem('orgDetails');
    let orgDetails = null;
    let orgName = "";
    
    try {
      orgDetails = orgDetailsString ? JSON.parse(orgDetailsString) : null;
    //   console.log(orgDetails);
      orgName = orgDetails.name;
    } catch (error) {
      console.error('Failed to parse orgDetails:', error);
    }
    


    const [barList, setBarList] = useState([]); 

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
  console.log(orgAuthToken)

  //alert message ko lagi
  const [isOnAlertLevel, setIsOnAlertLevel] = useState(false);
  const [lowStockBlood, setLowStockBlood] = useState([]);


  //update inventory ko lagi modal
  const [updateBloodInventory, setUpdateBloodInventory] = useState(false);

    
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  
    //blood request ko lagi modal
  const [requestBlood, setRequestBlood] = useState(false);
  const [bloodType, setBloodType] = useState("")
  const handleBloodTypeChange = (option) => {
    setBloodType(option);
  };
  const [pintValue, setPintValue] = useState("");

  const [filteredBloodRequestList, setFilteredBloodRequestList] = useState([]);

  const [donateBloodModal, setDonateBloodModal] = useState(false);
  const [pintValueDonate, setPintValueDonate] = useState("");
  const [orgNameToDonate, setOrgNameToDonate] = useState("");
  const [bloodGroupToDonate, setBloodGroupToDonate] = useState("");

  const [selectedRequestId, setSelectedRequestId] = useState(0);
  const handleDonateClick = (requestId) => {
    setSelectedRequestId(requestId)
    setDonateBloodModal(true);
  };



//inventory ko data taneko server bata
  useEffect(() => {
    if (!orgAuthToken) {
        setError('No auth token found. Please log in');
        setLoading(false);
        console.log('No auth token found. Please log in');
        return;
      }
    const fetchData = async () => {
        // console.log(orgAuthToken)

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
        console.log(result);
        const newBarList = Object.entries(result.inventory)
          .filter(([key]) =>
            ["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"].includes(key)
          )
          .map(([type, pint]) => ({ type, pint }));
        setBarList(newBarList);
        console.log("barList:"+newBarList);
        setAPositive(result.inventory["A+"]);
        setANegative(result.inventory["A-"]);
        setBPositive(result.inventory["B+"]);
        setBNegative(result.inventory["B-"]);
        setOPositive(result.inventory["O+"]);
        setONegative(result.inventory["O-"]);
        setABPositive(result.inventory["AB+"]);
        setABNegative(result.inventory["AB-"]);

        const lowStockBloodTypes = Object.entries(result.inventory)
            .filter(([bloodType, value]) => value <= 10)
            .map(([bloodType]) => bloodType);

            if (lowStockBloodTypes.length > 0) {
                setLowStockBlood(lowStockBloodTypes);
                setIsOnAlertLevel(true);
                console.log(lowStockBlood)
                console.log("Low stock blood types:", lowStockBloodTypes.join(", "));
              } else {
                console.log("All blood types have sufficient stock");
              }

        
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [orgAuthToken, updateBloodInventory]);


  //inventory update ko lagi
  const handleUpdateFunction = async (e) => {
    e.preventDefault();


    const inventory = {
        "A+": parseInt(aPositive) || 0,
        "A-": parseInt(aNegative) || 0,
        "B+": parseInt(bPositive) || 0,
        "B-": parseInt(bNegative) || 0,
        "AB+": parseInt(abPositive) || 0,
        "AB-": parseInt(abNegative) || 0,
        "O+": parseInt(oPositive) || 0,
        "O-": parseInt(oNegative) || 0,
    };

    try {
        const response = await fetch(api + 'blood-inventory/update/', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${orgAuthToken}`,
            },
            body: JSON.stringify(inventory),
        });

        if (response.ok) {
            const responseData = await response.json();
            // navigate('/org-dashboard');
            // alert('Inventory updated successfully!');
            window.location.reload();
            // setUpdateBloodInventory(false);
            // console.log('Server Response:', responseData);
        } else {
            console.log(response)
            console.error('Failed to update inventory');
            alert('Failed to update inventory');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while updating inventory');
    }
};


 //bulk request ko lagi post request
 const handleBloodRequestFunction = async (e) => {
    e.preventDefault();

    const request = {
            [bloodType.value]: parseInt(pintValue) || 0,
    };
    try {
        console.log('Request:', request);
        const response = await fetch(api + 'bulk-request/add/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${orgAuthToken}`,
            },
            body: JSON.stringify(request),
        });;

        if (response.ok) {
            const responseData = await response.json();
            // navigate('/org-dashboard');
            // alert('Inventory updated successfully!');
            window.location.reload();
            // setUpdateBloodInventory(false);
            // console.log('Server Response:', responseData);
            // console.log('Blood Request:', bloodType, pintValue);
        } else {
            console.log(response)
            console.error('Failed to request blood');
            alert('Failed to request blood');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while requesting blood');
    }
};



 //arule request gareko blood ko lagi get request
 useEffect(() => {
    if (!orgAuthToken) {
        setError('No auth token found. Please log in');
        setLoading(false);
        console.log('No auth token found. Please log in');
        return;
      }
    const fetchData = async () => {
      try {
        const response = await fetch(api+'bulk-requests', {
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
        const transformedFilteredRequests = Array.isArray(result)
      ? result
      .filter((data) => data.organization_name !== orgName)
      .map((data, index) => ({
          id: (index + 1).toString(),
          requestedBy: data.organization_name,
          bloodGroup: Object.keys(data.blood_request)[0],
          pintValue: data.blood_request[Object.keys(data.blood_request)[0]],
          date: data.date
        }))
      : [];
        // console.log('Transformed Events:', JSON.stringify(transformedRequests, null, 2));
  
    
        setFilteredBloodRequestList(transformedFilteredRequests);
        setPintValueDonate(transformedFilteredRequests[selectedRequestId].pintValue);
        setOrgNameToDonate(transformedFilteredRequests[selectedRequestId].requestedBy);
        setBloodGroupToDonate(transformedFilteredRequests[selectedRequestId].bloodGroup);

        
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  },[orgAuthToken]);


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
                    <button className="action-button" onClick={() => setRequestBlood(true)}>Urgent Blood Request</button>
                    <button className="action-button" onClick={() => navigate("/scheduled-donation")}>Scheduled Donation</button>
                    <button className="action-button" onClick={() => navigate("/events")}>Manage Events</button>
                    <button className="action-button" onClick={() => navigate("/user-blood-availability-org")}>Blood Inventory</button>
                    <button className="action-button" onClick={() => navigate("/search-donor")}>Find Donor</button>
                </section>

                <section className="blood-inventory-section">
                    <div className="h1-wrapper">
                        <h1>Blood Inventory</h1>
                        {isOnAlertLevel && (
                            <div className="alert-wrapper">
                                <h3 style={{fontSize:"18px"}} className='alert'>Alert!</h3>
                                <span>Blood Low on Stock: {lowStockBlood.join(',')}</span>
                            </div>
                        )}
                        
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
                        <MyBarChart className='bar-chart' barList={barList}/>
                    </div>
                </section>
            </div>
            <div className="bottom-section">
                <section className='requested-blood-wrapper'>
                    <div className="h1">
                        <h1>Blood Requested by Hospital/Blood Bank</h1>
                    </div>
                    <table border="0" style={{tableLayout: "fixed", width: "100%", borderCollapse: "collapse" }}>
                        <colgroup>
                        <col style={{ width: "16%" }} />
                        <col style={{ width: "20%" }} />
                        <col style={{ width: "16%" }} />  
                        <col style={{ width: "18%" }} />
                        <col style={{ width: "18%" }} />
                        {/* <col style={{ width: "12%" }} /> */}
                        </colgroup>
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Requested By</th>
                            <th>Blood Group</th>
                            <th>Pint Value</th>
                            <th>Donate</th>
                            {/* <th>Is Donated?</th> */}
                        </tr>
                        </thead>
                        <tbody>
                        {filteredBloodRequestList.map((data, index) => (
                            // console.log(data.date),

                            <tr key={index}>
                            <td>{format(data.date, "MMMM dd, yyyy")}</td>
                            <td>{data.requestedBy}</td>
                            <td>{data.bloodGroup}</td> {/* Format date */}
                            <td>{data.pintValue}</td>
                            <td>
                                <div className='donate-button-wrapper'>
                                    <button className='donate-button' onClick={()=>handleDonateClick(index)}>Donate</button>
                                </div>
                            </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </section>
            </div> 
        </div>

        {/* modal start for all */}
        
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
                            <button type='submit' onClick={handleUpdateFunction}  className="update-button">
                                Update
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        )}

        {requestBlood && (
            <div className="request-blood-wrapper">
                <div className="request-blood">
                    <div className="close-button">
                        <button onClick={() => setRequestBlood(false)}>X</button>
                    </div>
                    <h2 style={{color:"var(--secondary-text-color)"}}>Request Blood</h2>
                    <form>
                        <div className="input-box-wrapper">
                            <div className="drop-down-box">
                                <Select
                                value = {bloodType}
                                onChange={handleBloodTypeChange}
                                options={bloodTypeList}
                                styles={customStyles()}
                                placeholder="Select Blood Group"
                                isSearchable={false}
                                />
                                <IoIosArrowDropdownCircle className='icon'/>
                            </div>
                            <div className="pint-value">
                                <div className="input-box">
                                    <input type="text"
                                    value = {pintValue}
                                    onChange={(e) => setPintValue(e.target.value)}
                                    placeholder='Pint Value'/>
                                </div>
                            </div>
                        </div>
                        
                        <div className='request-button-wrapper'>
                            <button type='submit' onClick={handleBloodRequestFunction}  className="request-button">
                                Request
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        )}

        {/* yo chai org le blood donate garxa aru org lai, modal ho */}
        {donateBloodModal && (
            <div className="donate-blood-wrapper">
                <div className="donate-blood">
                    <div className="close-button">
                        <button onClick={() => setDonateBloodModal(false)}>X</button>
                    </div>
                    <h2 style={{color:"var(--secondary-text-color)"}}>Donate Blood</h2>
                    <form>
                        <div className="input-box-wrapper">
                            <span>Donate To: <span style={{fontWeight:"bold"}}>{orgNameToDonate}</span> </span>
                            <span>Blood Group: <span style={{fontWeight:"bold"}}>{bloodGroupToDonate}</span> </span>
                            <div className="pint-value">
                                <div className="input-box">
                                    <input type="text"
                                    value = {pintValueDonate}
                                    onChange={(e) => setPintValueDonate(e.target.value)}
                                    placeholder='Pint Value'/>
                                </div>
                            </div>
                        </div>
                        
                        <div className='donate-button-wrapper'>
                            <button type='submit'  className="donate-button">
                                Donate
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