import { useState } from 'react'

import Login from './Login'
import Registration from './Registration'

const LoginRegistration = () => {
    const [isLoginVisible, setIsLoginVisible] = useState(true);
  
  return (
    <>
        {isLoginVisible ? (
        <Login switchToRegister={() => setIsLoginVisible(false)} />
      ) : (
        <Registration switchToLogin={() => setIsLoginVisible(true)} />
      )}
    </>
  )
}

export default LoginRegistration