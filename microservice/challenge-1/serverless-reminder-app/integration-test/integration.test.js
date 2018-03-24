const fetch = require('node-fetch');
const ENDPOINT = 'http://localhost:3000';

describe('Integration tests', () => {
    test('SQL injection from GET parameters does not work', async () => {
        const res1 = await fetch(ENDPOINT + '/reminders?name=;DROP TABLE reminders');
        expect(res1.ok).toBeTruthy();

        const res2 = await fetch(ENDPOINT + '/reminders?name=1 OR 1=1');
        expect(res2.ok).toBeTruthy();
        const body2 = await res2.json();
        // expecting an empty response
        expect(body2).toHaveLength(0);
    })
});