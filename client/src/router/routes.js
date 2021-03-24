import NewCertificate from './../views/NewCertificate'
import AllCertificates from './../views/AllCertificates'

export default [
    {
      path: '/',
      component: AllCertificates
    },
    {
      path: '/new',
      component: NewCertificate
    }
];