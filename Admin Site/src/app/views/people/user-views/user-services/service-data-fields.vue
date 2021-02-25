<template>
    <text-input v-for="[field, value] in Object.entries(fields)" :key="field"
                :placeholder="value.toString()" :label="formatField(field)" :ref="setRef"
                type="text" :id="field" autocomplete="off" :no-spaces="false"
                :object="serviceData" :key-name="field" :required="false"></text-input>
    <flat-button text="Update" v-on:click="updateServiceData" v-if="Object.keys(fields).length > 0"></flat-button>
</template>

<script>
import TextInput from "../../../../components/widgets/text-input/text-input.vue";
import FlatButton from "../../../../components/widgets/buttons/flat-button.vue";

export default {
    name: "service-data-fields",
    components: {FlatButton, TextInput},
    props: {serviceName: String},
    data() {
        return {
            serviceData: {},
            fields: {},
            inputRefs: [],
        }
    },
    created() {
        this.setDefaultData();
    },
    beforeUpdate() {
        this.inputRefs = [];
    },
    methods: {
        setRef(el) {
            this.inputRefs.push(el);
        },
        clearAll() {
            for (const input of this.inputRefs) {
                input.clearInput();
            }
        },
        formatField(field) {
            return field.replaceAll("_", " ").toLowerCase();
        },
        async setDefaultData() {
            this.fields = await this.$store.getters["service/dataFields"](this.serviceName);
            for (const key of Object.keys(this.fields)) {
                this.serviceData[key] = null;
            }
        },
        async updateServiceData() {
            const form = {
                serviceName: this.serviceName,
                ...this.serviceData
            }
            await this.$store.dispatch("service/updateDataFields", form);
            this.clearAll();
            await this.setDefaultData();
        }
    },
}
</script>

<style>
.service .text-input label, .service .text-input input {
    text-transform: capitalize;
}

.service .text-input {
    margin-bottom: 8px;
    width: 100%;
}

.service .text-input label {
    color: #FFF;
}

.services-content .service .data-row .flat-button {
    margin-bottom: 8px;
}
</style>