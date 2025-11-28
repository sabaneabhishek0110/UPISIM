import { useAuthStore } from "../store/auth";
import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ children }) {
  const user = useAuthStore(state => state.user);
  const loading = useAuthStore(state => state.loading);

  if (loading) return <div>Checking session...</div>;
  if (!user) return <Navigate to="/login" />;
  
  return children;
}
