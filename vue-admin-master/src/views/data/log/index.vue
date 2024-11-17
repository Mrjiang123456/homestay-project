<template>
  <div>
    <div>
      <pane-head :route="route" />
    </div>
    <div>
      <div class="btns">
        <el-popconfirm
          width="220"
          confirm-button-text="确认"
          cancel-button-text="取消"
          icon-color="#626AEF"
          title="是否确认删除?"
          @confirm="deleteConfirm"
        >
          <template #reference>
            <el-button :icon="Delete" type="danger" size="small">
              删除
            </el-button>
          </template>
        </el-popconfirm>
      </div>
    </div>
    <el-table
      :data="tableData.list"
      style="width: 100%"
      @selection-change="handleSelectChange"
      :header-cell-style="{
        backgroundColor: dark ? '#1D1E1F' : '#fff',
        transition: 'all 0.2s',
        fontFamily: 'myFont',
        fontSize: '15px',
        color: dark ? '#fff' : '#000',
      }"
      :row-style="{
        background: dark ? '#1D1E1F' : '#fff',
        transition: 'all 0.2s',
        color: dark ? '#fff' : '#000',
        fontFamily: 'myFont',
        fontSize: '14px',
      }"
    >
      <el-table-column type="selection" />
      <el-table-column prop="id" label="id" width="80px" />
      <el-table-column prop="userId" label="用户id" width="150px" />
      <el-table-column prop="nickname" label="昵称" width="180px" />
      <el-table-column prop="isLogin" label="类型" width="150px">
        <template #default="scope">
          <el-tag
            :type="
              scope.row.isLogin === 1 && scope.row.content.includes('成功')
                ? 'warning'
                : scope.row.content.includes('失败')
                  ? 'danger'
                  : 'primary'
            "
          >
            {{ scope.row.isLogin === 0 ? "行为" : "登录" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="内容">
        <template #default="scope">
          <el-tag
            :type="
              scope.row.isLogin === 1 && scope.row.content.includes('成功')
                ? 'warning'
                : scope.row.content.includes('失败')
                  ? 'danger'
                  : 'primary'
            "
          >
            {{ scope.row.content }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="path" label="路径" />
      <el-table-column label="创建时间" show-overflow-tooltip>
        <template #default="scope">
          <div class="flex-box">
            <el-icon>
              <Clock />
            </el-icon>
            <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" show-overflow-tooltip>
        <template #default="scope">
          <div class="flex-box">
            <el-icon>
              <Clock />
            </el-icon>
            <span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-info">
      <el-pagination
        size="small"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[1, 2, 5, 10]"
        :total="tableData.total"
        v-model:current-page="pageParams.pageNum"
        v-model:page-size="pageParams.pageSize"
        @size-change="getPage"
        @current-change="getPage"
      />
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router"
import { getLogInfoPage, deleteLogInfo } from "@/api/index"
import { nextTick, onMounted, reactive, ref } from "vue"
import { Clock, Delete, Edit, Plus } from "@element-plus/icons-vue"
import { ElMessage } from "element-plus"
import { useSettingStore } from "@/stores/modules/setting"
import { storeToRefs } from "pinia"
const settingStore = useSettingStore()
const { dark } = storeToRefs(settingStore)
const route = useRoute()
// 表格数据
const tableData = reactive({
  list: [],
  total: 0,
})

const form = reactive({
})
// 分页
const pageParams = reactive({
  pageNum: 1,
  pageSize: 10,
})

onMounted(() => {
  getPage()
})

const getPage = async () => {
  await getLogInfoPage(pageParams).then((res) => {
    const { records, total } = res.data
    tableData.list = records
    tableData.total = total
  })
}

const selectIds = ref([])
const handleSelectChange = (val) => {
  selectIds.value = val.map((item) => item.id)
}

const deleteConfirm = async () => {
  if (selectIds.value.length === 0) {
    return ElMessage.error("请至少选中一项数据")
  }
  await deleteLogInfo(selectIds.value).then((res) => {
    if (res.code === "200") {
      ElMessage.success(res.message)
      getPage()
    } else {
      ElMessage.error(res.message)
    }
  })
}

</script>

<style lang="less"></style>
