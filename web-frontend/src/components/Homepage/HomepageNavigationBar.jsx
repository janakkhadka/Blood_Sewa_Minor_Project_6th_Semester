import React from "react";
import "./HomepageNavigationBar.css";

import { Link } from "react-router-dom";
import { HashLink } from "react-router-hash-link";

const HomepageNavigationBar = () => {
  return (
    <div className="navigation-bar">
      <nav className="navbar">
        <div className="navbar-left">
          <h1>Blood Sewa</h1>
        </div>
        <div className="navbar-right">
            <HashLink smooth to="#home">Home</HashLink>
            <HashLink smooth to="#why">Why Donate</HashLink>
            <HashLink smooth to="#about-us">About Us</HashLink>
            <Link to="/login">Login</Link>
        </div>
      </nav>
    </div>
  );
};

export default HomepageNavigationBar;
