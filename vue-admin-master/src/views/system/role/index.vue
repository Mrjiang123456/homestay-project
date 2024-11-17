<template>
  <div>
    <div>
      <pane-head :route="route" />
    </div>
    <div>
      <div class="btns">
        <el-button :icon="Plus" type="primary" size="small" @click="open()">
          新增
        </el-button>
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
      <el-table-column prop="id" label="id" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="flag" label="标识" />
      <el-table-column label="权限数量">
        <template #default="scope">
          <el-progress
            :text-inside="true"
            :stroke-width="20"
            :percentage="getProgress(scope.row.permissionIds)"
          />
        </template>
      </el-table-column>
      <el-table-column label="创建时间">
        <template #default="scope">
          <div class="flex-box">
            <el-icon>
              <Clock />
            </el-icon>
            <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="修改时间">
        <template #default="scope">
          <div class="flex-box">
            <el-icon>
              <Clock />
            </el-icon>
            <span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="100px">
        <template #default="scope">
          <el-button
            :icon="Edit"
            type="primary"
            size="small"
            @click="open(scope.row)"
          >
            编辑
          </el-button>
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

    <el-dialog
      v-model="dialogVisible"
      title="编辑数据"
      width="500"
      :before-close="close"
    >
      <el-form
        ref="ruleFormRef"
        :model="form"
        :rules="rules"
        label-width="100px"
        label-position="left"
      >
        <el-form-item v-show="false" label="ID" prop="id">
          <el-input v-model="form.id" />
        </el-form-item>
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="标识" prop="flag">
          <el-input v-model="form.flag" placeholder="请输入标识" />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-tree
            ref="treeRef"
            style="max-width: 600px"
            :data="permissionOptions"
            :props="{ label: 'title', value: 'id' }"
            show-checkbox
            node-key="id"
            :default-checked-keys="[1]"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="close">取消</el-button>
          <el-button size="small" type="primary" @click="submit(ruleFormRef)">
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router"
import {
  getRoleInfoPage,
  deleteRoleInfo,
  addOrUpdateRoleInfo,
  getPermissionCount,
  getPermissionList,
} from "@/api/index"
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

const permissionOptions = ref([])
const ruleFormRef = ref()
const treeRef = ref()
const dialogVisible = ref(false)
const permissionCount = ref(0)
const form = reactive({
  id: "",
  name: "",
  description: "",
  flag: "",
  permissionIds: "",
})
// 分页
const pageParams = reactive({
  pageNum: 1,
  pageSize: 10,
})

onMounted(() => {
  getPage()
  getPermissionList().then((res) => {
    permissionOptions.value = res.data
  })
  getPermissionCount().then((res) => {
    permissionCount.value = res.data
  })
})

const getPage = async () => {
  await getRoleInfoPage(pageParams).then((res) => {
    const { records, total } = res.data
    tableData.list = records
    tableData.total = total
  })
}

const open = (row) => {
  dialogVisible.value = true
  nextTick(() => {
    if (row) {
      Object.assign(form, row)
      treeRef.value.setCheckedKeys(row.permissionIds.split(",").map(Number))
    }
  })
}
const close = () => {
  dialogVisible.value = false
  ruleFormRef.value.resetFields()
  treeRef.value.setCheckedKeys([1])
  // // 手动重置 form 对象
  // Object.assign(form, {
  //   id: "",
  //   name: "",
  //   flag: "",
  //   description: "",
  //   permissionIds: "",
  // })
}

const submit = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      form.permissionIds = treeRef.value.getCheckedKeys().join(",")
      form.createTime = null
      form.updateTime = null
      addOrUpdateRoleInfo(form).then((res) => {
        if (res.code === "200") {
          ElMessage.success("操作成功")
          close()
          getPage()
        } else {
          ElMessage.error(res.message)
        }
      })
    }
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
  await deleteRoleInfo(selectIds.value).then((res) => {
    if (res.code === "200") {
      ElMessage.success(res.message)
      getPage()
    } else {
      ElMessage.error(res.message)
    }
  })
}

const rules = reactive({
  name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
  flag: [{ required: true, message: "标识不能为空", trigger: "blur" }],
  description: [{ required: true, message: "描述不能为空", trigger: "blur" }],
})

const getProgress = (ids) => {
  let total = permissionCount.value
  let count = ids.split(",").length // 将字符串转换为数组并获取长度
  let progress = count / total
  if (progress > 1) {
    progress = 1
  }
  return Math.floor(progress * 100)
}
</script>

<style lang="less"></style>
