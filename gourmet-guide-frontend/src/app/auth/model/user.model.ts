export interface User {
  id: number;

  lastName: string;

  firstName: string;

  username: string;

  password: string;

  email: string;

  role: Role;
}

export enum Role {
  user = 'user',

  admin = 'admin',
}
