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
      row-key="id"
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
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="path" label="路径" width="200px" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="orders" label="顺序">
        <template #default="scope">
          <el-input-number
            style="width: 100px"
            v-model="scope.row.orders"
            :min="1"
            :max="100"
            @change="handleChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="图标" width="100px">
        <template #default="scope">
          <svg-icon :icon="scope.row.icon"></svg-icon>
        </template>
      </el-table-column>
      <el-table-column prop="isShow" label="是否可见">
        <template #default="scope">
          <el-switch
            @change="handleChange(scope.row)"
            v-model="scope.row.isShow"
            class="mt-2"
            inline-prompt
            :active-icon="Check"
            :inactive-icon="Close"
            :disabled="scope.row.name === 'permission'"
          />
        </template>
      </el-table-column>
      <el-table-column prop="descr" label="描述" show-overflow-tooltip />
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
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="form.path" placeholder="请输入路径" />
        </el-form-item>
        <el-form-item label="顺序" prop="orders">
          <el-input-number
            style="width: 100px"
            v-model="form.orders"
            :min="1"
            :max="100"
          />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="form.icon"></el-input>
        </el-form-item>
        <el-form-item label="是否可见" prop="isShow">
          <el-switch
            v-model="form.isShow"
            inline-prompt
            :active-icon="Check"
            :inactive-icon="Close"
            :disabled="form.name === 'permission'"
          />
        </el-form-item>
        <el-form-item label="选择菜单">
          <el-tree-select
            clearable
            v-model="form.pid"
            :data="tableData.list"
            check-strictly
            :render-after-expand="false"
            :props="{ label: 'title', value: 'id' }"
          />
        </el-form-item>
        <el-form-item label="描述" prop="descr">
          <el-input
            v-model="form.descr"
            placeholder="请输入描述"
            type="textarea"
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
  getPermissionList,
  addOrUpdatePermissionInfo,
  deletePermissionInfo,
  getPermissionTree,
} from "@/api/index"
import { nextTick, onMounted, reactive, ref } from "vue"
import {
  Check,
  Clock,
  Close,
  Delete,
  Edit,
  Plus,
} from "@element-plus/icons-vue"
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

const ruleFormRef = ref()
const dialogVisible = ref(false)
const form = reactive({
  id: "",
  name: "",
  path: "",
  pid: "",
  orders: "99",
  title: "",
  icon: "",
  isShow: true,
  descr: "",
})

onMounted(() => {
  getList()
})

const getList = async () => {
  await getPermissionList().then((res) => {
    tableData.list = res.data
  })
}

const open = (row) => {
  dialogVisible.value = true
  nextTick(() => {
    if (row) {
      Object.assign(form, row)
    }
  })
}
const close = () => {
  dialogVisible.value = false
  ruleFormRef.value.resetFields()
}

const handleChange = (row) => {
  Object.assign(form, row)
  form.createTime = null
  form.updateTime = null
  addOrUpdatePermissionInfo(form).then((res) => {
    if (res.code === "200") {
      ElMessage.success("操作成功")
      getList()
    } else {
      ElMessage.error(res.message)
    }
  })
}

const submit = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      form.createTime = null
      form.updateTime = null
      addOrUpdatePermissionInfo(form).then((res) => {
        if (res.code === "200") {
          ElMessage.success("操作成功")
          close()
          getList()
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
  await deletePermissionInfo(selectIds.value).then((res) => {
    if (res.code === "200") {
      ElMessage.success(res.message)
      getList()
    } else {
      ElMessage.error(res.message)
    }
  })
}

const rules = reactive({
  name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
  title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
  path: [{ required: true, message: "路径不能为空", trigger: "blur" }],
  orders: [{ required: true, message: "排序不能为空", trigger: "blur" }],
  icon: [{ required: true, message: "图标不能为空", trigger: "blur" }],
  isShow: [{ required: true, message: "选择不能为空", trigger: "blur" }],
})
</script>

<style lang="less"></style>
