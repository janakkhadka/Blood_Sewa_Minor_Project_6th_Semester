import React,{useState, useEffect} from 'react'

import './BloodAvailability.css'

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import {provinceList, provinceHospitalList, hospitalBloodDataList} from '../LoginRegistration/DropDownList';

import { IoIosArrowDropdownCircle } from "react-icons/io";

import BackThreeD from '../LoginRegistration/3d'
import MyBarChart from '../Utils/MyBarChart';
import NavigationBar from '../Common/NavigationBar'
import { UserComponentNavbarRightLeft, UserComponentNavbarRightRight } from './UserNavbarComponent';
import { OrgDashboardNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgDashboard/OrgNavbarComponent'
import { useUserAuthToken, useOrgAuthToken } from '../../Logic/AuthKey';


function BloodAvailability() {

    const userAuthToken = useUserAuthToken();
    const orgAuthToken = useOrgAuthToken();


    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [hospitalOptions, setHospitalOptions] = useState([]);//list of hospital accordance to province hai

    const [hospitalBloodDataOptions, setHospitalBloodDataOptions] = useState([]); //hospital blood data ko lagi, hospital choose garesi tesko data halna lai

    const [selectedHospital, setSelectedHospital] = useState("")
    const handleHospitalChange = (selectedOption) => {
        setSelectedHospital(selectedOption);
        // setSelectedHospital(null);
    
        const selectedHospitalData = hospitalBloodDataList.find(
          (hospital) => hospital.name === selectedOption.label
        );
    
        const updatedHospitalBloodDataOptions = selectedHospitalData
          ? selectedHospitalData.bloodType
          : [];
    
        setHospitalBloodDataOptions(updatedHospitalBloodDataOptions); // Update the district options
      };

      //fetching scan gareko user haru(checkedin user list)
  useEffect(() => {
    if(!orgAuthToken){
      setError('No auth token found. Please log in');
      setLoading(false);
      return;
    }

    const fetchData = async () => {
      try {
        const response = await fetch(api+'events/'+todayEventData.slug+'/checkin/list/',{
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
        setCheckedInDonorList(Array.isArray(result.checked_in_users ) ? result.checked_in_users  : []);
        console.log(todayEventData.slug)

      }catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  },[todayEventData.slug, orgAuthToken]);


    

    


  return (
    <div className='blood-availability-wrapper'>
        <div className="syringe">
            <BackThreeD/>
        </div>
        {userAuthToken &&
          <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<UserComponentNavbarRightLeft/>}
          rightRightNav = {<UserComponentNavbarRightRight/>} 
          />
        }
        {orgAuthToken && 
          <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<OrgDashboardNavbarRightLeft/>}
          rightRightNav = {<OrgDashboardNavbarRightRight/>} 
        />
      }
        
        <div className="blood-availability">
            <form action="">
                <h1>Blood Inventory</h1>
                <span>Please choose the Hospital to see Blood Availability in the respective.</span>

                {/* <div className="drop-down-box" style={{marginTop:"18px"}}>
                    <Select
                        value = {selectedProvince}
                        onChange={handleProvinceChange}
                        options={provinceList}
                        styles={customStyles()}
                        placeholder="Koshi Province"
                        isSearchable={false}
                    />
                    <IoIosArrowDropdownCircle className='icon'/>
                </div> */}

                <div className="drop-down-box">
                    <Select
                        value = {selectedHospital}
                        onChange={handleHospitalChange}
                        options={hospitalOptions}
                        styles={customStyles()}
                        placeholder={
                       "Select a Hospital/Blood Bank"
                        }
                        isSearchable={false}
                    />
                    <IoIosArrowDropdownCircle className='icon'/>
                </div>
            </form>
            <div className="bar-chart">
                <MyBarChart barList ={hospitalBloodDataOptions}/>
            </div>
            
        </div>
        
    </div>
  )
}

export default BloodAvailability