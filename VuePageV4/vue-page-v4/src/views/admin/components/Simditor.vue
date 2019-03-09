<template>
    <textarea ref="editor" title=""></textarea>
</template>

<script>

    import Simditor from 'tar-simditor'
    import 'tar-simditor/styles/simditor.css'
    import 'tar-simditor-markdown'
    import 'tar-simditor-markdown/styles/simditor-markdown.css'

    export default {
        name: "Simditor",
        data() {
            return {
                editor: null,
            }
        },
        props: {
            toolbar: {
                type: Array,
                default: () => ['title', 'bold', 'italic', 'underline', 'fontScale', 'color', 'ol', 'ul', '|', 'code', '|', 'link', 'image', 'hr', '|', 'indent', 'outdent', 'alignment', '|', 'markdown']
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
                upload: {
                    url: '/api/admin/upload_image/',
                    params: null,
                    fileKey: 'image',
                    connectionCount: 3,
                    leaveConfirm: 'Uploading is in progress, are you sure to leave this page?'
                }
            })
        }
    }
</script>

<style scoped>

</style>