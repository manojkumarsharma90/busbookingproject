export interface AuthRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  msg: string;
  timestamp: string;
  userName: string;
  role: string;
}

export interface SignupRequest {
  name: string;
  userName: string;
  email: string;
  phoneNo: string;
  password: string;
  address?: string;
  city?: string;
  state?: string;
  zipCode?: string;
}
