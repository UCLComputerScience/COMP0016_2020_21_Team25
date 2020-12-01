<template>
    <div>
        <blog-post title="Gathering Requirements" subtitle="speaking to the client"
                   description="Lorem ipsum" date="23/10/2020"
                   author="Ernest Nkansah-Badu" icon="list">
            <div class="column">
                <placeholder-text></placeholder-text>
            </div>

            <div class="break"></div>

            <h3>Asking the right questions</h3>
            <div class="break"></div>

            <div class="column" style="margin-right: 64px">
                <div class="image">
                    <img src="../../assets/images/blog-posts/questions.png" alt="">
                </div>
            </div>

            <div class="text-column column">
                <placeholder-text v-for="_ in [...Array(5).keys()]"></placeholder-text>
            </div>

            <div class="break"></div>

            <h3 style="margin-top: 64px; margin-bottom: 0;">Finalising Requirements</h3>
            <div class="break"></div>
            <div class="text-column column">
                <placeholder-text v-for="_ in [...Array(4).keys()]"></placeholder-text>
            </div>

            <div class="column" style="margin-left: 64px">
                <div class="image">
                    <img src="../../assets/images/blog-posts/requirements.png" alt="">
                </div>
            </div>

            <div class="break"></div>

            <div class="column">
                <placeholder-text></placeholder-text>
            </div>


        </blog-post>
    </div>
</template>

<script>
    import BlogPost from "./blog-post.vue";
    import PlaceholderText from "../layout/placeholder-text.vue";

    export default {
        name: "gathering-requirements",
        components: {PlaceholderText, BlogPost},
        props: {
            latest: {
                type: Boolean,
                default: false,
            }
        },
    }
</script>

<style scoped>
    .text-column {
        flex: 3;
        margin-top: 0;
    }
</style>