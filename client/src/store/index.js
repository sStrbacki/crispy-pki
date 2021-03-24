import axios from 'axios'
import Vuex from 'vuex'
import Vue from 'vue'

import { api } from '../api/index.js'

Vue.use(Vuex)

const state = {
    certificateRequest: null,
    authorities : []
}

const getters = {
    issuerValidity: (state) => (serialNumber) => {
        return state.authorities.find(authority => authority.serialNumber == serialNumber).validity
    }
}

const actions = {
    postCertificate() {
        return axios.post(api.certificate.root, state.certificateRequest)
        .then( res => {
            return res.data
        })
    },
    getAuthorities({commit}){
        return axios.get(api.certificate.authorities)
        .then( res => {
            commit('SET_AUTHORITIES', res.data)
            return res.data
        })

    }
}

const mutations = {
    SET_AUTHORITIES(state, data) {
        state.authorities = data
    }
}

export default new Vuex.Store({
    state,
    getters,
    actions,
    mutations
})