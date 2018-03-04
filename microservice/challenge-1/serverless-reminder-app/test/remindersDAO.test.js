
  
  // test.js
  jest.mock('mysql'); // this happens automatically with automocking
  const mockMysql = require('mysql');

  test('mocking is working', () => {
    expect(mockMysql).toHaveProperty('createPool');
    const pool = mockMysql.createPool({});
    // console.log(pool);
    // expect(pool).toHaveProperty('query');
  });
  
 //console.log(mockMysql);