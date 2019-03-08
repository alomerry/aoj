<template>
    <textarea ref="editor" title="" placeholder="" autofocus>6666</textarea>
</template>

<script>
    import Simditor from 'tar-simditor'
    import 'tar-simditor/styles/simditor.css'
    import 'tar-simditor-markdown'
    import 'tar-simditor-markdown/styles/simditor-markdown.css'

    export default {
        name: 'Simditor',
        data() {
            return {
                editor: null,
            }
        },
        props: {
            toolbar: {
                type: Array,
                default: () => ['title', 'bold', 'italic', 'underline', 'fontScale', 'color', 'ol', 'ul', '|', 'link', 'image', 'hr', '|', 'code', '|', 'indent', 'outdent', 'alignment', '|', 'markdown']
            },
            value: {
                type: String,
                default: ''
            }
        },
        mounted() {
            Simditor.locale = 'en-US';
            this.editor = new Simditor({
                textarea: this.$refs.editor,
                toolbar: this.toolbar,
                pasteImage: true,
                markdown: true,
            });
        },
        watch: {}
    }
</script>

<style scoped>
</style>