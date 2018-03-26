module.exports = function(data, error){
    this.data = data;
    this.error = error;
  
    let me = this;

    const singleConnection = {
      query: jest.fn(function(){
        //console.log('query args: ', arguments);
        const sql = arguments[0];
        const values = arguments.length > 2 ? arguments[1] : null;
        const cb = arguments.length > 2 ? arguments[2] : arguments[1];
        console.log('me before cb', me);
        cb(me.error, me.data);
      }),
      end: jest.fn()
    };
  
    return {
      createPool: jest.fn(function(){
          return singleConnection
      }),
      createConnection: jest.fn(function(){
        return this.createPool.apply(this, arguments); 
      }),
      escapeId: (p) => p,
      setData: (data) => {
        me.data = data;
      },
      setError: (error) => {
        me.error = error;
      }
    };
  }