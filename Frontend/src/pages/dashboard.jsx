import React from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Dashboard() {
    const navigate = useNavigate();
    const handleLogout = (e) => {
        try{
            const response = axios.post("http://localhost:8080/api/User/logout",{}, { withCredentials: true });
            console.log(response.data);
            console.log("Logged out successfully");
            navigate("/login");
        } catch (error) {
            console.error("Error during login:", error);
        }
    }

  return (
    <div className='flex justify-center items-center'>
        <button className="bg-blue-500 text-white px-4 py-2 rounded w-20 h-10 cursor-pointer " onClick={handleLogout} type="submit">Logout</button>
    </div>
  )
}

export default Dashboard