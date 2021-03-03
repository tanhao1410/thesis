Vue.component('main-header', {
    template: ` <div class="bd-maintain-header" >
    <div class="header-left" >
    <slot name="title">服务器设备监控管理系统</slot>
    </div>
    <div class="header-right">            
        <el-menu 
        class="el-menu-demo" 
        mode="horizontal" 
        @select="handleSelect"
        >
            <el-submenu index="1">
                <template slot="title">
                    {{username}}
                </template>
                <el-menu-item index="logout">退出登录</el-menu-item>
                <el-menu-item index="userCentre">个人中心</el-menu-item>
                <el-menu-item index="userManage">用户管理</el-menu-item>
            </el-submenu>
        </el-menu>
    </div>
</div>`,
    data() {
        return {
            username: ""
        }
    },
    mounted() {
        this.getUserInfo();
    },
    methods: {
        // 获取用户详情
        getUserInfo() {
            let params = {};
            httpGet(
                "/loginUser/userInfo",
                params,
                this,
                (err, res) => {
                    if (!err) {
                        if (0 === res.code) {
                            this.username = res.result.name|| res.result.userName;
                        } else if (88 === res.code) {
                            window.location.href = "/login.html"
                        } else {
                            this.$message.error(res.resultMessage);
                        }
                    } else {
                        this.$message.error("获取用户信息失败");
                    }
                }
            );
        },
        // 根据用户详情的role和当前url判断用户是否具有进入该页面的权限
        handleRole(roleId = 1) {
            if (1 === roleId) {
                // 仅有上传权限
                if (-1 === window.location.pathname.indexOf('upload')) {
                    // 跳转到上传列表
                    window.location.href = "/upload.html"
                }
            } else if (2 === roleId) {
                // 下载权限
                if (-1 === window.location.pathname.indexOf('download') && -1 === window.location.pathname.indexOf('summary')) {
                    // 跳转到下载列表
                    window.location.href = "/download.html"
                }
            }
        },
        // 退出
        handleSelect(key) {
            if ("logout" === key) {
                httpPost("/loginUser/logOut", {}, this, (err, res) => {
                    if (!err) {
                        if (0 === res.code) {
                            window.location.href = `/login.html`;
                        } else {
                            this.$message.error(res.resultMessage);
                        }
                    } else {
                        this.$message.error('退出失败');
                    }
                })
            }

        },
    }
});