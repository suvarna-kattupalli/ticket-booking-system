import { useEffect, useState } from "react";

import EventCard from "../components/EventCard";

function Dashboard() {

  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    const fetchEvents = async () => {

      try {

       const response = await fetch(
  "http://localhost:8081/api/events"
);

const data = await response.json();

console.log(data);

setEvents(data);

      } catch (error) {

        console.error(error);

        alert("Failed to fetch events");

      } finally {

        setLoading(false);
      }
    };

    fetchEvents();

  }, []);

  if (loading) {
    return <h2>Loading Events...</h2>;
  }

  return (

    <div style={{ padding: "20px" }}>

      <h1 style={{ marginBottom: "20px", textAlign: "center" }} >
        Available Events
      </h1>

      <div
        style={{
          display: "grid",
          gridTemplateColumns:
            "repeat(auto-fit, minmax(300px, 1fr))",
          gap: "20px",
        }}
      >

        {events.map((event) => (

          <EventCard
            key={event.id}
            event={event}
          />

        ))}

      </div>

    </div>
  );
}

export default Dashboard;