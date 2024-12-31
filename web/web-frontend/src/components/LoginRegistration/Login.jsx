import React,{useState} from 'react';
import './LoginRegistration.css';

import { FaLock} from "react-icons/fa";
import { IoMdMail } from "react-icons/io";

import BackThreeD from './3d'



const Login = ({ switchToRegister }) => {

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [accountType, setAccountType] = useState("user")
    const handleChangeAccountType = (event) => {
        setAccountType(event.target.value);
      };

    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent page reload
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

                    <div className="account-type">
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
                    </div>

                    <div className="remember-forgot">
                        <label>
                            <input type="checkbox" />
                            Remember me
                        </label>
                        <a href="#">Forgot password?</a>
                        
                    </div>

                    <button type="submit">Login</button>
                    <div className="register-link">
                        <p> Don't have an account?
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