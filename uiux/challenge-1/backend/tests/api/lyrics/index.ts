import { Server } from "http";
import chai, { assert, expect } from "chai";
import chaiHTTP from "chai-http";

chai.use(chaiHTTP);

export default (app: Server) => {
  describe("testing /api/lyrics/:id", () => {
    it("Should return 200 status, with successful message", done => {
      chai
        .request(app)
        .get("/api/lyrics/somerandomid")
        .end((err, res) => {
          expect(res.status).to.be.equal(200);
          assert.isObject(res.body, "Response body should be an object");
        });
      done();
    });
  });
};
