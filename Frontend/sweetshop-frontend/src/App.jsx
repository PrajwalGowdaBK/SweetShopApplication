import React, { useEffect, useState } from 'react'
import { Routes, Route, Link, useNavigate } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Sweets from './pages/Sweets'
import Admin from './pages/Admin'
import { getCurrentUser, logout } from './services/authService'
import "./App.css";

export default function App() {
  const [user, setUser] = useState(getCurrentUser())
  const navigate = useNavigate()

  useEffect(() => {
    setUser(getCurrentUser())
  }, [])

  function handleLogout() {
    logout()
    setUser(null)
    navigate('/login')
  }

  const headerStyle = {
  background: "#ffffff",
  padding: "10px 20px",
  borderBottom: "1px solid #ddd",
  textAlign: "center"
};

  return (
    // <div className="app">
    //   <nav className="nav">
    //     <Link to="/">Home</Link>
    //     {!user && <Link to="/login">Login</Link>}
    //     {!user && <Link to="/register">Register</Link>}
    //     {user && <button onClick={handleLogout}>Logout ({user.username})</button>}
    //     {user && user.roles && user.roles.includes('ROLE_ADMIN') && <Link to="/admin">Admin</Link>}
    //   </nav>

    //   <main className="main">
    //     <Routes>
    //       <Route path="/" element={<Sweets />} />
    //       <Route path="/login" element={<Login onLogin={() => setUser(getCurrentUser())} />} />
    //       <Route path="/register" element={<Register onRegister={() => setUser(getCurrentUser())} />} />
    //       <Route path="/admin" element={<Admin />} />
    //     </Routes>
    //   </main>
    // </div>

    <div className="app">
      
      {/* SIMPLE NAVBAR */}
      <div style={{textAlign:"center",backgroundColor:"green",padding:"1px",color:"white",fontSize:"22px",fontFamily:"serif"}}>
        <h1 style={{wordSpacing:"40px"}}>SWEET   SHOP</h1>
      </div>
      <nav className="nav">
        <Link to="/">Home</Link>

        {!user && <Link to="/login">Login</Link>}
        {!user && <Link to="/register">Register</Link>}

        {user && (
          <button className="logout-btn" onClick={handleLogout}>
            Logout ({user.username})
          </button>
        )}

        {user && user.roles?.includes("ROLE_ADMIN") && (
          <Link to="/admin">Admin</Link>
        )}
      </nav>

      {/* MAIN CONTENT */}
      <main className="main">
        <Routes>
          <Route path="/" element={<Sweets />} />

          <Route
            path="/login"
            element={<Login onLogin={() => setUser(getCurrentUser())} />}
          />

          <Route
            path="/register"
            element={<Register onRegister={() => setUser(getCurrentUser())} />}
          />

          <Route path="/admin" element={<Admin />} />
        </Routes>
      </main>

    </div>
  )
}
