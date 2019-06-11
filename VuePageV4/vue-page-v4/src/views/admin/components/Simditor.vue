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
                currentValue: this.value,
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
                markdown: false,
                upload: {
                    url: '/api-oj/api-oj/simditorImage',
                    params: null,
                    fileKey: 'image',//server端获取文件数据的參数名
                    connectionCount: 3,
                    leaveConfirm: 'Uploading is in progress, are you sure to leave this page?'
                },
            });
            this.editor.uploader.on('uploadsuccess', (function(_this) {
                return function(e, file, result) {
                    console.log(result);
                    var $img, img_path, msg;
                    if (!file.inline) {
                        return;
                    }
                    $img = file.img;
                    if (!($img.hasClass('uploading') && $img.parent().length > 0)) {
                        return;
                    }
                    if (typeof result !== 'object') {
                        try {
                            result = $.parseJSON(result);
                        } catch (_error) {
                            e = _error;
                            result = {
                                success: false
                            };
                        }
                    }
                    if (result.success === false) {
                        msg = result.msg || _this._t('uploadFailed');
                        alert(msg);
                        img_path = _this.defaultImage;
                    } else {
                        img_path = result.file_path;
                    }
                    img_path = result.data.url;
                    
                    console.log(result.data.url);
                    $img.attr("src",result.data.url);
                };
            })(this));
            this.editor.on('valuechanged', (e, src) => {
                this.currentValue = this.editor.getValue();
            });
            this.editor.on('decorate', (e, src) => {
                this.currentValue = this.editor.getValue();
            });
            this.editor.setValue(this.value);
        },
        methods:{
            loadImage($img, src, callback) {
                var $mask, img, positionMask;
                positionMask = (function(_this) {
                    return function() {
                        var imgOffset, wrapperOffset;
                        imgOffset = $img.offset();
                        wrapperOffset = _this.editor.wrapper.offset();
                        return $mask.css({
                            top: imgOffset.top - wrapperOffset.top,
                            left: imgOffset.left - wrapperOffset.left,
                            width: $img.width(),
                            height: $img.height()
                        }).show();
                    };
                })(this);
                $img.addClass('loading');
                $mask = $img.data('mask');
                if (!$mask) {
                    $mask = $('<div class="simditor-image-loading">\n  <div class="progress"></div>\n</div>').hide().appendTo(this.editor.wrapper);
                    positionMask();
                    $img.data('mask', $mask);
                    $mask.data('img', $img);
                }
                img = new Image();
                img.onload = (function(_this) {
                    return function() {
                        var height, width;
                        if (!$img.hasClass('loading') && !$img.hasClass('uploading')) {
                            return;
                        }
                        width = img.width;
                        height = img.height;
                        $img.attr({
                            src: src,
                            width: width,
                            height: height,
                            'data-image-size': width + ',' + height
                        }).removeClass('loading');
                        if ($img.hasClass('uploading')) {
                            _this.editor.util.reflow(_this.editor.body);
                            positionMask();
                        } else {
                            $mask.remove();
                            $img.removeData('mask');
                        }
                        if ($.isFunction(callback)) {
                            return callback(img);
                        }
                    };
                })(this);
                img.onerror = function() {
                    if ($.isFunction(callback)) {
                        callback(false);
                    }
                    $mask.remove();
                    return $img.removeData('mask').removeClass('loading');
                };
                return img.src = src;
            },
        },
        watch: {
            'value'(val) {
                if (this.currentValue !== val) {
                    this.currentValue = val;
                    this.editor.setValue(val)
                }
            },
            'currentValue'(newVal, oldVal) {
                if (newVal !== oldVal) {
                    this.$emit('change', newVal);
                    this.$emit('input', newVal)
                }
            }
        }
    }
</script>

<style scoped>

</style>