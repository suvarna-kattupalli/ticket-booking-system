import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import "../styles/MyBookings.css";

function MyBookings() {

  const [bookings, setBookings] = useState([]);

  

  useEffect(() => {

  const fetchBookings = async () => {

    try {

      const token = localStorage.getItem("token");

      const response = await fetch(
        "http://localhost:8081/api/bookings/my-bookings",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const data = await response.json();

      setBookings(data);

    } catch (error) {
      console.error(error);
    }
  };

  fetchBookings();

}, []);

  return (
    <>
      <Navbar />

      <div className="bookings-container">

        <h1>My Bookings</h1>

        {bookings.length === 0 ? (
          <div className="empty-booking">
            No bookings found
          </div>
        ) : (
          <div className="booking-grid">

            {bookings.map((booking) => (
              <div
                key={booking.bookingId}
                className="booking-card"
              >
                <h2>{booking.eventTitle}</h2>

                <p>
                  🎟 Seat:
                  {" "}
                  {booking.seatNumber}
                </p>

                <p>
                  💰 ₹
                  {booking.amount}
                </p>

                <p>
                  Status:
                  {" "}
                  {booking.paymentStatus}
                </p>
              </div>
            ))}

          </div>
        )}

      </div>
    </>
  );
}

export default MyBookings;