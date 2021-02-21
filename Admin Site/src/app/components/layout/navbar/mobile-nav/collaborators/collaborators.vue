<template>
    <div class="collaborators centred">
        <p class="tagline">In collaboration with</p>
        <a aria-label="IBM" class="collab-logo" href="https://www.ibm.com/uk-en" rel="noopener"
           target="_blank">
            <img :src="image('IBM')" alt="IBM" class="image">
        </a>
        <a aria-label="UCL" class="collab-logo" href="https://www.ucl.ac.uk/" rel="noopener"
           target="_blank">
            <img :src="image('UCL')" alt="UCL" class="image">
        </a>
        <a aria-label="NHS" class="collab-logo" href="https://www.nhs.uk/" rel="noopener"
           target="_blank">
            <img :src="image('NHS')" alt="NHS" class="image">
        </a>
        <!--        <a aria-label="Waitrose" class="collab-logo" href="https://www.nhs.uk/" rel="noopener"-->
        <!--           target="_blank">-->
        <!--            <img :src="image('Waitrose')" alt="Waitrose" class="image">-->
        <!--        </a>-->
    </div>
</template>

<script>
export default {
    name: "collaborators",
    methods: {
        image(name) {
            return require(`./../../../../../../assets/images/collaborators/${name}.webp`).default;
        }
    }
}
</script>

<style scoped>
.collaborators {
    margin-top: 4px;
    margin-bottom: 16px;
    flex-direction: column;
}

.collaborators .tagline {
    width: 100%;
    text-align: center;
}

.collab-logo {
    flex: 1;
    cursor: pointer;
    border-radius: var(--border-radius);
    padding: 8px;
}

.collab-logo .image {
    width: 96px;
}

.collab-logo:not(:last-child) {
    margin-bottom: 8px;
}

.collab-logo:hover {
    background: var(--pale-blue);
    color: var(--blue);
}
</style>