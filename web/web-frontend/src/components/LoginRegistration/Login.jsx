import React,{useState} from 'react';
import './LoginRegistration.css';

import { Link, useNavigate, useLocation, Navigate } from "react-router-dom";

import { FaLock} from "react-icons/fa";
import { IoMdMail } from "react-icons/io";

import BackThreeD from './3d'

import NavigationBar from '../Common/NavigationBar'
import { NavbarRightLeft, NavbarRightRight } from '../Common/CommonNavBarComponent'
import {api} from '../../Logic/API.jsx'



const Login = () => {
    const location = useLocation()
    const navigate = useNavigate();
    const accountType = location.state?.accountType;
    const registerLink = accountType === 'user' ? '/registrationuser' : '/registrationorg';

    console.log(accountType);

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("");
    const [rememberMe, setRememberMe] = useState(false);

    const handleRememberMeChange = (event) => {
        setRememberMe(event.target.checked); // Update the state when checkbox is toggled
    };
    
    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent page reload
      };
    const [error, setError] = useState('* All fields must be filled.');
    const handleLogin = async (e) => {
        e.preventDefault();
        if(accountType === 'user'){
            try {
                const response = await fetch(api+'user/login/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
                });
    
                if (!response.ok) {
                    const errorResponse = await response.json();
                    console.log(errorResponse);
                    throw new Error('Login failed!');
                }
    
                const data = await response.json();
                if(response.ok){
                    const storage = rememberMe ? localStorage : sessionStorage;
                    storage.setItem('userAuthToken', data.access_token);
                    storage.setItem('userDetails', JSON.stringify(data.user_detail));
                    console.log(data.user_detail);
                    setError('* All fields must be filled.')
                    navigate("/"); 
                }
                console.log('Login successful:', data);
            } catch (err) {
                console.error(err.message);
                setError('Invalid email or password.');
            }
        }else if(accountType === 'organization'){
            try {
                const response = await fetch(api+'org/login/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
                });
    
                if (!response.ok) {
                    const errorResponse = await response.json();
                    console.log(errorResponse);
                    throw new Error('Login failed!');
                }
    
                const data = await response.json();
                if(response.ok){
                    const storage = rememberMe ? localStorage : sessionStorage;
                    storage.setItem('orgAuthToken', data.access_token);
                    storage.setItem('orgDetails', JSON.stringify(data.organization_details));
                    console.log(storage.getItem('orgDetails'));
                    navigate('/org-dashboard');
                }
                console.log('Login successful:', data);

            } catch (err) {
                console.error(err.message);
                setError('Invalid email or password.');
            }
        }else{
            console.log('Invalid account type');
        }
    };
  return (
    <div className="wrapper">
        <NavigationBar 
          titleNav = "Blood Sewa" 
          rightLeftNav = {<NavbarRightLeft/>}
          rightRightNav = {null} 
        />
        <div className="background">
            <BackThreeD/>
        </div>
        <div className="form-box-login login">
            <div className="login-section">
                <h1>Blood Sewa</h1>
                <h3>Donate Blood, Save Life . . .</h3>
                <div className="img">
                    <img src="donate.png" alt="Donate" />
                </div>
            </div>
            <div className="login-form">
                <form onSubmit={handleSubmit}>
                    <h1>Login</h1>
                    <span style={{fontSize:"12px",marginLeft:"10px"}}>{error}</span>
                    <div className="input-box">
                        <input type="email"
                        value = {email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder='Email'/>
                        <IoMdMail className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="password"
                        value = {password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder='Password'/>
                        <FaLock className="icon"/>
                    </div>

                    <div className="remember-forgot">
                    <label>
                        <input
                            type="checkbox"
                            checked={rememberMe}
                            onChange={handleRememberMeChange}
                        />
                        Remember me
                    </label>
                        <a href="#">Forgot password?</a>
                        
                    </div>

                    <button type="submit" onClick={handleLogin}>Login</button>
                    <div className="register-link">
                        <p> Don't have an account?
                            <Link to = {registerLink}>Register</Link>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>
  )
}

export default Login