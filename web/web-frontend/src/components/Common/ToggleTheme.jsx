import React, { useState } from "react";
import "./ToggleTheme.css";

export default function ToggleTheme() {
  const [theme, setTheme] = useState("light");
  localStorage.setItem('theme', theme);
  console.log(localStorage.getItem('theme'));

  const toggleTheme = () => {
    setTheme((currentTheme) => {
      switch (currentTheme) {
        case "light":
          return "dark";
        case "dark":
          return "custom";
        case "custom":
          return "light";
        default:
          return "light";
      }
    });
  };

  return (
    <button onClick={toggleTheme} className={`toggle-button ${theme}`}>
      <span className="icon light-icon">☀️</span>
      <span className="icon dark-icon">🌙</span>
      <span className="icon custom-icon">🎨</span>
      <span className="slider"></span>
    </button>
  );
}
