import ChatService from './services/ChatService';
import ChatScreen from './components/ChatScreen/ChatScreen';
import './App.css';
import LoginScreen from './components/LoginScreen/LoginScreen';
import UserService from './services/UserService';
import { useEffect, useState } from 'react';

function App() {
  const [username, setUsername] = useState<string | null>(null);

  const chatService = new ChatService('/backend');
  const userService = new UserService();

  const handleOnLogin = (username: string) => {
    console.log(username);

    setUsername(username);
  };

  useEffect(() => {
    const loadUser = async () => {
      const username: string = await userService.getUser();
      setUsername(username);
    };
    void loadUser();
  }, []);

  console.log(username);

  return (
    <div className='page'>
      {username ? (
        <ChatScreen chatService={chatService} username={username} />
      ) : (
        <LoginScreen onLogin={handleOnLogin} userService={userService} />
      )}
    </div>
  );
}

export default App;
