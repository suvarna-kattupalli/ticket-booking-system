import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Navbar from "../components/Navbar";
import "../styles/EventDetails.css";
import PaymentQRCode from "./PaymentQRCode";

function EventDetails() {
  const { id } = useParams();

  const [event, setEvent] = useState(null);
  const [seats, setSeats] = useState([]);
  const [selectedSeat, setSelectedSeat] = useState(null);
  const [loading, setLoading] = useState(true);

 

  const loadEvent = async () => {
    try {
      const response = await fetch(
        `http://localhost:8081/api/events/${id}`
      );

      const data = await response.json();

      setEvent(data);
    } catch (error) {
      console.error("Failed to fetch event", error);
    }
  };

  const loadSeats = async () => {
    try {
      console.log("Loading seats...");
      const response = await fetch(
        `http://localhost:8081/api/seats/event/${id}`
      );
       console.log("Seat status:", response.status);

      const data = await response.json();
         console.log(data);

      setSeats(data);
      setLoading(false);
    } catch (error) {
      console.error("Failed to fetch seats", error);
      setLoading(false);
    }
  };
 useEffect(() => {
  const fetchData = async () => {

    const eventResponse = await fetch(
      `http://localhost:8081/api/events/${id}`
    );

    const eventData =
      await eventResponse.json();

    setEvent(eventData);

    const seatResponse = await fetch(
      `http://localhost:8081/api/seats/event/${id}`
    );

    const seatData =
      await seatResponse.json();

    setSeats(seatData);

    setLoading(false);
  };

  fetchData();
}, [id]);
const handleBookSeat = async () => {
  if (!selectedSeat) {
    alert("Please select a seat");
    return;
  }

  const token = localStorage.getItem("token");

  if (!token) {
    alert("Please login first");
    return;
  }

  try {

    const orderResponse = await fetch(
      "http://localhost:8081/api/payment/create-order",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          amount: event.price,
        }),
      }
    );

    const order = await orderResponse.json();

    console.log("ORDER =", order);

    const options = {
      key: "rzp_test_Synai6FEaFjmOy",

      amount: order.amount,
      currency: order.currency,

      name: "Ticketing System",

      description: event.title,

      order_id: order.id,

      handler: async function (response) {

        console.log(
          "Payment Success",
          response
        );

        const bookingResponse = await fetch(
          "http://localhost:8081/api/bookings",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({
              eventId: Number(id),
              seatId: selectedSeat.id,
            }),
          }
        );

        const booking =
          await bookingResponse.json();

        alert(
          `Booking Successful!\nSeat: ${booking.seatNumber}`
        );

        setSelectedSeat(null);

        loadSeats();
        loadEvent();
      },

      theme: {
        color: "#3399cc",
      },
    };
    console.log("ORDER =", order);
console.log("OPTIONS =", options);

    const razorpay =
      new window.Razorpay(options);

   razorpay.on("payment.failed", function (response) {

  console.log("PAYMENT FAILED");
  console.log(response);

  console.log(response.error.code);
  console.log(response.error.description);
  console.log(response.error.reason);

  alert(
    response.error.description
  );
});

    razorpay.open();

  } catch (error) {
    console.error(error);
    alert("Payment failed");
  }
  
};

  if (loading) {
    return <h2>Loading...</h2>;
  }

  if (!event) {
    return <h2>Event not found</h2>;
  }

  return (
    <>
      <Navbar />

      <div className="details-container">
        <div className="details-card">

          <img
            src="https://images.unsplash.com/photo-1501386761578-eac5c94b800a"
            alt={event.title}
            className="details-image"
          />

          <div className="details-content">

            <h1>{event.title}</h1>

            <p className="details-info">
              📍 {event.location}
            </p>

            <p className="details-info">
              📅{" "}
              {new Date(
                event.eventDate
              ).toLocaleString()}
            </p>

            <p className="details-info">
              🎟 Available Seats:
              {" "}
              {event.availableSeats}
            </p>

            <p className="details-info">
              {event.description}
            </p>

            <div className="price-box">
              <h2>₹{event.price}</h2>
            </div>
            <PaymentQRCode amount={event.price} />

          <button
  className="book-seat-btn"
  onClick={handleBookSeat}
>
  Pay & Book Seat
</button>

          </div>
        </div>

        <h2
          style={{
            marginTop: "40px",
            textAlign: "center",
          }}
        >
          Select Your Seat
        </h2>

        <div className="seat-grid">
          {seats.map((seat) => (
            <div
              key={seat.id}
              onClick={() => {
                if (seat.status === "AVAILABLE") {
                  setSelectedSeat(seat);
                }
              }}
              className={`seat
                ${
                  seat.status === "BOOKED"
                    ? "booked"
                    : selectedSeat?.id === seat.id
                    ? "selected"
                    : "available"
                }
              `}
            >
              {seat.seatNumber}
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default EventDetails;