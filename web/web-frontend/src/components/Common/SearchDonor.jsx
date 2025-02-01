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
    const [donorData, setDonorData] = useState();
    const [donorList, setDonorList] = useState([]);

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

    //   const [searchBasis, setSearchBasis] = useState("bloodGroup")
    // const handleChangeSearchBasis = (event) => {
    //     setSearchBasis(event.target.value);
    //   };

    const [isBloodGroupActive, setBloodGroupActive] = useState(false);
    const [isDistrictActive, setDistrictActive] = useState(false);
    const [isProvinceActive, setProvinceActive] = useState(false);


  //donor list lai fetch gareko
  useEffect(() => {
    if (!orgAuthToken) {
        setError('No auth token found. Please log in');
        setLoading(false);
        console.log('No auth token found. Please log in');
        return;
      }
      console.log('Org Auth Token:', orgAuthToken);
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
        const transformedDonorData = result.map((data, index) => ({
          id: (index + 1).toString(),
          title: data.name,
          phoneNumber: data.phone_number,
          bloodGroup: data.blood_group,
          district: data.district,
          province: data.province,
          age: data.age,
        }));
        console.log('Transformed Donor:', JSON.stringify(transformedDonorData, null, 2));
        setDonorList(transformedDonorData);
        console.log('Fetched Result:', donorData);
        setDonorData(result);
        console.log('Data Upcoming:', donorList);
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
    if (donorData) {
      // Assuming `dataUpcoming` is the response
      const transformedDonorData = donorData.map((data, index) => ({
        id: (index + 1).toString(),
        title: data.name,
        phoneNumber: data.phone_number,
        bloodGroup: data.blood_group,
        district: data.district,
        province: data.province,
        age: data.age,
      }));
      console.log('Transformed Events:', JSON.stringify(transformedDonorData, null, 2));

  
      setDonorList(transformedDonorData);
      console.log('Fetched Result:', donorList);
    }
  }, [donorData]);

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

                    <div className="button-container">
                      <button
                        className={isBloodGroupActive ? "button active" : "button"}
                        onClick={() => setBloodGroupActive(!isBloodGroupActive)}
                      >
                        Blood Group
                      </button>

                      <button
                        className={isDistrictActive ? "button active" : "button"}
                        onClick={() => setDistrictActive(!isDistrictActive)}
                      >
                        District
                      </button>

                      <button
                        className={isProvinceActive ? "button active" : "button"}
                        onClick={() => setProvinceActive(!isProvinceActive)}
                      >
                        Province
                      </button>
                    </div>
                    {/* <div className="search-basis-wrapper">
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
                      
                    </div> */}
                <div className="blood-request-submit-button">
                    <button type="submit" >Search</button>
                </div>
                
            </form>
        </div>
    </div>
  )
}

export default SearchDonor