import { useState } from "react";
import { EyeIcon, EyeSlashIcon } from "@heroicons/react/24/outline";

const Input = ({ label, type = "text", value, onChange, placeholder }) => {
  const [showPassword, setShowPassword] = useState(false);

  const inputType = type === "password" ? (showPassword ? "text" : "password") : type;

  return (
    <div className="flex flex-col mb-4 relative">
      <label className="mb-1 font-semibold text-gray-700">{label}</label>
      <input
        type={inputType}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className="p-2 border border-gray-300 rounded w-full focus:outline-none focus:ring-2 focus:ring-primary pr-10"
      />
      {/* Eye icon toggle for password */}
      {type === "password" && (
        <button
          type="button"
          className="absolute right-3 top-9 text-gray-500 hover:text-gray-700"
          onClick={() => setShowPassword(!showPassword)}
        >
          {showPassword ? (
            <EyeSlashIcon className="h-5 w-5" />
          ) : (
            <EyeIcon className="h-5 w-5" />
          )}
        </button>
      )}
    </div>
  );
};

export default Input;
