<template>
    <div class="row">
        <p :id="headerID" class="tagline">{{ subtitle }}</p>
        <h2>
            <span class="title-icon material-icons">{{ icon }}</span>
            {{ title }}</h2>
        <i class="date">
            <span class="material-icons">calendar_today</span>
            {{ author }} on {{ date }}</i>
        <p class="description">{{ description }}</p>
    </div>

    <div class="group">
        <slot></slot>
    </div>
</template>

<script>
    export default {
        name: "blog-post",
        props: {
            subtitle: String,
            title: String,
            icon: String,
            description: String,
            author: String,
            date: String,
            latest: Boolean,
        },
        computed: {
            headerID() {
                if (this.latest) {
                    return "";
                }
                return this.title.replace(/\s+/g, '-').replace('\?', '').toLowerCase();
            }
        }
    }
</script>

<style scoped>
    .group {
        flex-wrap: wrap;
    }

    .date {
        display: flex;
        align-items: center;
    }

    .description {
        color: var(--main);
    }

    .date span {
        margin-right: 28px;
        zoom: 0.75;
        margin-top: -4px;
    }

    .title-icon {
        margin-right: 4px;
    }

    @media (max-width: 1024px) {
        .title-icon {
            display: none;
        }
    }
</style>