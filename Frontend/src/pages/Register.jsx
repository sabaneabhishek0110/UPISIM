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


    const validatePassword = (pwd) => {
        const minLength = 8;
        const maxLength = 16;
        const pattern = /^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]+$/;

        if (pwd.length < minLength || pwd.length > maxLength) {
        return `Password must be between ${minLength}-${maxLength} characters`;
        }
        if (!pattern.test(pwd)) {
        return "Password must include at least 1 number and 1 special character (!@#$%^&*)";
        }
        return null; // valid
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        try{
            if (password !== confirmPassword) {
                setError("Passwords do not match!");
                return;
            }

            const passError = validatePassword(password);
            if (passError) {
                setError(passError);
                return;
            }

            setError("");

            const response = await axios.post("http://localhost:8080/api/User/register", {
                phone,
                password
            },
            {withCredentials: true});
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
            <form onSubmit={handleRegister} className="space-y-4">
                {/* Phone Input */}
                <Input
                label="Phone"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                placeholder="Enter Phone"
                />

                {/* Password Input with helper text and show/hide */}
                <Input
                label="Password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter your password"
                />
                <p className="text-sm text-gray-500 -mt-2 mb-2">
                    (8-16 characters, at least 1 number & 1 special character)
                </p>

                {/* Confirm Password Input */}
                <Input
                label="Confirm Password"
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirm Password"
                />

                {/* Warning message */}
                {error && <p className="text-red-500 mb-4">{error}</p>}

                {/* Register Button */}
                <Button type="submit" className="w-full">
                Register
                </Button>
            </form>

            <p className="mt-4 text-center text-gray-700">
                Already have an account?{" "}
                <Link className="text-primary font-semibold" to="/login">
                Login
                </Link>
            </p>
        </Card>

    );
};

export default Register;
