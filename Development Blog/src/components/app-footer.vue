<template>
    <div class="footer">
        <footer>
            <div class="footer-title">
                <span class="logo material-icons">face</span>
                <h1>Concierge</h1>
                <div class="break"></div>
                <p class="text">By Calin Hadarean, Ernest Nkansah-Badu & Mohammad Ali Syed</p>
            </div>
            <div class="footer-links">
                <ul>
                    <li class="footer-link">
                        <a href="https://github.com/UCLComputerScience/COMP0016_2020_21_Team25"
                           class="" target="_blank" rel="noopener">
                            <github-icon></github-icon>
                        </a>
                    </li>
                    <li class="footer-link">
                        <a href="https://github.com/IBMIXN/FISE-Social-Isolation-UCL"
                           class="image" target="_blank" rel="noopener" id="FISEv1">
                            <img alt="IBM FISE v1" src="../assets/images/logos/fisev1.png">
                        </a>
                    </li>
                </ul>
            </div>

            <p class="collab-line tagline">in collaboration with</p>

            <div class="collaborators">
                <ul>
                    <li>
                        <a class="collab-logo">
                            <img src="../assets/images/logos/ucl.png" alt="">
                        </a>
                    </li>

                    <li>
                        <a class="collab-logo">
                            <img src="../assets/images/logos/ibm.png" alt="">
                        </a>
                    </li>

                    <li>
                        <a class="collab-logo">
                            <img src="../assets/images/logos/nhs.png" alt="">
                        </a>
                    </li>

                    <li>
                        <a class="collab-logo">
                            <img src="../assets/images/logos/waitrose.png" alt="">
                        </a>
                    </li>
                </ul>

                <br>
                <p class="collab text tagline">in association with University College London,
                    IBM, NHS and Waitrose</p>
                <p class="collab text tagline">Supervised by Dean Mohammedally & John McNamara</p>
            </div>
        </footer>
    </div>
</template>

<script>
    import GithubIcon from "./layout/github-icon.vue";

    export default {
        name: "app-footer",
        components: {GithubIcon},
    }
</script>

<style scoped>
    .footer {
        background: #111;
        height: 300px;
        display: flex;
        align-items: center;
        overflow: hidden;
    }

    footer {
        width: 100%;
        margin: 0;
    }

    .footer-title {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
        padding: 0;
        margin: 24px 0 0;
    }

    h1, .logo {
        color: white;
        margin: 0;
    }

    .logo {
        margin-right: 6px;
        margin-top: -4px;
        zoom: 1.7;
    }

    ul {
        display: flex;
        padding: 0;
        list-style-type: none;
        margin: 0;
    }

    .footer-links, .collaborators {
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 0 auto;
    }

    .footer-links {
        margin-top: 12px;
        margin-bottom: 12px;
    }

    .footer-link {
        cursor: pointer;
    }

    #FISEv1 {
        border: none;
        padding: 0;
        width: 1.6em;
        margin: 0 0 0 24px;
    }

    .collaborators {
        margin-bottom: 16px;
        flex-direction: column;
    }

    .collab {
        text-transform: uppercase;
        font-size: 11px;
    }

    .text, .collab-line {
        color: white;
        text-align: center;
        margin: 8px 0 0;
        padding-left: 16px;
        padding-right: 16px;
    }

    .collab-line {
        text-transform: uppercase;
        padding: 0 16px 16px;
        margin: 0;
    }

    .collab-logo {
        height: 50px;
        margin-right: 24px;
        border: 0;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .collab-logo img {
        max-height: 100%;
        width: auto;
        height: auto;
        margin-left: auto;
        margin-right: auto;
    }

    @media (max-width: 1024px) {
        .footer {
            height: auto;
        }

        .collaborators ul {
            flex-direction: column;
        }

        .collab-logo {
            margin-right: 0;
            margin-bottom: 24px;
            width: 100px;
            height: unset;
        }

        .collab-logo img {
            max-width: 100%;
            width: auto;
            height: auto;
            margin-left: auto;
            margin-right: auto;
        }
    }
</style>