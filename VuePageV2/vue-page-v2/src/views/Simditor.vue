<template>
    <textarea ref="editor" title="" placeholder="Balabala" autofocus></textarea>
</template>

<script>
    import Simditor from "../assets/simditor.js"
    import '../assets/simditor.css'
    // import '../assets/module.js'
    // import '../assets/uploader.js'
    // import '../assets/hotkeys.js'

    export default {
        name: 'Simditor',
        props: {},
        data() {
            return {
                editor: null,
            }
        },
        mounted() {

            this.editor = new Simditor({
                textarea: this.ref.editor
                //optional options
            });
        },
        watch: {}
    }
</script>

<style scoped>
</style>