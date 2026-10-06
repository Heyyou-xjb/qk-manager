# 前端说明（frontend/）

本目录是「轻客管家」前端的**运行产物**（Vue3 + Element Plus 构建结果）：

```
frontend/
├── index.html        # 单页应用入口
├── favicon.ico
├── assets/           # 构建后的 js / css / 图片（文件名带 hash）
└── nginx.conf        # 配套的 nginx 站点配置模板（静态托管 + /api 反向代理）
```

> 说明：当前工作区中只存在**构建产物**（原前端工程源码不在本机），因此仓库以产物形式保留前端，
> 保证「前后端内容完整、克隆下来即可运行」。如果后续拿到前端源码工程，可用源码替换本目录。

## 接口约定

- 前端所有请求都以 `/api` 开头（例如 `POST /api/login`、`GET /api/clues`）。
- `nginx.conf` 中通过 `location /api { rewrite ^/api/(.*)$ /$1 break; proxy_pass http://localhost:8080; }`
  把 `/api/*` 反向代理到后端 `8080`，因此前端与后端需部署在同一台机器上（默认 90 端口）。
- 请求头携带 `token`（登录接口返回的 JWT），后端 `LoginInterceptor` 校验。

## 本地启动方式

### 方式一：使用本机 nginx（推荐，与开发时一致）

```powershell
# 1. 把本目录内容放到 nginx 的站点根目录（nginx.conf 中 root html）
Copy-Item .\* "<你的nginx目录>\html" -Recurse -Force

# 2. 用本目录的 nginx.conf 覆盖 nginx 的 conf/nginx.conf（按需修改 listen 端口）
Copy-Item .\nginx.conf "<你的nginx目录>\conf\nginx.conf" -Force

# 3. 启动 nginx（工作目录必须是 nginx 根目录）
Start-Process "<你的nginx目录>\nginx.exe" -WorkingDirectory "<你的nginx目录>"
```

或直接使用仓库根目录脚本 `scripts/start-frontend.ps1`（先设置环境变量 `QK_NGINX_DIR`）。

### 方式二：任意静态服务器 + 反向代理

用 `python -m http.server`、`npx serve`、IIS、Apache 等托管本目录，并把 `/api` 反代到后端 8080；
单页应用需要开启「找不到文件时回退到 index.html」的规则。

## 访问

浏览器打开 `http://localhost:90`（或你配置的端口），使用数据库 `user` 表中的账号登录即可；
通过「用户管理」新增的用户默认密码为 `用户名+123`。
