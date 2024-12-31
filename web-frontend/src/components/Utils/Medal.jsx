import React from "react";

import { PiMedalFill } from "react-icons/pi";
import { MdBloodtype } from "react-icons/md";

function Medal({ donationCount }) {
  // Define milestones and their corresponding medals
  const milestones = [
    { min: 0, max: 10, medal: "bronze.png", title:"Bronze" },
    { min: 10, max: 25, medal: "silver.png", title:"Silver" },
    { min: 25, max: 50, medal: "gold.png", title:"Gold" },
    { min: 50, max: 75, medal: "platinum.png", title:"Platinum" },
    { min: 75, max: 100, medal: "diamond.png", title:"Diamond" },
    { min: 100, max: 200, medal: "heroic.png", title:"Heroic" },
  ];

  // Find the current milestone range
  const currentMilestone = milestones.find(
    (milestone) =>
      donationCount >= milestone.min && donationCount < milestone.max
  );

  // Calculate progress within the current milestone
  const range =
    currentMilestone.max === Infinity
      ? currentMilestone.max - currentMilestone.min
      : currentMilestone.max - currentMilestone.min;

  const progress = range > 0
    ? ((donationCount - currentMilestone.min) / range) * 100
    : 100;

  console.log("Donation Count:", donationCount);
  console.log("Range:", range);
  console.log("Progress:", progress);

  return (
    <div className="medal-wrapper">
        <div className="medal-left-section">
            <div className="icon-info-wrapper">
                <PiMedalFill/>
                <span>Current Badge: <span style={{fontWeight:"Bold"}}>{currentMilestone.title}</span> </span>
            </div>
            <div className="progress-percentage">
                <span style={{fontSize:"15px"}}>Progress to next milestone</span>
                <span style={{fontWeight:"Bold"}}>{Math.round(progress)}%</span>
            </div>
            <div className="progress-bar-wrapper">
                <div className="progress-bar-background">
                    <div className="progress-bar-foreground" style={{width: `${progress}%`}}>
                        
                    </div>
                </div>
            </div>
            <div className="icon-info-wrapper">
                <MdBloodtype/>
                <span>Blood Type: <span style={{fontWeight:"Bold"}}>A+</span></span>
            </div>
        </div>
      <div className="medal-image">
        <img src={currentMilestone.medal}/>
      </div>
      
    </div>
  );
}

export default Medal;
