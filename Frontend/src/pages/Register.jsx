import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

import Input from "../components/Input.jsx";
import Button from "../components/Button.jsx";
import Card from "../components/Card.jsx";
import useAuthStore from "../store/authStore.js";

const Register = () => {
    const [phone, setPhone] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");
    const setUser = useAuthStore((state) => state.setUser);
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try{
            if (password !== confirmPassword) {
                setError("Passwords do not match!");
                return;
            }
            setError("");

            const response = await axios.post("http://localhost:8081/api/User/register", {
                phone,
                password
            });
            console.log("data : ",response.data);
            setUser(response.data);
            navigate("/dashboard"); 
        }
        catch(err){
            console.error("Registration failed:", err.response?.data?.message || err.message);
            setError(err.response?.data?.message || "Something went wrong!");
        }
    };

    return (
        <Card>
        <h2 className="text-2xl font-bold text-center mb-6">Register for UPI Simulator</h2>
        <form onSubmit={handleRegister}>
            <Input
            label="Phone"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
            placeholder="Enter Phone"
            />
            <Input
            label="Password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Enter password"
            />
                <Input
                label="Confirm Password"
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirm Password"
                />

                {/* Warning message */}
                {error && <p className="text-red-500 mb-4">{error}</p>}

                <Button type="submit">Register</Button>
            </form>
            <p className="mt-4 text-center">
                Already have an account?{" "}
                <Link className="text-primary font-semibold" to="/login">
                Login
                </Link>
            </p>
        </Card>
    );
};

export default Register;
