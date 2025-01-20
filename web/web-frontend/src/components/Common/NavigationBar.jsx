import React from "react";
import "./NavigationBar.css";
import ToggleTheme from "./ToggleTheme";


const NavigationBar = ({ titleNav, rightLeftNav, rightRightNav }) => {
  return (
    <div className="navigation-bar">
      <nav className="navbar">
        <div className="navbar-left">
          <h1>{titleNav}</h1>
        </div>
        <div className="navbar-right">
            {rightLeftNav}
            <div className="right-right">
              {rightRightNav}
              <ToggleTheme/>
            </div>
            
        </div>
      </nav>
    </div>
  );
};

export default NavigationBar;

// yo chai theme toggle ko lagi ho

