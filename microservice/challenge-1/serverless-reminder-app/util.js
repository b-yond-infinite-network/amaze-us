require('./polyfills');


module.exports = function(escapeId){
    return  { 
        filterByAllSql: (filters) => {
            if (!filters || typeof filters !== 'object' || Object.keys(filters).length === 0){
                return '1 = 1';
            }
            
            return Object.keys(filters)
                .map(k => `${escapeId(k)} = ?`)
                .join(' AND ');
        }
    }
}
  