import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { login } from '../services/authService'

export default function Login({ onLogin }) {
  const [usernameOrEmail, setUsernameOrEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState(null)
  const navigate = useNavigate()

  async function submit(e) {
    e.preventDefault()                 // <- prevent browser GET submit
    setError(null)
    try {
      await login(usernameOrEmail, password) // axios.post inside authService
      onLogin?.()
      navigate('/')
    } catch (err) {
      console.error("login error", err)
      setError(err.response?.data?.error || err.response?.data || 'Login failed')
    }
  }

  return (
    <div style={{padding:"10px,50px",backgroundColor:"wheat"}}>
      <h2 style={{textAlign:"center"}}>Login</h2>
      <div >
      <form onSubmit={submit} /* no action attribute */ /*style={{backgroundColor:"green",padding:"10px",width:"600px",margin:"auto",borderRadius:"10px"}}*/>
        <div className="form-row">
          <input name="usernameOrEmail" placeholder="Username or E-mail"
                 value={usernameOrEmail} onChange={e=>setUsernameOrEmail(e.target.value)} />
        </div>
        <div className="form-row">
          <input name="password" type="password" placeholder="Password"
                 value={password} onChange={e=>setPassword(e.target.value)} />
        </div>
        <div className="form-row">
          <button type="submit">Login</button>
        </div>
        {error && <div style={{color:'red'}}>{JSON.stringify(error)}</div>}
      </form>
      </div>
    </div>
  )
}
