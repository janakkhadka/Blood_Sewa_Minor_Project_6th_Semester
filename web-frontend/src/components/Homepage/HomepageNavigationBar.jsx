import React from "react";
import "./HomepageNavigationBar.css";

import { Link } from "react-router-dom";
import { HashLink } from "react-router-hash-link";

const HomepageNavigationBar = () => {
  return (
    <div className="homepage-navigation-bar">
      <nav className="homepage-navbar">
        <div className="homepage-navbar-left">
          <h1>Blood Sewa</h1>
        </div>
        <div className="homepage-navbar-right">
            <div className="homepage-navbar-right-left">
                <HashLink smooth to="#home">Home</HashLink>
                <HashLink smooth to="#why">Why Donate</HashLink>
                <HashLink smooth to="#about-us">About Us</HashLink>
            </div>
            <div className="homepage-navbar-right-right">
                <Link to="/login">Login</Link>
            </div> 
        </div>
      </nav>
    </div>
  );
};

export default HomepageNavigationBar;
