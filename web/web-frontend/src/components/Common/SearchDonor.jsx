import React,{useState, useEffect} from 'react'

import './SearchDonor.css'

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import 'react-date-picker/dist/DatePicker.css';
import '../LoginRegistration/Calender.css';

import {provinceList, ProvinceDistrictList, bloodGroupList1} from '../LoginRegistration/DropDownList';

import { IoIosArrowDropdownCircle } from "react-icons/io";


import BackThreeD from '../LoginRegistration/3d'
import NavigationBar from './NavigationBar'
import { UserComponentNavbarRightLeft, UserComponentNavbarRightRight } from '../UserDashboard/UserNavbarComponent';
import { OrgDashboardNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgDashboard/OrgNavbarComponent'

import { useUserAuthToken, useOrgAuthToken } from '../../Logic/AuthKey';
import {api} from '../../Logic/api';

function SearchDonor() {

    const userAuthToken = useUserAuthToken();
    const orgAuthToken = useOrgAuthToken();

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [pastEventData, setPastEventData] = useState();
    const [pastEventList, setPastEventList] = useState([]);

    const [bloodType, setBloodType] = useState("")
    const handleBloodTypeChange = (option) => {
      setBloodType(option);
    }

    const [districtOptions, setDistrictOptions] = useState([]);
    const [selectedProvince, setSelectedProvince] = useState('')
    const handleProvinceChange = (selectedOption) => {
        setSelectedProvince(selectedOption);
        setSelectedDistrict(null);
    
        const selectedProvinceData = ProvinceDistrictList.find(
          (province) => province.label === selectedOption.label
        );
    
        const updatedDistrictOptions = selectedProvinceData
          ? selectedProvinceData.options
          : [];
    
        setDistrictOptions(updatedDistrictOptions); // Update the district options
      };

      const [selectedDistrict, setSelectedDistrict] = useState("")
      const handleDistrictChange = (option) => {
          setSelectedDistrict(option);
      };

      const [searchBasis, setSearchBasis] = useState("bloodGroup")
    const handleChangeSearchBasis = (event) => {
        setSearchBasis(event.target.value);
      };


  //past event lai rakheko
  useEffect(() => {
    if (!orgAuthToken) {
        setError('No auth token found. Please log in');
        setLoading(false);
        console.log('No auth token found. Please log in');
        return;
      }
    const fetchData = async () => {
      try {
        const response = await fetch(api+'user/blood-group/?blood_group=A%2B', {
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
        const transformedEvents = result.map((event, index) => ({
          id: (index + 1).toString(),
          title: event.name,
          date: event.date,
          location: event.location,
          description: event.description,
        }));
        console.log('Transformed Events:', JSON.stringify(transformedEvents, null, 2));
        setPastEventList(transformedEvents);
        console.log('Fetched Result:', pastEventList);
        setPastEventData(result);
        console.log('Data Upcoming:', pastEventData);
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
  useEffect(() => {
    if (pastEventData) {
      // Assuming `dataUpcoming` is the response
      const transformedEvents = pastEventData.map((event, index) => ({
        id: (index + 1).toString(),
        title: event.name,
        date: event.date,
        location: event.location,
        description: event.description,
      }));
      console.log('Transformed Events:', JSON.stringify(transformedEvents, null, 2));

  
      setPastEventList(transformedEvents);
      console.log('Fetched Result:', pastEventList);
    }
  }, [pastEventData]);

  return (
    <div className='donor-search-wrapper'>
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
        
        <div className="donor-search">
            <form action="">
                <h1>Search Donor</h1>

                <div className="drop-down-box">
                <Select
                  value = {bloodType}
                  onChange={handleBloodTypeChange}
                  options={bloodGroupList1}
                  styles={customStyles()}
                  placeholder="Select Blood Group"
                  isSearchable={false}
                />
                <IoIosArrowDropdownCircle className='icon'/>
              </div>
                <div className="drop-down-box">
                        <Select
                            value = {selectedProvince}
                            onChange={handleProvinceChange}
                            options={provinceList}
                            styles={customStyles()}
                            placeholder="Province"
                            isSearchable={false}
                        />
                        <IoIosArrowDropdownCircle className='icon'/>
                    </div>

                    <div className="drop-down-box">
                        <Select
                            value = {selectedDistrict}
                            onChange={handleDistrictChange}
                            options={districtOptions}
                            styles={customStyles()}
                            placeholder={
                            selectedProvince ? "Select a District" : "Select a Province first"
                            }
                            isDisabled={!selectedProvince}
                            isSearchable={false}
                        />
                        <IoIosArrowDropdownCircle className='icon'/>
                    </div>
                    <div className="search-basis-wrapper">
                      Search on the basis of :
                      <div className="search-basis-option">
                      <div>
                          <input type="radio"
                              name="search-basis"
                              value="bloodGroup"
                              checked={searchBasis === "bloodGroup"}
                              onChange={handleChangeSearchBasis} 
                              />
                          <label htmlFor="group" className='bloodGroup'>
                            Blood Group only
                          </label>
                        </div>
                        <div>
                          <input type="radio"
                              name="search-basis"
                              value="province"
                              checked={searchBasis === "province"}
                              onChange={handleChangeSearchBasis} 
                              />
                          <label htmlFor="province" className='province'>
                            Blood Group and Province
                          </label>
                        </div>
                        <div>
                          <input type="radio"
                              name="search-basis"
                              value="district"
                              checked={searchBasis === "district"}
                              onChange={handleChangeSearchBasis} 
                              />
                          <label htmlFor="district" className='district'>
                            Blood Group and District
                          </label>
                        </div>
                      </div>
                      
                    </div>
                <div className="blood-request-submit-button">
                    <button type="submit" >Search</button>
                </div>
                
            </form>
        </div>
    </div>
  )
}

export default SearchDonor