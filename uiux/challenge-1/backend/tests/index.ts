import { Server } from "http";
import chai from "chai";
import chaiHTTP from "chai-http";

import app from "../src/handlers/server";
import "../src/handlers/dotenv";
import lyricsAPITests from "./api/lyrics";
import artistAPITests from "./api/search/artist";
import trackAPITests from "./api/search/track";

chai.use(chaiHTTP);

let testServer: Server = app().listen(6000);

describe("Initializing tests", () => {
  describe("Testing server", () => {
    it("Should return 404 error when an unknown URL is requested", done => {
      chai
        .request(testServer)
        .get("/unknown/path")
        .end((err, res) => {
          chai.expect(res.status).to.be.equal(404);
        });
      done();
    });
  });

  describe("Testing APIs", () => {
    lyricsAPITests(testServer);
    artistAPITests(testServer);
    trackAPITests(testServer);
  });

  after(done => {
    testServer.close();
    done();
  });
});
