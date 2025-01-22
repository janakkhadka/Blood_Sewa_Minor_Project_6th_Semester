import React,{useState} from "react";

import './Home.css'

import { FaHeart, FaSyringe, FaGlassWater } from "react-icons/fa6";
import { MdOutlineAccessTimeFilled, MdDone, MdBloodtype } from "react-icons/md";
import { HiUserGroup } from "react-icons/hi";
import { RxCross2 } from "react-icons/rx";
import { GiChickenOven } from "react-icons/gi";
import { MdOutlineSmokeFree } from "react-icons/md";
import { FaRunning, FaHandHoldingWater } from "react-icons/fa";
import { IoIosArrowDropdownCircle } from "react-icons/io";

import Select from 'react-select';
import customStyles from '../LoginRegistration/ReactSelectStyle';


import CustomSlider from '../Utils/CustomImageSlider/CustomSlider'
import { imageList } from "../Utils/CustomImageSlider/ImageList";
import { bloodInfoList, bloodTypeList, } from "../Utils/DataList";
import LocalStorage from "../Common/LocalStorage";

function Home() {


  const [bloodType, setBloodType] = useState("")
  const [bloodInfo, setBloodInfo] = useState("")
  const handleBloodTypeChange = (option) => {
    setBloodType(option);

    const selectedBloodInfo = bloodInfoList.find(
      (bloodInfo) => option.value === bloodInfo.bloodType
    );

    const updatedBloodInfo = selectedBloodInfo
    ? selectedBloodInfo.canDonate
    : "";

    setBloodInfo(updatedBloodInfo)
  };
  

  return (
    <div className="home-wrapper">
      <div className="home-section">
        <div className="home-left-section">
          <section className="left-top">
            <h1>Welcome to Blood Sewa</h1>
            <div className="typing-container">
              Donate Blood, Save Life!
            </div>
          </section>
          <section className="left-bottom">
            <div className="blood-info-wrapper">
              <div className="h3">
                <MdBloodtype className="icon"/>
                <h3>Blood Type Info</h3>
              </div>
              <div className="drop-down-box">
                <Select
                  value = {bloodType}
                  onChange={handleBloodTypeChange}
                  options={bloodTypeList}
                  styles={customStyles({optionTextColor:'rgb(160, 25, 25)'})}
                  placeholder="Select Blood Group"
                  isSearchable={false}
                />
                <IoIosArrowDropdownCircle className='icon'/>
              </div>
              <div className="info-show">
                <span style={{fontSize:"15px"}}>{bloodInfo}</span>
              </div>
            </div>
            <div className="eligibility-wrapper">
              <div className="h3">
                <FaSyringe className="icon"/>
                <h3>Eligibility Criteria</h3>
              </div>
              <div className="eligibility-message">
                <ul style={{listStyleType:"none"}}>
                  <div className="eligibility-list">
                    <MdDone/>
                    <li>Be at least 17 years old</li>
                  </div>
                  <div className="eligibility-list">
                    <MdDone/>
                    <li>Weigh at least 110 pounds</li>
                  </div>
                  <div className="eligibility-list">
                    <MdDone/>
                    <li>Be in good health</li>
                  </div>
                  <div className="eligibility-list">
                    <RxCross2/>
                    <li>Have a recent tattoo (less than 3 months old)</li>
                  </div>
                  <div className="eligibility-list">
                    <RxCross2/>
                    <li>Have a cold or flu</li>
                  </div>
                </ul>
              </div>
            </div>
            <div className="blood-health-wrapper">
              <div className="h3">
                <MdBloodtype className="icon"/>
                <h3>Blood Health Overview</h3>
              </div>
              <div className="health-message">
                <ul style={{listStyleType:"none"}}>
                  <div className="health-list">
                    <FaRunning/>
                    <li>Exercise regularly to improve blood flow</li>
                  </div>
                  <div className="health-list">
                    <MdOutlineSmokeFree/>
                    <li>Avoid smoking and limit alcohol consumption</li>
                  </div>
                  <div className="health-list">
                    <FaHandHoldingWater/>
                    <li>Donate blood periodically</li>
                  </div>
                  <div className="health-list">
                    <FaGlassWater/>
                    <li>Stay hydrated to maintain blood volume</li>
                  </div>
                  <div className="health-list">
                    <GiChickenOven/>
                    <li>Eat iron-rich foods like red meat, legumes</li>
                  </div>
                </ul>
              </div>
            </div>
          </section>
        </div>
        <div className="home-right-section">
          <section className="right-top">
            <div className="image-slider">
              <CustomSlider>
                {imageList.map((image, index) => {
                  return <img key={index} src={image.src} alt={image.alt} />;
                })}
              </CustomSlider>
            </div>
          </section>
          <section className="right-bottom">
            <h3>Benefits of Donating Blood</h3>
            <div className="message-wrapper">
              <section className="message">
                <FaHeart className="message-icon"/>
                <h3>Save Lives</h3>
                <span>One donation can save up to three lives.</span>
              </section>
              <section className="message">
                <MdOutlineAccessTimeFilled className="message-icon"/>
                <h3>Quick Process</h3>
                <span>The donation process takes only about an hour.</span>
              </section>
              <section className="message">
                <HiUserGroup className="message-icon"/>
                <h3>Help Community</h3>
                <span>Support your local community's health needs.</span>
              </section>
            </div>
          </section>
        </div>
      </div>
    </div>
  )
}

export default Home