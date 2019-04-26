<template>
    <div>
        <Modal v-model="modal_falg" width="900">
            <div slot="header">
                <span style="font-size: 20px;font-weight: 400;">Create Announcement</span>
            </div>
            <div>
                <Input value="666"></Input>
            </div>
            <div slot="footer" style="height: 25px">
                <Button>555</Button>
            </div>
        </Modal>
        <Card style="margin: 15px">
            <div slot="title" style="height: 30px;padding-top: 10px">
                <span style="font-size: 25px;font-weight: 400;">Problems</span>
            </div>
            <Table :columns="anno_column" :data="anno_data"></Table>
            <Button>66</Button>
        </Card>
    </div>
</template>

<script>
    import Simditor from '../../components/Simditor'

    export default {
        name: "announcement",
        data() {
            return {
                modal_falg: false,
                anno_column: [
                    {
                        title: "Title",
                        key: "",
                        render: (h, params) => {
                            h("span", {})
                        }
                    },
                    {
                        title: "Create Time",
                        key: "",
                        render: (h, params) => {
                            h("span", {})
                        }
                    },
                    {
                        title: "Last Update Time",
                        key: "",
                        render: (h, params) => {
                            h("span", {})
                        }
                    },
                    {
                        title: "Anthor",
                        key: "",
                        render: (h, params) => {
                            h("span", {})
                        }
                    },
                    {
                        title: "Visable",
                        key: "",
                        render: (h, params) => {
                            h("span", {})
                        }
                    },
                    {
                        title: "Option",
                        key: "",
                        render: (h, params) => {
                            h("span", {})
                        }
                    }
                ],
                anno_data: [],
            }
        },
        components: {
            Simditor,
        },
    }
</script>

<style scoped>

</style>