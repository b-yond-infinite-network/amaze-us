const request = require('supertest')
const app = require('../app.js')
describe('Auth Endpoints', () => {

  it('/Post api/v1/auth/signin should user login to the  sys', async () => {
    const input={
      username: 'manager@test.com',
      password: "manager@123",
    }
    const res = await request(app)
      .post('/api/v1/auth/signin')
      .send(input)


    expect(res.statusCode).toEqual(200)
    expect(res.body).toHaveProperty('success')
    expect(res.body).toHaveProperty('token')
    expect(res.body.success).toEqual(true);
   
  })

  it('/Post api/v1/auth/signin should Authentication failed', async () => {
    const input={
      username: 'notfound@test.com',
      password: "manager@123",
    }
    const res = await request(app)
      .post('/api/v1/auth/signin')
      .send(input)
  
    expect(res.statusCode).toEqual(401)
    expect(res.body).toHaveProperty('message')
    expect(res.body.message).toEqual('Authentication failed. User not found.');
   
  })
})





