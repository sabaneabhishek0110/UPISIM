const Button = ({ children, onClick, type = "button" }) => (
  <button
    type={type}
    onClick={onClick}
    className="w-full bg-primary text-white py-2 rounded hover:bg-indigo-600 transition"
  >
    {children}
  </button>
);

export default Button;
