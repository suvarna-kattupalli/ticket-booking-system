

function SeatGrid({
  seats = [],
  selectedSeats = [],
  setSelectedSeats,
}) {

  const handleSeatClick = (seat) => {

    console.log("Clicked Seat:", seat);

    if (seat.status === "BOOKED") {
      return;
    }

    const isSelected = selectedSeats.some(
      (s) => s.id === seat.id
    );

    if (isSelected) {

      setSelectedSeats(
        selectedSeats.filter(
          (s) => s.id !== seat.id
        )
      );

    } else {

      setSelectedSeats((prev) => [
        ...prev,
        seat,
      ]);

    }
  };

  return (
    <div>

      <div
        style={{
          display: "flex",
          gap: "20px",
          marginBottom: "20px",
        }}
      >
        <span>🟢 Available</span>
        <span>🔵 Selected</span>
        <span>🔴 Booked</span>
      </div>

      <div
        style={{
          display: "grid",
          gridTemplateColumns:
            "repeat(10, 1fr)",
          gap: "10px",
        }}
      >

        {seats.length === 0 ? (
          <h3>No Seats Available</h3>
        ) : (
          seats.map((seat) => {

            const isSelected =
              selectedSeats.some(
                (s) => s.id === seat.id
              );

            let backgroundColor = "green";

            if (
              seat.status === "BOOKED"
            ) {
              backgroundColor = "red";
            }

            if (isSelected) {
              backgroundColor = "blue";
            }

            return (
              <button
                key={seat.id}
                onClick={() =>
                  handleSeatClick(seat)
                }
                disabled={
                  seat.status === "BOOKED"
                }
                style={{
                  padding: "12px",
                  border: "none",
                  borderRadius: "6px",
                  cursor:
                    seat.status === "BOOKED"
                      ? "not-allowed"
                      : "pointer",
                  backgroundColor,
                  color: "white",
                  fontWeight: "bold",
                }}
              >
                {seat.seatNumber}
              </button>
            );
          })
        )}

      </div>

      <div
        style={{
          marginTop: "20px",
        }}
      >
        <h3>Selected Seats</h3>

        {selectedSeats.length === 0 ? (
          <p>No seats selected</p>
        ) : (
          <p>
            {selectedSeats
              .map(
                (seat) =>
                  seat.seatNumber
              )
              .join(", ")}
          </p>
        )}
      </div>

    </div>
  );
}

export default SeatGrid;