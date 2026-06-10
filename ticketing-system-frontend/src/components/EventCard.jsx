import { useNavigate } from "react-router-dom";

function EventCard({ event }) {

  const navigate = useNavigate();

  const handleView = () => {
    navigate(`/event/${event.id}`);
  };
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
    <div
      style={{
        background: "#ffffff",
        borderRadius: "20px",
        overflow: "hidden",
        boxShadow: "0 8px 25px rgba(0,0,0,0.08)",
        transition: "0.3s",
        cursor: "pointer",
      }}
    >
                <img
  src={getEventImage(event.title)}
  alt={event.title}
  className="event-image"
/>

      <div
        style={{
          padding: "20px",
        }}
      >
        <h2
          style={{
            color: "#1e293b",
            marginBottom: "15px",
          }}
        >
          {event.title}
        </h2>

        <p style={{ color: "#64748b" }}>
          📍 {event.location}
        </p>

        <p style={{ color: "#64748b" }}>
          📅{" "}
          {event.eventDate
            ? new Date(
                event.eventDate
              ).toLocaleString()
            : "N/A"}
        </p>

        <p
          style={{
            color: "#16a34a",
            fontWeight: "600",
          }}
        >
          💰 ₹{event.price}
        </p>

        <p
          style={{
            color: "#2563eb",
            fontWeight: "600",
          }}
        >
          🎟 Seats Left: {event.availableSeats}
        </p>

        <button
          onClick={handleView}
          style={{
            width: "100%",
            marginTop: "15px",
            padding: "12px",
            border: "none",
            borderRadius: "10px",
            background:
              "linear-gradient(135deg,#6366f1,#8b5cf6)",
            color: "white",
            fontSize: "15px",
            fontWeight: "600",
            cursor: "pointer",
          }}
        >
          View Details
        </button>
      </div>
    </div>
  );
}

export default EventCard;