import React,{useState} from 'react';
import './LoginRegistration.css';

import { FaLock} from "react-icons/fa";
import { IoMdMail } from "react-icons/io";

import BackThreeD from './3d'



const Login = ({ switchToRegister }) => {



    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent page reload
        console.log("Email:", email);
        console.log("Password:", password);
      };
  return (
    <div className="wrapper">
        <div className="background">
            <BackThreeD/>
        </div>
        <div className="form-box-login login">
            <div className="login-section">
                <h1>Blood Sewa</h1>
                <h3>Donate Blood, Save Life . . .</h3>
            </div>
            <div className="login-form">
                <form onSubmit={handleSubmit}>
                    <h1>Login</h1>
                    <div className="input-box">
                        <input type="email"
                        placeholder='Email' require/>
                        <IoMdMail className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="password"
                        placeholder='Password' require/>
                        <FaLock className="icon"/>
                    </div>

                    <div className="remember-forgot">
                        <label>
                            <input type="checkbox" />
                            Remember me
                        </label>
                        <a href="#">Forget password?</a>
                        
                    </div>

                    <button type="submit">Login</button>
                    <div className="register-link">
                        <p>Don't have an account?
                            <a href="#" onClick={switchToRegister}>Register</a>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>
  )
}

export default Login