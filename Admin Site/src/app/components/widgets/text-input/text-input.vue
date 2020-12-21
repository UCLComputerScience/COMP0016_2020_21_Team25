<template>
    <div class="text-input centred">
        <label :for="id" v-if="label !== ''">{{ label }}</label>
        <div class="input-container centred">
            <span class="icon material-icons">{{ icon }}</span>
            <input :autocomplete="autocomplete" :id="id" :maxlength="maxlength"
                   :placeholder="placeholder" :type="type" ref="input"
                   v-model="variable" v-on:keydown.space="onKeyPress"
                   v-on:keyup.enter="onEnter">
            <span class="close-icon material-icons noselect"
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
            variable: String,
            label: {type: String, default: ""},
            noSpaces: {type: Boolean, default: false},
            onEnter: {type: Function},
            autocomplete: {type: String, default: "off"}
        },
        methods: {
            clearInput() {
                this.variable = ""
                this.$refs.input.focus();
            },
            onKeyPress(event) {
                if (this.noSpaces) {
                    event.preventDefault()
                }
            }
        }
    }
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
    }

    .input-container {
        border-radius: var(--border-radius);
        border: 2px solid transparent;
        padding-top: 6px;
        padding-bottom: 6px;
        background: #fff;
        cursor: text;
        box-shadow: 0 1px 3px 0 rgba(21, 28, 52, .16);
        width: 100%;
    }

    .input-container, .input-container * {
        transition: 0.2s ease-in-out all;
    }

    .icon, .close-icon, input {
        color: #000;
    }

    .icon {
        margin-left: 8px;
        margin-right: 4px;
        cursor: text;
    }

    .close-icon {
        right: 0;
        cursor: pointer;
        margin-right: 8px;
        margin-left: 4px;
    }

    input {
        border: none;
        background: transparent;
        flex: 1;
        font-size: 1em;
        outline: none;
    }

    @media (pointer: fine) {
        .input-container:hover {
            border: 2px solid var(--light-blue);
        }

        .input-container:hover .icon {
            color: var(--light-blue);
        }

        .close-icon:hover {
            color: var(--red);
        }
    }

    .text-input:focus-within .input-container {
        border: 2px solid var(--green);
    }

    .text-input:focus-within .icon {
        color: var(--green);
    }

    input:not(:valid) ~ .close-icon {
        opacity: 0;
        pointer-events: none;
    }

    ::-ms-clear, ::-ms-reveal {
        display: none;
        width: 0;
        height: 0;
    }
</style>