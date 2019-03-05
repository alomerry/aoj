<template>
    <Card>
        <div class="index">
            simditor编辑器
            <br>
            <!--<simditor :options="options" @change="change">
            </simditor>-->
        </div>
    </Card>
</template>

<script>
    import Simditor from '../../components/simditor'

    export default {
        name: "ProblemEdit",
        data() {
            return {
                content: '',
                options: {
                    placeHolder: 'this is placeHolder',
                    toolbarFloat: false,
                    toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|', 'link', 'image', 'hr', '|', 'indent', 'outdent', 'alignment',
                    ],
                    pasteImage: true,
                    upload: {
                        url: `http://...`, //文件上传的接口地址
                        params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
                        fileKey: 'file', //服务器端获取文件数据的参数名
                        connectionCount: 3,
                        leaveConfirm: '正在上传文件'
                    }

                }
            }
        },
        components: {
            Simditor
        },
        methods: {
            change(val) {
                console.log(val)  //以html格式获取simditor的正文内容
            }
        }
    }
</script>

<style scoped>

</style>