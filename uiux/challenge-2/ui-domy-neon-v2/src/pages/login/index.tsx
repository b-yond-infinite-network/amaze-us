import React, { useCallback, useState } from 'react';
import { useDispatch } from 'react-redux';
import { login } from '../../store/sagas/auth';

function LoginPage() {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const dispatch = useDispatch();
  
  const onLogin = useCallback((e: React.FormEvent) => {
    e.preventDefault();
    dispatch(login({
      username,
      password
    }));
  }, [username, password, dispatch]);

  return (
    <form onSubmit={onLogin}>
      <input name="username" value={username} onChange={e => setUsername(e.target.value)} required />
      <br/>
      <input name="password" type="password" value={password} onChange={e => setPassword(e.target.value)} required />
      <br/>
      <button type="submit">Login</button>
    </form>
  )
}

export default LoginPage;