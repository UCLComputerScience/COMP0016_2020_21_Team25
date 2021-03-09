<template>
    <div class="text-input centred">
        <label v-if="label !== ''" :for="id">{{ label }} <b v-if="required">*</b></label>
        <div class="input-container centred">
            <span class="icon material-icons noselect">{{ icon }}</span>
            <input :id="id" ref="input" v-model="object[keyName]"
                   :autocomplete="autocomplete" :maxlength="maxlength" 
                   :placeholder="placeholder" :type="type" v-on:input="toggleDelete"
                   v-on:keydown="onKeyPress" v-on:keyup.enter.prevent="onEnter">
            <span ref="delete" class="delete-icon material-icons noselect"
                  v-on:click="clearInput">close</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "text-input",
    props: {
        id: {
            type: String,
            required: true
        },
        type: {
            type: String,
            default: "text",
        },
        icon: String,
        maxlength: {
            type: Number,
            default: 32
        },
        placeholder: String,
        label: { type: String, default: "" },
        noSpaces: { type: Boolean, default: false },
        onEnter: {
            type: Function, default: () => {
            }
        },
        disallowedKeys: {
            type: Array,
            default: [],
        },
        autocomplete: { type: String, default: "off" },
        object: { type: Object },
        keyName: { type: String },
        required: { type: Boolean, default: false }
    },
    data() {
        return {
            ignoreKeys: [],
        }
    },
    methods: {
        focus() {
            this.$refs.input.focus();
        },
        toggleDelete() {
            if (this.object[this.keyName] === "") {
                this.$refs.delete.classList.remove("delete-icon-visible");
            } else {
                this.$refs.delete.classList.add("delete-icon-visible");
            }
        },
        clearInput() {
            this.object[this.keyName] = "";
            this.$refs.input.focus();
            this.$refs.delete.classList.remove("delete-icon-visible");
        },
        onKeyPress(event) {
            if (this.ignoreKeys.includes(event.key)) {
                event.preventDefault();
            }
        },
    },
    created() {
        this.ignoreKeys = [...this.disallowedKeys];
        if (this.noSpaces === true) {
            this.ignoreKeys.push(" ");
        }
    }
};
</script>

<style scoped>
.text-input {
    flex-direction: column;
    align-items: flex-start;
}

label {
    color: #555;
    margin-left: 4px;
    margin-bottom: 8px;
    line-height: 90%;
}

b {
    font-weight: 700;
    color: var(--red);
}

.input-container {
    border-radius: var(--border-radius);
    border: 2px solid rgba(0, 0, 0, .2);
    padding-top: 6px;
    padding-bottom: 6px;
    background: #FFF;
    cursor: text;
    box-shadow: 0 1px 3px 0 rgba(21, 28, 52, .16);
    width: 100%;
    justify-content: flex-start;
}

.input-container, .input-container * {
    transition: 0.15s ease-in-out all;
}

.icon, .delete-icon, input {
    color: #000;
}

.icon {
    margin-left: 8px;
    margin-right: 4px;
    cursor: text;
    color: rgba(0, 0, 0, .2);
}

.delete-icon {
    right: 0;
    cursor: pointer;
    margin-right: 8px;
    margin-left: 4px;
    opacity: 0;
    pointer-events: none;
}

input {
    border: none;
    background: transparent;
    flex: 1;
    font-size: 1em;
    outline: none;
}

@media (pointer: fine) {
    .input-container:not(:focus-within):hover {
        filter: brightness(80%);
    }

    .input-container:hover .icon {
        color: var(--light-blue);
    }

    .delete-icon:hover {
        color: var(--red);
    }
}

.text-input:focus-within .input-container {
    border: 2px solid var(--green);
}

.text-input:focus-within .icon {
    color: var(--green);
}

.delete-icon.delete-icon-visible {
    opacity: 1;
    pointer-events: auto;
}

::-ms-clear, ::-ms-reveal {
    display: none;
    width: 0;
    height: 0;
}

@media (max-width: 320px) {
    .material-icons {
        font-size: 16px;
    }

    input {
        font-size: 13px;
    }
}
</style>