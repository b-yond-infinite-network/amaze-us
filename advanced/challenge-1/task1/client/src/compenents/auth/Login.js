import React, { useState } from "react";
import { setUserSession,postHTTP } from "../../Utils/Common";

const useFormInput = (initialValue) => {
  const [value, setValue] = useState(initialValue);

  const handleChange = (e) => {
    setValue(e.target.value);
  };
  return {
    value,
    onChange: handleChange,
  };
};

function Login(props) {
  const [loading, setLoading] = useState(false);
  const username = useFormInput("");
  const password = useFormInput("");
  const [error, setError] = useState(null);

  // handle button click of login form
  const handleLogin = () => {
    setError(null);
    setLoading(true);
    console.log("window.$baseURL", window.$baseURL);
    // axios
    //   .post(`${window.$baseURL}auth/signin`, {
    //     username: username.value,
    //     password: password.value,
    //   })
    postHTTP("auth/signin", {
      username: username.value,
      password: password.value,
    })
      .then((response) => {
        setLoading(false);
        setUserSession(response.data.token, response.data.user);
        props.history.push("/home");
        window.location.reload();
      })
      .catch((error) => {
        setLoading(false);
        if (error.response?.status === 401)
          setError(error.response.data.message);
        else setError("Something went wrong. Please try again later.");
      });
  };

  return (
    <div class="wrapper-login">
      Login :
      <br />
      <br />
      <div>
        Username
        <br />
        <input
          type="text"
          {...username}
          autoComplete="new-password"
          class="form-control form-contrl-genrl"
        />
      </div>
      <div style={{ marginTop: 10 }}>
        Password
        <br />
        <input
          type="password"
          {...password}
          autoComplete="new-password"
          class="form-control form-contrl-genrl"
        />
      </div>
      {error && (
        <>
          <small style={{ color: "red" }}>{error}</small>
          <br />
        </>
      )}
      <br />
      <input
        type="button"
        value={loading ? "Loading..." : "Login"}
        onClick={handleLogin}
        disabled={loading}
      />
      <br />
    </div>
  );
}

export default Login;
