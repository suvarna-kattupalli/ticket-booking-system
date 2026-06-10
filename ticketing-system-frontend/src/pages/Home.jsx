import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import "../styles/Home.css";

function Home() {
  const [events, setEvents] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8081/api/events")
      .then((res) => res.json())
      .then((data) => setEvents(data))
      .catch((err) => console.error(err));
  }, []);
  const getEventImage = (title) => {

  const name = title.toLowerCase();

  if (name.includes("music"))
    return "https://images.unsplash.com/photo-1501386761578-eac5c94b800a";

  if (name.includes("tech"))
    return "https://images.unsplash.com/photo-1516321318423-f06f85e504b3";

  if (name.includes("sports"))
   return "https://images.unsplash.com/photo-1540747913346-19e32dc3e97e";

  if (name.includes("comedy"))
        return "https://images.unsplash.com/photo-1507676184212-d03ab07a01bf";

  return "https://images.unsplash.com/photo-1492684223066-81342ee5ff30";
};

  return (
    <>
      <Navbar />

      {/* HERO SECTION */}
      <section className="hero">
        <div className="hero-content">
          <h1>Discover Amazing Events Near You</h1>

          <p>
            Book concerts, sports, comedy shows, festivals,
            and unforgettable experiences with a few clicks.
          </p>

          <button
            className="explore-btn"
            onClick={() =>
              document
                .getElementById("events-section")
                ?.scrollIntoView({ behavior: "smooth" })
            }
          >
            Explore Events
          </button>
        </div>
      </section>

      {/* STATS */}
      <section className="stats">
        <div className="stat-card">
          <h2>500+</h2>
          <p>Events</p>
        </div>

        <div className="stat-card">
          <h2>50K+</h2>
          <p>Bookings</p>
        </div>

        <div className="stat-card">
          <h2>100+</h2>
          <p>Venues</p>
        </div>

        <div className="stat-card">
          <h2>24/7</h2>
          <p>Support</p>
        </div>
      </section>

      {/* CATEGORIES */}
      <section className="categories">
        <h2>Browse Categories</h2>

        <div className="category-grid">
          <div className="category-card">
            🎵
            <h3>Music</h3>
          </div>

          <div className="category-card">
            ⚽
            <h3>Sports</h3>
          </div>

          <div className="category-card">
            🎭
            <h3>Comedy</h3>
          </div>

          <div className="category-card">
            🎪
            <h3>Festivals</h3>
          </div>
        </div>
      </section>

      {/* EVENTS */}
      <section
        id="events-section"
        className="events-section"
      >
        <h2>Upcoming Events</h2>

        <div className="event-grid">
          {events.map((event) => (
            <div
              key={event.id}
              className="event-card"
            >
              <img
  src={getEventImage(event.title)}
  alt={event.title}
  className="event-image"
/>

              <div className="event-content">
                <h3>{event.title}</h3>

                <p>📍 {event.location}</p>

                <p>
                  🎫 Available Seats:
                  {" "}
                  {event.availableSeats}
                </p>

                <p>
                  💰 ₹{event.price}
                </p>

                <button
                  className="book-btn"
                  onClick={() =>
                    navigate(`/events/${event.id}`)
                  }
                >
                  View Details
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* WHY CHOOSE US */}
      <section className="features">
        <h2>Why Choose Us?</h2>

        <div className="feature-grid">
          <div className="feature-card">
            ⚡
            <h3>Instant Booking</h3>
            <p>Book tickets in seconds.</p>
          </div>

          <div className="feature-card">
            🔒
            <h3>Secure Payments</h3>
            <p>Safe and protected transactions.</p>
          </div>

          <div className="feature-card">
            🎟️
            <h3>E-Tickets</h3>
            <p>Get tickets directly on your phone.</p>
          </div>

          <div className="feature-card">
            ⭐
            <h3>Best Events</h3>
            <p>Top-rated events and experiences.</p>
          </div>
        </div>
      </section>

      {/* FOOTER */}
      <footer className="footer">
        <h3>TicketHub</h3>

        <p>
          Your trusted platform for booking concerts,
          sports, comedy shows, and live events.
        </p>

        <p>
          © 2026 TicketHub. All rights reserved.
        </p>
      </footer>
    </>
  );
}

export default Home;