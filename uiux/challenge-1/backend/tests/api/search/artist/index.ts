import { Server } from "http";
import chai, { assert, expect } from "chai";
import chaiHTTP from "chai-http";

chai.use(chaiHTTP);

export default (app: Server) => {
  describe("testing /api/search/artist", () => {
    it("Should return 200 status and response body array, when a valid request is made", done => {
      chai
        .request(app)
        .get("/api/search/artist/")
        .query({ name: "justin", page: 1, pageSize: 10 })
        .end((err, res) => {
          expect(res.status).to.be.equal(200);
          assert.isArray(res.body, "Response body should be an array");
          expect(res.body.length).to.be.equal(10);
        });
      done();
    });

    it("Should return 400 status with error message, when an invalid query parameter request is made", done => {
      chai
        .request(app)
        .get("/api/search/artist/")
        .query({ name: "justin" })
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
