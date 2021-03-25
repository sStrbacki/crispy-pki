import NewCertificate from './../views/NewCertificate'
import AllCertificates from './../views/AllCertificates'
import RevokeCertificate from './../views/RevokeCertificate'
import ValidateCertificate from './../views/ValidateCertificate'

export default [
    {
      path: '/',
      component: AllCertificates
    },
    {
      path: '/new',
      component: NewCertificate
    },
    {
      path: '/revoke',
      component: RevokeCertificate
    },
    {
      path: '/validity',
      component: ValidateCertificate
    }
];