<template>
<div class="container">
    <h1 class="mb-5">Validate certificate</h1>
    <b-row align-h="center">
        <b-form>
            <b-form-group>
                <b-form-input v-model="serialNumber" placeholder="Enter serial number">
                </b-form-input>
            </b-form-group>
            <b-button class="mb-5" v-on:click="revoke">Validate</b-button>
            <h2 v-bind:class="{ 'text-success': success, 'text-danger': !success }">{{message}}</h2>
        </b-form>
    </b-row>
</div>
</template>

<script>
import axios from 'axios'
import { api } from '../api/index'

export default {
    name: 'ValidateCertificate',
    data: function () {
        return {
            serialNumber: '',
            message: '',
            success: true
        }
    },
    methods: {
        revoke: function () {
            axios.get(api.certificate.root + '/validate/' + this.serialNumber)
            .then(() => {
                this.$notify({
                    group: 'foo',
                    position:'top right',
                    type:'success',
                    width:'200%',
                    text: 'Certificate is valid.'
                })
                this.message = 'Certificate is valid.'
                this.success = true
            })
            .catch(error => {
                this.$notify({
                    group: 'foo',
                    position:'top right',
                    type:'error',
                    width:'200%',
                    text: error.response.data
                })
                this.message = error.response.data
                this.success = false
            })
        }
    }
}
</script>