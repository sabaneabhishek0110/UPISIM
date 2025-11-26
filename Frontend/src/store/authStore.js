import { create } from "zustand";
import axios from "axios";

const useAuthStore = create((set) => ({
  user: null,          // user object from backend
  isLoggedIn: false,   // boolean flag
  loading: true,       // auth check in progress

  setUser: (user) => set({ user, isLoggedIn: true, loading: false }),

  logout: () => set({ user: null, isLoggedIn: false, loading: false }),

  checkAuth: async () => {
    set({ loading: true });
    try {
      const res = await axios.get("/api/auth/me", { withCredentials: true }); 
      
      set({ user: res.data.user, isLoggedIn: true, loading: false });
    } catch (error) {
      set({ user: null, isLoggedIn: false, loading: false });
    }
  },
}));

export default useAuthStore;
