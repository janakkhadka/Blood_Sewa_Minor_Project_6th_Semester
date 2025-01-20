import React, { useState, useEffect } from "react";
import "./ToggleTheme.css";

export default function ToggleTheme() {
    const [theme, setTheme] = useState(() => {
        const savedTheme = localStorage.getItem("theme");
        if (savedTheme) {
          return savedTheme;
        }
        const systemTheme = window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
        return systemTheme;
      });

    useEffect(() => {
        document.documentElement.setAttribute("data-theme", theme);
        localStorage.setItem("theme", theme);
      }, [theme]);

      console.log(localStorage.getItem("theme"));

  const toggleTheme = () => {
    setTheme((currentTheme) => {
      switch (currentTheme) {
        case "light":
          return "dark";
        case "dark":
          return "bloody";
        case "bloody":
          return "light";
        default:
          return "light";
      }
    });
  };

  return (
    <button onClick={toggleTheme} className={`toggle-button ${theme}`}>
      <span className="icon1 light-icon">☀️</span>
      <span className="icon1 dark-icon">🌙</span>
      <span className="icon1 bloody-icon">🩸</span>
      <span className="slider"></span>
    </button>
  );
}
