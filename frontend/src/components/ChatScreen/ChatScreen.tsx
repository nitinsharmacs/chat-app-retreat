import './chatscreen.css';
import Chats from '../Chats/Chats';
import CreateChat from '../CreateChat/CreateChat';
import { ChatAppProps } from '../../Types';

const ChatScreen = ({ chatService, username }: ChatAppProps) => {
  const handleSendMessage = async (newMessage: string) => {
    if (newMessage.trim() !== '') {
      await chatService.createMessage(
        username,
        newMessage,
        new Date().toISOString()
      );
    }
  };

  return (
    <div className='chat-page'>
      <div className='chat-wrapper'>
        <Chats chatService={chatService} />
        <CreateChat handleSendMessage={handleSendMessage}></CreateChat>
      </div>
    </div>
  );
};

export default ChatScreen;
