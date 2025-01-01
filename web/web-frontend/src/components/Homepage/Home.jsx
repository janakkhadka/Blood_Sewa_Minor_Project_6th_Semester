import React from "react";

import './Home.css'

import { FaHeart } from "react-icons/fa6";
import { MdOutlineAccessTimeFilled, MdOutlineDone, MdBloodtype  } from "react-icons/md";
import { HiUserGroup } from "react-icons/hi";
import { RxCross2 } from "react-icons/rx";

import CustomSlider from '../Utils/CustomImageSlider/CustomSlider'
import { imageList } from "../Utils/CustomImageSlider/ImageList";



function Home() {
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
            <div className="left-bottom-criteria">
              <div className="h3">
                <MdBloodtype className="icon"/>
                <h3>Eligibility Criteria</h3>
              </div>
              <ul style={{listStyleType:"none"}}>
                <div className="criteria-list">
                  <MdOutlineDone />
                  <li>Be at least 17 years old</li>
                </div>
                <div className="criteria-list">
                  <MdOutlineDone />
                  <li>Weigh at least 110 pounds</li>
                </div>
                <div className="criteria-list">
                  <MdOutlineDone />
                  <li>Be in good health</li>
                </div>
                <div className="criteria-list">
                  <RxCross2/>
                  <li>Have a recent tattoo (less than 3 months old)</li>
                </div>
                <div className="criteria-list">
                  <RxCross2/>
                  <li>Have a cold or flu</li>
                </div>
              </ul>
            </div>
            <div className="contact-us">

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