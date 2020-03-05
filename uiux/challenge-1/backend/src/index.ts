import app from "./handlers/server";
import "./handlers/dotenv";

app().listen(process.env.PORT, () => {
  console.log(
    `Listening on port ${process.env.PORT}. You can stop this server with CTRL + C`
  );
});
