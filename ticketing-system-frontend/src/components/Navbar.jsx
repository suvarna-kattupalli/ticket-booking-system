import { Link, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

function Navbar() {
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div className="navbar-logo">
        🎟️ TicketHub
      </div>

      <div className="navbar-links">
        <Link to="/">Home</Link>
        <Link to="/dashboard">Events</Link>
        <Link to="/my-bookings">My Bookings</Link>  
      </div>

      <div className="navbar-actions">
        {token ? (
          <button
            className="logout-btn"
            onClick={handleLogout}
          >
            Logout
          </button>
        ) : (
          <>
            <button
              className="login-btn"
              onClick={() => navigate("/login")}
            >
              Login
            </button>

            <button
              className="register-btn"
              onClick={() => navigate("/register")}
            >
              Register
            </button>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;