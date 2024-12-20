import { useState } from 'react'

import Login from './Login'
import Registration from './RegistrationOrg'
import Show from '../Show';

const LoginRegistration = () => {
    const [isLoginVisible, setIsLoginVisible] = useState(true);
  
  return (
    <>
    <Show isVisible={isLoginVisible}>
      <Login switchToRegister={() => setIsLoginVisible(false)} />
    </Show>
    <Show isVisible={!isLoginVisible}>
      <Registration switchToLogin={() => setIsLoginVisible(true)} />
    </Show>
    </>
  )
}

export default LoginRegistration