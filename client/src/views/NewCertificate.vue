<template>
    <b-container>
        <b-row>
            <b-col>
                <h1>New certificate</h1>
            </b-col>
        </b-row>

        <b-modal id="ext-modal" title="Extensions" okOnly>
            <div>

                <b-form-group label="Key usages:" v-slot="{ ariaDescribedby }">

                    <b-form-checkbox
                    id="ku-critical"
                    v-model="certificate.extensions.keyPurposes.critical"
                    value="true"
                    unchecked-value="false"
                    class="mt-0 mb-4"
                    >
                        Critical?
                    </b-form-checkbox>

                    <b-form-checkbox-group
                        id="key-usages"
                        :options="keyUsageOptions"
                        :aria-describedby="ariaDescribedby"
                        v-model="certificate.extensions.keyPurposes.keyPurposes"
                        stacked
                    ></b-form-checkbox-group>
                </b-form-group>

                <b-form-group label="Extended key usages:" v-slot="{ ariaDescribedby }">

                    <b-form-checkbox
                    id="eku-critical"
                    v-model="certificate.extensions.extendedKeyPurposes.critical"
                    value="true"
                    unchecked-value="false"
                    class="mt-0 mb-4"
                    >
                        Critical?
                    </b-form-checkbox>

                    <b-form-checkbox-group
                        id="ext-key-usages"
                        :options="extendedKeyUsageOptions"
                        :aria-describedby="ariaDescribedby"
                        v-model="certificate.extensions.extendedKeyPurposes.extendedKeyPurposes"
                        stacked
                    ></b-form-checkbox-group>
                </b-form-group>

            </div>
        </b-modal>

        <b-row align-h="center" class="mt-4" cols="10" >
            <b-form class="w-50">
                <b-form-group 
                    id="cert-type" 
                    label="Certificate type (*):" 
                    label-for="cert-type-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-select
                        id="cert-type-input"
                        v-model="certificate.certificateType"
                        v-on:change="configureValidityRange"
                        :options="certificateOptions"
                        required
                    >
                    </b-form-select>
                </b-form-group>

                <b-form-group 
                    id="issuers" 
                    label="Issuer (*):" 
                    label-for="issuer-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    v-if="!isSelfSigned"
                    >

                    <b-form-select
                        id="issuer-input"
                        v-on:change="setValidityRange"
                        v-model="certificate.issuerSerialNumber"
                        :options="issuerOptions"
                    >
                    </b-form-select>
                </b-form-group>

                <b-form-group 
                    id="cert-validity" 
                    label="Validity (*):" 
                    label-for="cert-v-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    content-align-sm="left"
                    content-cols-sm="8"
                    >
                    
                    <DatePicker v-model="certificatePeriod"
                                is-range 
                                :min-date="validityPeriod.min" 
                                :max-date="validityPeriod.max">

                        <template v-slot="{ inputValue, inputEvents }">
                            <div class="cal-input-group">
                                <input
                                    :value="inputValue.start"
                                    v-on="inputEvents.start"
                                    class="input-date"
                                />
                                <svg
                                    class="w-2 h-2 mx-2"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    stroke="currentColor"
                                >
                                    <path
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="2"
                                    d="M14 5l7 7m0 0l-7 7m7-7H3"
                                    />
                                </svg>
                                <input
                                    :value="inputValue.end"
                                    v-on="inputEvents.end"
                                    class="input-date"
                                />
                            </div>
                        </template>
                    </DatePicker>

                </b-form-group>

                <b-form-group 
                    id="cert-cn" 
                    label="CN (*):" 
                    label-for="cert-cn-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-cn-input"
                        v-model="certificate.subjectInfo.commonName"
                        placeholder="Enter a common name"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-on" 
                    label="ON (*):" 
                    label-for="cert-on-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-on-input"
                        v-model="certificate.subjectInfo.organizationName"
                        placeholder="Enter an organization name"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-ou" 
                    label="OU:" 
                    label-for="cert-ou-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-ou-input"
                        v-model="certificate.subjectInfo.organizationalUnitName"
                        placeholder="Enter an organization unit name"
                    >
                    </b-form-input>
                </b-form-group>


                <b-form-group 
                    id="cert-gn" 
                    label="Given name:" 
                    label-for="cert-gn-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-gn-input"
                        v-model="certificate.subjectInfo.givenName"
                        placeholder="Enter a given name"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-uid" 
                    label="Certificate UID:" 
                    label-for="cert-uid-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-uid-input"
                        v-model="certificate.subjectInfo.UID"
                        placeholder="Enter certificate UID"
                    >
                    </b-form-input>
                </b-form-group>
                
                <b-form-group 
                    id="cert-surname" 
                    label="Surname:" 
                    label-for="cert-surname-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-surname-input"
                        v-model="certificate.subjectInfo.surname"
                        placeholder="Enter your surname"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-initials" 
                    label="Initials:" 
                    label-for="cert-initials-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-initials-input"
                        v-model="certificate.subjectInfo.initials"
                        placeholder="Enter your initials"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-gender" 
                    label="Gender:" 
                    label-for="cert-gender-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-select
                        id="cert-gender-input"
                        v-model="certificate.subjectInfo.gender"
                        :options="genderOptions"
                    >
                    </b-form-select>
                </b-form-group>

                <b-form-group 
                    id="cert-tn" 
                    label="Telephone number:" 
                    label-for="cert-tn-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-tn-input"
                        v-model="certificate.subjectInfo.telephoneNUmber"
                        placeholder="Enter your telephone number"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-email" 
                    label="Email:" 
                    label-for="cert-email-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-email-input"
                        v-model="certificate.subjectInfo.emailAddress"
                        placeholder="Enter your email address"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-email" 
                    label="Generation:" 
                    label-for="cert-gen-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-gen-input"
                        v-model="certificate.subjectInfo.generation"
                        placeholder="Enter a certificate generation"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-bc" 
                    label="Business category:" 
                    label-for="cert-bc-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-bc-input"
                        v-model="certificate.subjectInfo.businessCategory"
                        placeholder="Enter a business category"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-pa" 
                    label="Postal address:" 
                    label-for="cert-pa-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-pa-input"
                        v-model="certificate.subjectInfo.postalAddress"
                        placeholder="Enter your postal address"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-pcode" 
                    label="Postal code:" 
                    label-for="cert-pcode-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-pcode-input"
                        v-model="certificate.subjectInfo.postalCode"
                        placeholder="Enter your postal code"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-title" 
                    label="Title:" 
                    label-for="cert-title-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-title-input"
                        v-model="certificate.subjectInfo.title"
                        placeholder="Enter your title"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-street" 
                    label="Street:" 
                    label-for="cert-street-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-street-input"
                        v-model="certificate.subjectInfo.street"
                        placeholder="Enter your street name"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-sn" 
                    label="ST:" 
                    label-for="cert-st-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-st-input"
                        v-model="certificate.subjectInfo.stateName"
                        placeholder="Enter your state name"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group 
                    id="cert-l" 
                    label="Locality:" 
                    label-for="cert-l-input"
                    label-cols-sm="4"
                    label-align-sm="left"
                    >
                    
                    <b-form-input
                        id="cert-l-input"
                        v-model="certificate.subjectInfo.localityName"
                        placeholder="Enter your locality name"
                    >
                    </b-form-input>
                </b-form-group>

                <b-row class="mt-4"></b-row>
  
            </b-form>

        </b-row>
        <b-row align-h="center">
             <b-button type="submit" class="mr-5" variant="primary" :disabled="!certValid" @click="submit">Submit</b-button>
        </b-row>
        <b-row class="mt-5"></b-row>
    </b-container>
