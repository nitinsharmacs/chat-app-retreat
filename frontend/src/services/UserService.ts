class UserService {
  async getUser(): Promise<string | null> {
    return Promise.resolve(localStorage.getItem('username'));
  }

  async setUser(user: string): Promise<void> {
    return Promise.resolve(localStorage.setItem('username', user));
  }
}

export default UserService;
