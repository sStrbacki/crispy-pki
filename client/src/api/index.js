let host = 'http://localhost:8081'

let certificate = '/api/certificate'

export const api = {
    certificate: {
        root: host + certificate,
        authorities: host + certificate + '/authorities',
        download: host + certificate + '/download'
    }
}