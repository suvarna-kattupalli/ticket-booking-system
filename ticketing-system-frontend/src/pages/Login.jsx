import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
 const navigate = useNavigate();
    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");

    const handleLogin = async (e) => {

        e.preventDefault();

        try {

           const response = await fetch(
  "http://localhost:8081/api/auth/login",
  {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email,
      password,
    }),
  }
);

const data = await response.json();


console.log("LOGIN RESPONSE:", data);

if (response.ok) {
  localStorage.setItem("token", data.token);
  localStorage.setItem("userId", data.userId);

  navigate("/dashboard");
} else {
  alert(data.message || "Login Failed");
}
        } catch (error) {

            console.error(error);

            alert("Something went wrong");
        }
    };

    return (

        <div style={styles.container}>

            <div style={styles.card}>

                <div style={styles.leftSection}>

                    <h1 style={styles.title}>
                        Welcome Back
                    </h1>

                    <p style={styles.subtitle}>
                        Login to continue booking
                        your favorite events.
                    </p>

                </div>

                <div style={styles.rightSection}>

                    <h2 style={styles.loginText}>
                        Login
                    </h2>

                    <form
                        onSubmit={handleLogin}
                        style={styles.form}
                    >

                        <input
                            type="email"
                            placeholder="Enter Email"
                            value={email}
                            onChange={(e) =>
                                setEmail(
                                    e.target.value
                                )
                            }
                            style={styles.input}
                        />

                        <input
                            type="password"
                            placeholder="Enter Password"
                            value={password}
                            onChange={(e) =>
                                setPassword(
                                    e.target.value
                                )
                            }
                            style={styles.input}
                        />

                        <button
                            type="submit"
                            style={styles.button}
                        >
                            Login
                        </button>

                    </form>

                </div>

            </div>

        </div>
    );
}

const styles = {

    container: {
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background:
            "linear-gradient(to right, #141e30, #243b55)",
        fontFamily: "Arial",
    },

    card: {
        width: "850px",
        height: "450px",
        background: "#fff",
        borderRadius: "20px",
        overflow: "hidden",
        display: "flex",
        boxShadow:
            "0 10px 30px rgba(0,0,0,0.3)",
    },

    leftSection: {
        flex: 1,
        background:
            "linear-gradient(to bottom right, #4facfe, #00f2fe)",
        color: "white",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        padding: "40px",
    },

    title: {
        fontSize: "42px",
        marginBottom: "20px",
    },

    subtitle: {
        fontSize: "18px",
        textAlign: "center",
        lineHeight: "1.6",
    },

    rightSection: {
        flex: 1,
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        padding: "50px",
    },

    loginText: {
        fontSize: "34px",
        marginBottom: "30px",
        color: "#243b55",
    },

    form: {
        display: "flex",
        flexDirection: "column",
        gap: "20px",
    },

    input: {
        padding: "15px",
        fontSize: "16px",
        borderRadius: "10px",
        border: "1px solid #ccc",
        outline: "none",
    },

    button: {
        padding: "15px",
        background: "#243b55",
        color: "white",
        border: "none",
        borderRadius: "10px",
        fontSize: "18px",
        cursor: "pointer",
        transition: "0.3s",
    },
};

export default Login;