<template>
<div class="container">
    <h1 class="mb-5">Revoke certificate</h1>
    <b-row align-h="center">
        <b-form>
            <b-form-group>
                <b-form-input v-model="serialNumber" placeholder="Enter serial number">
                </b-form-input>
            </b-form-group>
            <b-button v-on:click="revoke">Revoke</b-button>
        </b-form>
    </b-row>
</div>
</template>

<script>
import axios from 'axios'
import { api } from '../api/index'

export default {
    name: 'RevokeCertificate',
    data: function () {
        return {
            serialNumber: ''
        }
    },
    methods: {
        revoke: function () {
            axios.delete(api.certificate.root + '/' + this.serialNumber)
            .then(() => {
                this.$notify({
                    group: 'foo',
                    position:'top right',
                    type:'success',
                    width:'200%',
                    text: 'Your certificate has been successfully revoked!'
                })
            })
            .catch(() => {
                this.$notify({
                    group: 'foo',
                    position:'top right',
                    type:'error',
                    width:'200%',
                    text: 'Oh no! An error occurred, please try again later :('
                })
            })
        }
    }
}
</script>
