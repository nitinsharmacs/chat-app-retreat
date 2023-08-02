import ChatService from './services/ChatService';
import UserService from './services/UserService';

export interface Message {
  text: string;
  sender: string;
}

export interface ChatAppProps {
  messages?: Message[];
  chatService: ChatService;
  username: string;
}

export interface CreateChatProps {
  handleSendMessage: (arg0: string) => Promise<void>;
}

export interface LoginScreenProps {
  userService: UserService;
  onLogin: (username: string) => void;
}
