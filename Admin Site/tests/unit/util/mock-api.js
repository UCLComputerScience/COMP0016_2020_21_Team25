const mockResponse = {}

const mock = (method, response) => {
    return jest.spyOn(api, method).mockImplementationOnce(() => {
        return Promise.resolve({
            json: () => Promise.resolve(response)
        })
    })
}

const login = mock("login", mockResponse);
const register = mock("login", mockResponse);
const admin = mock("login", mockResponse);
