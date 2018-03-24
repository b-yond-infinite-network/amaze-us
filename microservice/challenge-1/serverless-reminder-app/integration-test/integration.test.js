const fetch = require('node-fetch');
const ENDPOINT = 'http://localhost:3000';

describe('Integration tests', () => {
    test('SQL injection from GET parameters does not work', async () => {
        const res1 = await fetch(ENDPOINT + '/reminders?name=;DROP TABLE reminders');
        const afterTableDrop = await fetch(ENDPOINT + '/reminders');
        expect(afterTableDrop.ok).toBeTruthy();

        const res2 = await fetch(ENDPOINT + '/reminders?name=1 OR 1=1');
        expect(res2.ok).toBeTruthy();
        const body2 = await res2.json();
        // expecting an empty response
        expect(body2).toHaveLength(0);
    })

    test('SQL injection from POST values does not work', async () => {
        const res = await fetch(ENDPOINT + '/reminders/321', {
            method: 'POST',
            body: {
                "name": "; DROP TABLE reminders",
                "--; DROP TABLE reminders": "asd"
              }
        });
        // 2nd key does not match the schema
        expect(res.ok).toBeFalsy();

        // table should still exist
        const afterTableDrop = await fetch(ENDPOINT + '/reminders');
        expect(afterTableDrop.ok).toBeTruthy();
    })
});