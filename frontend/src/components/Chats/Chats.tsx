import { useEffect, useRef, useState } from 'react';
import { ChatAppProps, Message } from '../../Types';
import './chats.css';

const Chats = ({ chatService }: ChatAppProps) => {
  const [messages, setMessages] = useState<Message[]>([]);
  const messageRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    setInterval(async () => {
      setMessages(await chatService.getMessages());
      if (messageRef.current)
        messageRef.current.scrollTop = messageRef.current.scrollHeight;
    }, 1000);
  }, []);

  return (
    <div className='chat-messages' ref={messageRef}>
      {messages.map((message, index) => (
        <div className='message' key={index}>
          <div className='message-sender'>{message.sender}</div>
          <div className='message-text'>{message.text}</div>
        </div>
      ))}
    </div>
  );
};

export default Chats;
