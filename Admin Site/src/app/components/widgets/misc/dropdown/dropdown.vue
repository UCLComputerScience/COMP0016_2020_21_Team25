<template>
    <div ref="dropdown" class="dropdown noselect" v-on:blur="toggle">
        <p ref="label" class="dropdown-label">{{ title }}</p>
        <div ref="button" class="dropdown-button" v-on:click="toggle">
            {{ activeItem.text }}
        </div>
        <div class="dropdown-container">
            <div ref="content" class="dropdown-elements centred">
                <dropdown-item v-for="item in elements" :item="item"></dropdown-item>
            </div>
        </div>
    </div>
</template>

<script>
import DropdownItem from "./dropdown-item.vue";

export default {
    name: "dropdown",
    components: { DropdownItem },
    computed: {
        elements() {
            const elements = [];
            for (const item of this.items) {
                if (item.text !== this.activeItem.text) {
                    elements.push(item);
                }
            }
            return elements;
        }
    },
    data() {
        return {
            activeItem: {
                text: this.label
            },
            isActive: false,
        };
    },
    props: {
        label: String,
        title: String,
        items: Array,
    },
    methods: {
        selectByText(text) {
            for (let item of this.items) {
                if (item.text === text) {
                    this.activeItem = item;
                    break;
                }
            }
        },
        select(item) {
            this.activeItem = item;
            this.toggle();
        },
        toggle() {
            this.$refs.button.classList.toggle("dropdown-open");
            this.$refs.content.classList.toggle("dropdown-visible");
            if (this.isActive)
                this.close();
            else
                this.open();
        },
        open() {
            this.isActive = true;
            setTimeout(() => {
                document.getElementById('app').addEventListener('click', this.toggle, false);
            }, 250);
        },
        close() {
            this.isActive = false;
            document.getElementById('app').removeEventListener('click', this.toggle, false);
        },
    },
    beforeUnmount() {
        document.getElementById('app').removeEventListener('click', this.toggle, false);
    }
};
</script>

<style scoped>
.dropdown {
    width: 100%;
}

.dropdown-container {
    position: relative;
}

.dropdown-label {
    margin-top: 0;
    margin-bottom: 8px;
    color: #555;
    line-height: 90%;
}

.dropdown-button {
    border-radius: var(--border-radius);
    color: #FFF;
    padding: 10px 24px;
    background: var(--blue);
    font-weight: 700;
    cursor: pointer;
    border: 2px solid var(--blue);
    text-transform: capitalize;
}

.dropdown-button:hover {
    filter: brightness(70%);
}

.dropdown-elements {
    flex-direction: column;
    display: none;
    position: absolute;
    left: 0;
    right: 0;
    z-index: 20;
    border: 2px solid var(--light-blue);
    border-top: 0;
    border-bottom-left-radius: var(--border-radius);
    border-bottom-right-radius: var(--border-radius);
    box-shadow: 0 8px 8px 1px rgba(0, 0, 0, .4);
}

.dropdown-open {
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
    pointer-events: none;
}

.dropdown-visible {
    display: flex;
}
</style>