import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import EventDetails from "./pages/EventDetails";
import SeatGrid from "./components/SeatGrid";
import BookingSuccess from "./pages/BookingSuccess";
import MyBookings from "./pages/MyBookings";
import "./styles/Home.css";
import Home from "./pages/Home";



function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/events/:id" element={<EventDetails />} />
        <Route path="/seats" element={<SeatGrid />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/booking-success" element={<BookingSuccess />} />
        <Route path="/my-bookings" element={<MyBookings />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;