
import API from './api';

export async function login(usernameOrEmail, password) {
  const res = await API.post('/auth/login', { usernameOrEmail, password });
  localStorage.setItem('token', res.data.token);
  localStorage.setItem('user', JSON.stringify({ username: res.data.username, roles: res.data.roles }));
  return res.data;
}

export async function register(username, email, password) {
  const res = await API.post('/auth/register', { username, email, password });
  localStorage.setItem('token', res.data.token);
  localStorage.setItem('user', JSON.stringify({ username: res.data.username, roles: res.data.roles }));
  return res.data;
}

export function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
}

export function getCurrentUser() {
  const u = localStorage.getItem('user');
  return u ? JSON.parse(u) : null;
}


