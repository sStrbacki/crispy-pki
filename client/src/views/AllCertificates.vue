<template>
<div class="container">
    <h1>Stored Certificates</h1>
    <b-table v-if="certificates.length > 0" striped hover :items="certificates">

      <template #cell(actions)="row">
        <b-button size="sm" @click="download(row.item)" class="mr-1">
          Download
        </b-button>
      </template>

    </b-table>
    <div v-else><b>There are currently no stored certificates.</b></div>
</div>
</template>

<script>
import axios from 'axios';
import { api } from '../api/index.js'
import { format } from 'date-fns'

export default {
    name: 'AllCertificates',
    data: function () {
        return {
            certificates: []
        }
    },
    methods:{

        download(item){
            axios.get(api.certificate.download + '/' + item.serialNumber, {responseType: 'blob'})
            .then( response => {
                let blob = new Blob([response.data], { type: 'application/octet-stream' })
                let link = document.createElement('a')
                link.href = URL.createObjectURL(blob)
                link.download = item.subjectCN + '.cer'
                link.click()
                URL.revokeObjectURL(link.href)
            })
            
        }
    },
    mounted: function () {
        axios.get(api.certificate.root)
        .then(response => {
            this.certificates = response.data
            this.certificates.forEach(c => {
                c.validFrom = format(new Date(c.validity.validFrom), 'yyyy-MM-dd hh:mm')
                c.validTo = format(new Date(c.validity.validTo), 'yyyy-MM-dd hh:mm')
                c['actions'] = 'Download'
                delete c.validity
            })
            
        })
    }
}

</script>