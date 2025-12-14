import React, { useEffect, useState } from "react";
import api from "../services/api";
import { getCurrentUser } from "../services/authService";
import "./Sweets.css";

export default function Sweets() {
  const [sweets, setSweets] = useState([]);
  const [buyQty, setBuyQty] = useState({});
  const [error, setError] = useState(null);

  const user = getCurrentUser();
  const isLoggedIn = !!user;

  // 🔹 Load sweets from backend
  const loadSweets = async () => {
    try {
      const res = await api.get("/sweets");
      const data = Array.isArray(res.data) ? res.data : res.data.content;
      setSweets(data);
    } catch (err) {
      setError("Failed to load sweets");
    }
  };

  useEffect(() => {
    loadSweets();
  }, []);

  console.log("Current user:", getCurrentUser());

  // 🔹 Purchase logic
const purchase = async (id, quantity) => {
  try {
    await api.post(`/sweets/${id}/purchase`, {
      quantity: quantity,
    });

    // refresh from DB
    await loadSweets();

    // reset quantity for this sweet
    setBuyQty((prev) => ({
      ...prev,
      [id]: 1,
    }));
  } catch (err) {
    console.error(err);
    alert(err.response?.data?.message || "Purchase failed");
  }
};


  return (
    <div className="page">
      <h2>Available Sweets</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {sweets.map(sweet => {
       const qty = Number(buyQty[sweet.id]) || 0;
      const total = sweet.price * qty;


        return (
          <div key={sweet.id} className="sweet-card">
            <div>
              <h4>{sweet.name}</h4>
              <p className="meta">
                {sweet.category} • {sweet.price} Rs • Qty: {sweet.quantity}
              </p>
            </div>

            <p style={{ fontSize: "14px", color: "#444" }}>
              Total: {total} Rs
            </p>

            <input
  type="number"
  min="0"
  max={sweet.quantity}
  value={buyQty[sweet.id] ?? 0}
  disabled={!isLoggedIn}
  onChange={(e) => {
    const value = e.target.value;
    
    setBuyQty((prev) => ({
      ...prev,
      [sweet.id]: value === "" ? "" : Number(value),
    }));
  }}
  style={{ width: "60px", marginRight: "10px" }}
/>


            <button
              disabled={
                !isLoggedIn ||
                sweet.quantity === 0 ||
                qty > sweet.quantity
              }
              onClick={() => {
                if (!isLoggedIn) {
                  alert("Please login to purchase sweets.");
                  return;
                }

                const confirmed = window.confirm(
                  `Confirm purchase?\n\nSweet: ${sweet.name}\nQuantity: ${qty}\nTotal: ${total} Rs`
                );

                if (!confirmed) return;

                purchase(sweet.id, qty);
              }}
            >
              Buy
            </button>

            {qty > sweet.quantity && (
              <div style={{ color: "red", fontSize: "12px" }}>
                Quantity exceeds available stock
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
}
