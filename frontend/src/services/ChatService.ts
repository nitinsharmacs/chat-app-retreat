import { Message } from '../Types';

class ChatService {
  url: string;
  constructor(url: string) {
    this.url = url;
  }

  async getMessages(): Promise<Message[]> {
    return await fetch(this.url + '/e4r/message').then(async (res) => {
      const messages: Message[] = (await res.json()) as Message[];
      return messages;
    });
  }

  async createMessage(sender: string, text: string, createdAt: string) {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-return
    return await fetch(this.url + '/e4r/create-message', {
      method: 'POST',
      headers: { 'Content-type': 'application/json' },
      body: JSON.stringify({ sender, text, createdAt }),
    }).then((res) => res.json());
  }
}

export default ChatService;
