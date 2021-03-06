<template>
    <div class="authors centred">
        <p class="authors">By Calin Hadarean, Ernest Nkansah-Badu, Mohammad Ali Syed</p>
        <p class="authors">Supervised by Dean Mohammedally & John McNamara</p>

        <div class="links centred noselect">
            <a aria-label="Home" class="link" href="https://fise-concierge.web.app"
               rel="noopener" target="_blank">
                <span class="icon material-icons">face</span>
            </a>
            <a aria-label="View on GitHub" class="link"
               href="https://github.com/UCLComputerScience/COMP0016_2020_21_Team25"
               rel="noopener" target="_blank">
                <github-icon colour="#2848d1" height="2em" width="2em"></github-icon>
            </a>

            <a id="FISEv1" aria-label="View repo for IBM FISE v1"
               class="link image" href="https://github.com/IBMIXN/FISE-Social-Isolation-UCL"
               rel="noopener" target="_blank">
                <img :src="image('IBM-FISE-v1')"
                     alt="Source code repository for IBM FISE v1">
            </a>
        </div>
    </div>
</template>

<script>
import GithubIcon from "../../../widgets/misc/github-icon.vue";

export default {
    name: "authors",
    components: { GithubIcon },
    methods: {
        image(name) {
            return require(`./../../../../../assets/images/collaborators/${name}.webp`).default;
        }
    }
};
</script>

<style scoped>
.authors {
    flex-direction: column;
}

.authors > p {
    text-align: center;
    margin-top: 0;
    margin-bottom: 0;
    font-size: 10px;
}

.links {
    margin-top: 16px;
}

.link {
    color: var(--blue);
    width: 2em;
    height: 2em;
    border-radius: var(--border-radius);
}

.link:not(:last-child) {
    margin-right: 8px;
}

.link .icon {
    font-size: 2em;
    color: inherit;
}

.link:hover {
    background: var(--pale-blue);
    color: var(--blue);
}
</style>