</template>

<script>
import DatePicker from 'v-calendar/lib/components/date-picker.umd'
import { mapGetters } from 'vuex'

export default {
    name:'NewCertificate',
    components:{
        DatePicker
    },
    computed: {
        isSelfSigned:function() {
            return this.certificate.certificateType === 'SELF_SIGNED'
        },
        certValid:function(){
            return this.certificate.certificateType != null &&
            ( ( this.certificate.certificateType == 'SELF_SIGNED' && this.certificate.issuerSerialNumber == null) 
            || ( this.certificate.certificateType != 'SELF_SIGNED' && this.certificate.issuerSerialNumber != null)) &&
            this.certificatePeriod.start != null && this.certificatePeriod.end != null &&
            this.certificate.subjectInfo.commonName != null && this.certificate.subjectInfo.organizationName
        },
        ...mapGetters(['issuerValidity'])
    },
    data(){
        return {
            certificate:{
                certificateType: null,
                issuerSerialNumber: null,
                certificateValidity:{
                    validFrom:null,
                    validTo:null
                },
                subjectInfo: {
                    UID: null,
                    organizationName: null,
                    organizationalUnitName: null,
                    commonName: null,
                    surname: null,
                    givenName: null,
                    initials: null,
                    gender: null,
                    telephoneNUmber: null,
                    emailAddress: null,
                    generation: null,
                    businessCategory: null,
                    postalAddress: null,
                    postalCode: null,
                    title: null,
                    street: null,
                    stateName: null,
                    localityName: null
                },
                extensions: {
                    keyPurposes: {
                        critical: false,
                        keyPurposes: []
                    },
                    extendedKeyPurposes: {
                        critical: false,
                        extendedKeyPurposes: []
                    }
                }
            },
            certificatePeriod:{
                start:null,
                end:null
            },
            validityPeriod: {
                min: new Date(),
                max: null
            },
            availableAuthorities:[],
            certificateOptions:[
                    { text: 'Select certificate type', value: null },
                    { text: 'Root CA', value: 'SELF_SIGNED'},
                    { text: 'Intermediate', value: 'INTERMEDIATE'},
                    { text: 'End entity', value: 'END_ENTITY'}
            ],
            genderOptions:[
                    { text: 'Choose gender', value: null },
                    { text: 'Male', value: 'M'},
                    { text: 'Female', value: 'F'}
            ],
            issuerOptions:[ { text: 'Select an issuer', value: null } ],
            keyUsageOptions:[
                { text: 'Digital signature', value:'DIGITAL_SIGNATURE' },
                { text: 'Non repudiation', value:'NON_REPUDIATION' },
                { text: 'Key encipherment', value:'KEY_ENCIPHERMENT' },
                { text: 'Data encipherment', value:'DATA_ENCIPHERMENT' },
                { text: 'Key agreement', value:'KEY_AGREEMENT' },
                { text: 'Key cert sign', value:'KEY_CERT_SIGN' },
                { text: 'CRL sign', value:'CRLSIGN' },
                { text: 'Encipher only', value:'ENCIPHER_ONLY' },
                { text: 'Decipher only', value:'DECIPHER_ONLY' },
            ],
            extendedKeyUsageOptions:[
                { text: 'Server auth', value:'SERVER_AUTH' },
                { text: 'Client auth', value:'CLIENT_AUTH' },
                { text: 'Code signing', value:'CODE_SIGNING' },
                { text: 'Email protection', value:'EMAIL_PROTECTION' },
                { text: 'Time stamping', value:'TIME_STAMPING' },
                { text: 'OCSP signing', value:'OCSP_SIGNING' },
            ]
        }
    },
    methods:{
        configureValidityRange(){
            if(this.certificate.certificateType === 'SELF_SIGNED'){
                this.resetValidityRange()
        }
        },
        resetValidityRange(){
            this.validityPeriod.min = new Date()
            this.validityPeriod.max = null
        },

        setValidityRange(){
            var validityRange = this.issuerValidity(this.certificate.issuerSerialNumber)

            this.validityPeriod.min = new Date(validityRange.validFrom)
            if(this.validityPeriod.min < new Date())
                this.validityPeriod = new Date()
            this.validityPeriod.max = new Date(validityRange.validTo)
        },
        submit(){
            this.certificate.certificateValidity.validFrom = this.certificatePeriod.start
            this.certificate.certificateValidity.validTo = this.certificatePeriod.end
            this.certificate.extensions.keyPurposes.critical 
                = this.certificate.extensions.keyPurposes.critical === "true" ? true : false
            this.certificate.extensions.extendedKeyPurposes.critical 
                = this.certificate.extensions.extendedKeyPurposes.critical === "true" ? true : false

            this.$store.state.certificateRequest = this.certificate
            this.$store.dispatch("postCertificate")
            .then(() => {
                this.$notify({
                    group: 'foo',
                    position:'top right',
                    type:'success',
                    width:'200%',
                    text: 'Yey! Your certificate has been successfully issued!'
                })
                this.$router.push('/')
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
    },
    mounted(){
            this.$bvModal.show('ext-modal')

            if(this.certificate.certificateType !== 'SELF_SIGNED'){
                this.$store.dispatch("getAuthorities")
                .then( () => {
                    let authorities = this.$store.state.authorities
                    authorities.forEach(authority => {
                        this.issuerOptions.push({
                            text: authority.subjectCN,
                            value: authority.serialNumber
                        })
                    });
                })
            }
    }
}
</script>

<style scoped>
svg {
    width: 10% !important;
    height: 10% !important;
}
.input-date{
    width: 35% !important;
}
.cal-input-group{
    display: flex;
    justify-items: start !important;
}
</style>