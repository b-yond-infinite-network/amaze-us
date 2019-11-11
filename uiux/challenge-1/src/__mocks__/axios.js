const mockAxios = jest.genMockFromModule('axios');
mockAxios.get = jest.fn(() => Promise.resolve({ data: {} }));
mockAxios.create = jest.fn(() => mockAxios)

export function mockAction(response) {
    const action = {
        get: jest.fn(() => {
            return new Promise((resolve, reject) => {
                resolve(response);
            });
        })
    };
    return action;
}

export default mockAxios;
