import { useEffect } from "react";
import useAuthStore from "./store/authStore";
import { useNavigate } from "react-router-dom";
import AppRoutes from "./routes.jsx";

function App() {
  const checkAuth = useAuthStore((s) => s.checkAuth);
  const user = useAuthStore((s) => s.user);
  const loading = useAuthStore((s) => s.loading);
  const navigate = useNavigate();

  useEffect(() => {
    checkAuth();
  }, []);

  useEffect(() => {
    if (!loading && user) {
      navigate("/dashboard");
    }
  }, [loading, user]);

  if (loading) return <div className="p-10">Checking session...</div>;

  return <AppRoutes />;
}

export default App;