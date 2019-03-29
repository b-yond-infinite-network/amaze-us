"use strict";

const http = require("http");

const app = require("./src/app");
const config = require("./src/config");

app.set("port", config.server.port);
const server = http.createServer(app);
server.listen(config.server.port);
server.on("error", error => {
});

server.on("listening", () => console.log(`Server is listening on port ${config.server.port}`));

process.on("SIGINT", () => {
  console.log("Stopping server.");
  server.close(() => {
    console.log("Server stopped.");
  });
});
