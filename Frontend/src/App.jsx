import { useEffect } from "react";
import useAuthStore from "./store/authStore";
import AppRoutes from "./routes";

function App() {
  const checkAuth = useAuthStore((state) => state.checkAuth);
  const loading = useAuthStore((state) => state.loading);

  useEffect(() => {
    checkAuth(); 
  }, []);

  if (loading) return <div>Checking authentication...</div>;

  return <AppRoutes />; 
}

export default App;