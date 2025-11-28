import { use, useEffect } from "react";
import useAuthStore from "./store/authStore";
import { useNavigate } from "react-router-dom";

const ProtectedRoute = ({ children }) => {
  const loggedIn = useAuthStore(state => state.isLoggedIn);
  const navigate = useNavigate();

  useEffect(() => {
    if (!loggedIn) {
      navigate("/login");
    } 
  }, []);
  
  return children;
}

export default ProtectedRoute;
