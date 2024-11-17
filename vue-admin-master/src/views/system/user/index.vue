<template>
  <div>
    <div>
      <pane-head :route="route" />
    </div>
    <div>
      <div class="btns">
        <el-button :icon="Plus" type="primary" size="small" @click="open">
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
        color: dark ? '#fff' : '#000',
        fontFamily: 'myFont',
        fontSize: '14px',
      }"
    >
      <el-table-column type="selection" />
      <el-table-column prop="id" label="id" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="phone" label="联系号码" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column label="角色">
        <template #default="scope">
          {{ parseRoleName(scope.row.role) }}
        </template>
      </el-table-column>
      <el-table-column prop="avatar" label="头像">
        <template #default="scope">
          <el-image
            :src="scope.row.avatar"
            style="
              height: 30px;
              width: 30px;
              object-fit: cover;
              text-align: center;
            "
          ></el-image>
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
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="form.username"
            :disabled="form.id.length > 0"
            placeholder="请输入昵称"
          />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="联系号码" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入号码" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="菜单权限" prop="role">
          <el-select
            v-model="form.role"
            clearable
            placeholder="请选择菜单"
            style="width: 240px"
          >
            <el-option
              v-for="item in menuOptions"
              :key="item.flag"
              :label="item.name"
              :value="item.flag"
            />
          </el-select>
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
  addOrUpdateUserInfo,
  getRoleInfoList,
  getUserInfoPage,
  deleteUserInfo,
} from "@/api/index"
import { nextTick, onMounted, reactive, ref } from "vue"
import { Clock, Delete, Edit, InfoFilled, Plus } from "@element-plus/icons-vue"
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
const menuOptions = ref([])
const ruleFormRef = ref()
const dialogVisible = ref(false)
const form = reactive({
  id: "",
  phone: "",
  avatar: "",
  username: "",
  role: "",
  email: "",
})
// 分页
const pageParams = reactive({
  pageNum: 1,
  pageSize: 10,
})

onMounted(() => {
  getPage()
  getRoleInfoList().then((res) => {
    menuOptions.value = res.data
  })
})

const getPage = () => {
  getUserInfoPage(pageParams).then((res) => {
    const { records, total } = res.data
    tableData.list = records
    tableData.total = total
  })
}

const open = (row = {}) => {
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
  // 手动重置 form 对象
  // Object.assign(form, {
  //   id: "",
  //   phone: "",
  //   avatar: "",
  //   username: "",
  //   role: "",
  //   email: "",
  // })
}

const submit = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      form.createTime = null
      form.updateTime = null
      addOrUpdateUserInfo(form).then((res) => {
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

  await deleteUserInfo(selectIds.value).then((res) => {
    if (res.code === "200") {
      getPage()
    }
  })
}

const rules = reactive({
  username: [
    { required: true, message: "用户名不能为空", trigger: "blur" },
    { min: 4, message: "用户名的长度最少4位", trigger: "blur" },
  ],
  nickname: [{ required: true, message: "昵称不能为空", trigger: "blur" }],
  phone: [{ required: true, message: "联系方式不能为空", trigger: "blur" }],
  email: [{ required: true, message: "邮箱不能为空", trigger: "blur" }],
  role: [{ required: true, message: "角色不能为空", trigger: "blur" }],
})
const parseRoleName = (flag) => {
  const item = menuOptions.value.find((item) => item.flag === flag)
  return item ? item.name : ""
}
</script>

<style lang="less">

body {
  font-family: myFont;
}
</style>
