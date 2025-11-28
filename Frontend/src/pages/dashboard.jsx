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
   <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 p-6">
        <h1 className="text-2xl font-semibold mb-6 text-gray-800">Welcome to Your Dashboard</h1>

        <button
        onClick={handleLogout}
        type="button"
        className=" bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white font-medium py-2 px-4 rounded-lg shadow-md transition duration-300 ease-in-out"
        >
        Logout
        </button>
    </div>

  )
}

export default Dashboard