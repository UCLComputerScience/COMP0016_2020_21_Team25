<template>
    <div>
        <blog-post title="Brainstorming" subtitle="mapping out ideas"
                   description="Lorem ipsum" date="24/10/2020" :latest="latest"
                   author="Ernest Nkansah-Badu" icon="edit">
            <div class="column">
                <placeholder-text></placeholder-text>
            </div>
            <div class="break"></div>
            <div class="column">
                <placeholder-text v-for="_ in [...Array(4).keys()]"></placeholder-text>
            </div>

            <div class="column" id="board-container">
                <div class="image">
                    <img alt="" src="../../assets/images/blog-posts/brainstorm.jpg">
                </div>
                <!--                <iframe class="miro-board" src="https://miro.com/app/embed/o9J_kiX8vAY=/?pres=1&frameId=3074457350944710547"-->
                <!--                        frameBorder="0" scrolling="no" allowFullScreen></iframe>-->
            </div>
        </blog-post>
    </div>
</template>

<script>
    import BlogPost from "./blog-post.vue";
    import PlaceholderText from "../layout/placeholder-text.vue";

    export default {
        name: "brainstorming",
        components: {PlaceholderText, BlogPost},
        props: {
            latest: {
                type: Boolean,
                default: false,
            }
        },
        methods: {
            setBoardWidth() {
                let container = document.getElementById('board-container');
                let board = container.querySelector('miro-board');
                board.style.width = '100%';
                board.style.height = (board.clientWidth * 9 / 16).toString() + 'px';
            }
        },
        mounted() {
            // this.setBoardWidth();
            // window.addEventListener('resize', this.setBoardWidth, true);
        }
    }
</script>

<style scoped>
    .miro-board {
        max-width: 100%;
        width: auto;
        height: auto;
    }

    #board-container {
        margin-left: 32px;
        /*border: 2px solid var(--main);*/
        /*padding: 5px;*/
        flex: 2;
    }

    @media (max-width: 1024px) {
        #board-container {
            margin-left: 0;
        }
    }
</style>