//const jest = require('jest');
const I = (p) => p;
const escapeId = jest.fn(I);
const util = require('../app/util')(escapeId);

describe('filterByAllSql', () => {
    test('no parameters gives a tautology', () => {
        expect(util.filterByAllSql()).toEqual('1 = 1');
        expect(util.filterByAllSql({})).toEqual('1 = 1');
    })

    test('one parameter', () => {
        expect(util.filterByAllSql({id: 1})).toEqual('id = ?');
        expect(escapeId).toHaveBeenLastCalledWith('id');
    })

    test('many parameters', () => {
        expect(util.filterByAllSql({
            id: 1, 
            name: 'sheep', 
            date: '2018-10-10'
        }))
        .toEqual('id = ? AND name = ? AND date = ?');
    })
})
