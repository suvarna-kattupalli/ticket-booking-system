import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import API from "../services/api";

function Register() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    role: "USER",
  });

  const [loading, setLoading] = useState(false);

  // Handle Input Change
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  // Handle Form Submit
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Password Match Validation
    if (formData.password !== formData.confirmPassword) {
      alert("Passwords do not match");
      return;
    }

    // Password Length Validation
    if (formData.password.length < 6) {
      alert("Password must be at least 6 characters");
      return;
    }

    try {
      setLoading(true);

      const requestData = {
        name: formData.name,
        email: formData.email,
        password: formData.password,
        role: formData.role,
      };

      const response = await API.post(
        "/auth/register",
        requestData
      );
    alert(response.data.message);
      console.log(response.data);

      alert("Registration Successful");

      navigate("/login");
    } catch (error) {
      console.error(error);

      if (error.response) {
        alert(error.response.data.message);
      } else {
        alert("Registration Failed");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "90vh",
      }}
    >
      <div
        style={{
          width: "400px",
          backgroundColor: "white",
          padding: "30px",
          borderRadius: "10px",
          boxShadow: "0px 0px 10px rgba(0,0,0,0.2)",
        }}
      >
        <h2
          style={{
            textAlign: "center",
            marginBottom: "20px",
          }}
        >
          Register
        </h2>

        <form onSubmit={handleSubmit}>
          {/* Name */}
          <div style={{ marginBottom: "15px" }}>
            <input
              type="text"
              name="name"
              placeholder="Enter Name"
              value={formData.name}
              onChange={handleChange}
              required
              style={{
                width: "100%",
                padding: "12px",
                borderRadius: "5px",
                border: "1px solid gray",
              }}
            />
          </div>

          {/* Email */}
          <div style={{ marginBottom: "15px" }}>
            <input
              type="email"
              name="email"
              placeholder="Enter Email"
              value={formData.email}
              onChange={handleChange}
              required
              style={{
                width: "100%",
                padding: "12px",
                borderRadius: "5px",
                border: "1px solid gray",
              }}
            />
          </div>

          {/* Password */}
          <div style={{ marginBottom: "15px" }}>
            <input
              type="password"
              name="password"
              placeholder="Enter Password"
              value={formData.password}
              onChange={handleChange}
              required
              style={{
                width: "100%",
                padding: "12px",
                borderRadius: "5px",
                border: "1px solid gray",
              }}
            />
          </div>
             {/* Role */}
<div style={{ marginBottom: "15px" }}>
  <select
    name="role"
    value={formData.role}
    onChange={handleChange}
    style={{
      width: "100%",
      padding: "12px",
      borderRadius: "5px",
      border: "1px solid gray",
    }}
  >
    <option value="USER">USER</option>
    <option value="ADMIN">ADMIN</option>
  </select>
</div>
          

          {/* Confirm Password */}
          <div style={{ marginBottom: "20px" }}>
            <input
              type="password"
              name="confirmPassword"
              placeholder="Confirm Password"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
              style={{
                width: "100%",
                padding: "12px",
                borderRadius: "5px",
                border: "1px solid gray",
              }}
            />
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            disabled={loading}
            style={{
              width: "100%",
              padding: "12px",
              backgroundColor: "black",
              color: "white",
              border: "none",
              borderRadius: "5px",
              cursor: "pointer",
            }}
          >
            {loading ? "Registering..." : "Register"}
          </button>
        </form>

        {/* Login Link */}
        <p
          style={{
            marginTop: "15px",
            textAlign: "center",
          }}
        >
          Already have an account?{" "}
          <Link to="/login">Login</Link>
        </p>
      </div>
    </div>
  );
}

export default Register;