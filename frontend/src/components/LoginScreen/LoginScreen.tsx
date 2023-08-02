import { useState } from 'react';
import { LoginScreenProps } from '../../Types';
import './loginscreen.css';

const LoginScreen = ({ userService, onLogin }: LoginScreenProps) => {
  const [username, setUsername] = useState('');

  return (
    <div className='login-screen'>
      <h2>Join E4R Group</h2>
      <input
        type='text'
        name='username'
        id='username'
        placeholder='Type username'
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <button
        // eslint-disable-next-line @typescript-eslint/no-misused-promises
        onClick={async () => {
          await userService.setUser(username);
          onLogin(username);
        }}
      >
        Start
      </button>
    </div>
  );
};

export default LoginScreen;
