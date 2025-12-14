import React, { useEffect, useState } from 'react'
import API from '../services/api'
import { getCurrentUser } from '../services/authService'

export default function Admin() {
  const [sweets, setSweets] = useState([])
  const [form, setForm] = useState({ name:'', category:'', price:0, quantity:0 })
  const [error, setError] = useState(null)
  const user = getCurrentUser()

  useEffect(() => { load() }, [])

  async function load(){
    try {
      const res = await API.get('/sweets')
      const data = res.data.content ? res.data.content : res.data
      setSweets(data)
    } catch (err) { setError(err.response?.data || 'load failed') }
  }

  async function create(e) {
    e.preventDefault()
    try {
      await API.post('/sweets', form)
      setForm({ name:'', category:'', price:0, quantity:0 })
      load()
    } catch (err) { setError(err.response?.data || 'create failed') }
  }

  async function remove(id){
    try{ await API.delete(`/sweets/${id}`); load() } catch(err){ setError(err.response?.data) }
  }

  async function restock(id){
    const qty = parseInt(prompt('Restock quantity'),10)
    if (!qty) return
    try{ await API.post(`/sweets/${id}/restock`, { quantity: qty }); load() } catch(err){ setError(err.response?.data) }
  }

  if (!user || !user.roles.includes('ROLE_ADMIN')) return <div>Admin access required</div>

  return (
    <div>
      <h2 style={{textAlign:"center"}}>Admin — Manage Sweets</h2>
      {error && <div style={{color:'red'}}>{JSON.stringify(error)}</div>}
      <form onSubmit={create}>
        <label>
  Name
  <input
    value={form.name}
    onChange={e => setForm({ ...form, name: e.target.value })}
  />
</label>

<label>
  Category
  <input
    value={form.category}
    onChange={e => setForm({ ...form, category: e.target.value })}
  />
</label>

<label>
  Price
  <input
    type="number"
    value={form.price}
    onChange={e => setForm({ ...form, price: parseFloat(e.target.value) })}
  />
</label>

<label>
  Quantity
  <input
    type="number"
    value={form.quantity}
    onChange={e => setForm({ ...form, quantity: parseInt(e.target.value, 10) })}
  />
</label>

        <button type="submit">Add</button>
      </form>

    <div className="admin-existing">
  <h3>Existing Sweets</h3>

  {sweets.map(s => (
    <div className="admin-card" key={s.id}>
      <div className="admin-info">
        <strong>{s.name}</strong>
        <span>Qty: {s.quantity}</span>
      </div>

      <div className="admin-actions">
        <button onClick={() => restock(s.id)}>Restock</button>
        <button className="danger" onClick={() => remove(s.id)}>Delete</button>
      </div>
    </div>
  ))}
</div>
    </div>
  )
}
