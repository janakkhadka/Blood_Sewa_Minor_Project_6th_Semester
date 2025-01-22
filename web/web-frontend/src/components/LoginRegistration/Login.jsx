import React,{useState} from 'react';
import './LoginRegistration.css';

import { Link, useNavigate, useLocation, Navigate } from "react-router-dom";

import { FaLock} from "react-icons/fa";
import { IoMdMail } from "react-icons/io";

import BackThreeD from './3d'

import NavigationBar from '../Common/NavigationBar'
import { NavbarRightLeft, NavbarRightRight } from '../Common/CommonNavBarComponent'



const Login = () => {
    const location = useLocation()
    const accountType = location.state?.accountType;
    const registerLink = accountType === 'user' ? '/registrationuser' : '/registrationorg';

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("");
    
    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent page reload
      };
    const [error, setError] = useState('* All fields must be filled.');
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://172.16.12.229:8000/api/user/login/', {
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
                localStorage.setItem('authToken', data.token);
                setError('* All fields must be filled.')
            }
            console.log('Login successful:', data);
            // Handle successful login (e.g., save token, redirect)
        } catch (err) {
            console.error(err.message);
            setError('Invalid email or password.');
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

                    {/* <div className="account-type">
                        <label>
                            Login/Register as:

                            <input type="radio"
                             name="user-type"
                             value="user"
                             checked={accountType === "user"}
                             onChange={handleChangeAccountType} 
                            />
                            <label htmlFor="User" className='user'>User</label>

                            <input type="radio"
                             name="user-type"
                             value="organization"
                             checked={accountType === "organization"}
                             onChange={handleChangeAccountType}
                            />
                            <label htmlFor="Organization">Organization</label>

                        </label>
                    </div> */}

                    <div className="remember-forgot">
                        <label>
                            <input type="checkbox" />
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