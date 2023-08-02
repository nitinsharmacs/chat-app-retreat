import { useEffect, useRef, useState } from 'react';
import { CreateChatProps } from '../../Types';
import './createchat.css';

const CreateChat = ({ handleSendMessage }: CreateChatProps) => {
  const [newMessage, setNewMessage] = useState('');
  const inputRef = useRef<HTMLInputElement>(null);

  const onSendBtnClick = () => {
    handleSendMessage(newMessage);
    setNewMessage('');
  };

  useEffect(() => {
    if (inputRef.current) inputRef.current.focus();
  }, []);

  return (
    <div className='chat-input'>
      <input
        type='text'
        placeholder='Type a message...'
        value={newMessage}
        onChange={(e) => setNewMessage(e.target.value)}
        onKeyPress={(e) => {
          if (e.key === 'Enter') onSendBtnClick();
        }}
        ref={inputRef}
      />
      <button onClick={onSendBtnClick}>Send</button>
    </div>
  );
};

export default CreateChat;
