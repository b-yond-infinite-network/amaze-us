import { Server } from "http";
import chai, { assert, expect } from "chai";
import chaiHTTP from "chai-http";
import { ITrack } from "../../../../../shared/";

chai.use(chaiHTTP);

export default (app: Server) => {
  describe("testing /api/search/track", () => {
    it("Should return 200 status and response body as an array, when a valid request is made", done => {
      chai
        .request(app)
        .get("/api/search/track")
        .query({
          name: "What is life?",
          lyricsRequired: true,
          page: 1,
          pageSize: 20
        })
        .end((err, res) => {
          expect(res.status).to.be.equal(200);
          assert.isArray(res.body, "Response body should be an Array");
          expect(res.body.length).to.be.equal(20);
          const trackObject: ITrack = res.body[0];
          expect(trackObject).to.have.property("name");
          expect(trackObject).to.have.property("rating");
          expect(trackObject).to.have.property("explicit");
          expect(trackObject).to.have.property("artistID");
          expect(trackObject).to.have.property("artistName");
          expect(trackObject).to.have.property("hasLyrics");
          expect(trackObject).to.have.property("numFavorite");
        });
      done();
    });

    it("Should return 200 status and response body as an array, when a valid request (only artistID) is made", done => {
      chai
        .request(app)
        .get("/api/search/track")
        .query({
          artistID: "118",
          lyricsRequired: true,
          page: 1,
          pageSize: 5
        })
        .end((err, res) => {
          expect(res.status).to.be.equal(200);
          assert.isArray(res.body, "Response body should be an Array");
          expect(res.body.length).to.be.equal(5);
          const trackObject: ITrack = res.body[0];
          expect(trackObject).to.have.property("name");
          expect(trackObject).to.have.property("rating");
          expect(trackObject).to.have.property("explicit");
          expect(trackObject).to.have.property("artistID");
          expect(trackObject).to.have.property("artistName");
          expect(trackObject).to.have.property("hasLyrics");
          expect(trackObject).to.have.property("numFavorite");
        });
      done();
    });

    it("Should return 400 status and response body as an object, when an invalid/incomplete request is made", done => {
      chai
        .request(app)
        .get("/api/search/track")
        .query({ name: "What is life?" })
        .end((err, res) => {
          expect(res.status).to.be.equal(400);
          assert.isObject(JSON.parse(res.text), "Response should be an Object");
          expect(JSON.parse(res.text)).to.have.property("message");
          expect(JSON.parse(res.text).message).to.be.equal(
            "Bad request. All queries must be present"
          );
        });
      done();
    });
  });
};
