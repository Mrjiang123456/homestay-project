/***
 * @常量路由
 */
export const Layout = () => import("@/views/layout/index.vue")
export const constRouter = [
  {
    path: "/login",
    meta: { title: "登录", icon: "", isShow: false },
    component: () => import("@/views/login/index.vue"),
  },
  {
    path: "/404",
    meta: { title: "404", isShow: false },
    component: () => import("@/views/404/index.vue"),
  },
  {
    path: "/:pathMatch(.*)*",
    // name: 'Any',
    meta: { title: "任意路由", isShow: false },
    redirect: "/404",
  },
  {
    path: "/",
    redirect: "/home",
    name: "layout",
    meta: { title: "", icon: "", isShow: false },
    component: Layout,
    children: [],
  },
]
