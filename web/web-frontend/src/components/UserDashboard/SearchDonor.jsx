import React,{useState} from 'react'

import './SearchDonor.css'

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';

import 'react-date-picker/dist/DatePicker.css';
import '../LoginRegistration/Calender.css';

import {provinceList, ProvinceDistrictList, bloodGroupList} from '../LoginRegistration/DropDownList';

import { IoIosArrowDropdownCircle } from "react-icons/io";


import BackThreeD from '../LoginRegistration/3d'
import NavigationBar from '../Common/NavigationBar'
import { UserComponentNavbarRightLeft, UserComponentNavbarRightRight } from './UserNavbarComponent';
import { OrgDashboardNavbarRightLeft, OrgDashboardNavbarRightRight } from '../OrgDashboard/OrgNavbarComponent'  

function SearchDonor() {
    const userAuthToken1  = localStorage.getItem("userAuthToken");
    const userAuthToken2 = sessionStorage.getItem("userAuthToken");
    const orgAuthToken1 = localStorage.getItem("orgAuthToken");
    const orgAuthToken2 = sessionStorage.getItem("orgAuthToken");

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

  return (
    <div className='donor-search-wrapper'>
        <div className="syringe">
            <BackThreeD/>
        </div>
        {(userAuthToken1 || userAuthToken2) && 
          <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<UserComponentNavbarRightLeft/>}
          rightRightNav = {<UserComponentNavbarRightRight/>} 
          />
        }
        {(orgAuthToken1 || orgAuthToken2) && 
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
                  options={bloodGroupList}
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