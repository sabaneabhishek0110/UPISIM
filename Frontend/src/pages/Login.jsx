import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import Input from "../components/Input.jsx";
import Button from "../components/Button.jsx";
import Card from "../components/Card.jsx";
import useAuthStore from "../store/authStore.js";
import axios from "axios";

const Login = () => {
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [error,setError] = useState("");
  const setUser = useAuthStore((state) => state.setUser);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try{
        const response = await axios.post("http://localhost:8080/api/User/login",{
            phone,
            password
        }, 
        { withCredentials: true });
        console.log("data : ",response.data);
        console.log("data : ",response.body);

        setUser(response.data);
        navigate("/dashboard");
    }
    catch(err){
        console.log("Login Failed",err.response?.data?.message || err.message);
        setError(err.response?.data?.message|| "Something went wrong");
    }
  };

  return (
    <Card>
      <h2 className="text-2xl font-bold text-center mb-6">Login to UPI Simulator</h2>
      <form onSubmit={handleLogin}>
        <Input label="Phone" value={phone} onChange={(e) => setPhone(e.target.value)} placeholder="Enter Phone" />
        <Input label="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Enter password" />
        <Button type="submit">Login</Button>
      </form>
      <p className="mt-4 text-center">
        Don't have an account? <Link className="text-primary font-semibold" to="/register">Register</Link>
      </p>
    </Card>
  );
};

export default Login;
