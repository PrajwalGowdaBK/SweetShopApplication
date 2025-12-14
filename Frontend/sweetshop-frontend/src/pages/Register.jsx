import React, { useState } from 'react'
import { register } from '../services/authService'
import { useNavigate } from 'react-router-dom'

export default function Register({ onRegister }) {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState(null)
  const navigate = useNavigate()

  async function submit(e) {
    e.preventDefault()
    setError(null)
    try {
      await register(username, email, password)
      onRegister?.()
      navigate('/')
    } catch (err) {
      setError(err.response?.data || 'Registration failed')
    }
  }

  return (
    <div>
      <h2 style={
        {textAlign:"center"}
      }>Register</h2>
      <form onSubmit={submit}>
        <div className="form-row"><input placeholder="Username" value={username} onChange={e=>setUsername(e.target.value)} /></div>
        <div className="form-row"><input placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} /></div>
        <div className="form-row"><input type="Password" placeholder="password" value={password} onChange={e=>setPassword(e.target.value)} /></div>
        <div className="form-row"><button type="submit">Register</button></div>
        {error && <div style={{color:'red'}}>{JSON.stringify(error)}</div>}
      </form>
    </div>
  )
}
