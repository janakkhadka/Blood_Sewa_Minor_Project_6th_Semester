import React from "react";
import "./NavigationBar.css";

import { Link } from "react-router-dom";
import { HashLink } from "react-router-hash-link";

const HomepageNavigationBar = ({
  titleNav,rightLeftNav,rightRightNav}) => {
  return (
    <div className="navigation-bar">
      <nav className="navbar">
        <div className="navbar-left">
          <h1>{titleNav}</h1>
        </div>
        <div className="navbar-right">
            {rightLeftNav}
            {rightRightNav}
        </div>
      </nav>
    </div>
  );
};

export default HomepageNavigationBar;
