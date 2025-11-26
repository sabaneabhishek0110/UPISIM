const Input = ({ label, type = "text", value, onChange, placeholder }) => (
  <div className="flex flex-col mb-4">
    <label className="mb-1 font-semibold">{label}</label>
    <input
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-primary"
    />
  </div>
);

export default Input;
