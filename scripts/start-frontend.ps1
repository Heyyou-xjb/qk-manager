# 启动前端：把 frontend/ 同步到 nginx 站点目录并启动 nginx
# 用法：$env:QK_NGINX_DIR = 'D:\qk-manager-nginx\nginx-1.24.0-qk' 然后执行本脚本

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$frontend = Join-Path $root 'frontend'
$nginxDir = $env:QK_NGINX_DIR

if (-not $nginxDir) {
    Write-Host '[!] 请先设置环境变量 QK_NGINX_DIR 指向 nginx 安装目录（含 nginx.exe）'
    exit 1
}
if (-not (Test-Path (Join-Path $nginxDir 'nginx.exe'))) {
    Write-Host "[!] $nginxDir 下未找到 nginx.exe"
    exit 1
}

$htmlDir = Join-Path $nginxDir 'html'
New-Item -ItemType Directory -Force -Path $htmlDir | Out-Null
Write-Host "[i] 同步前端产物 -> $htmlDir"
Copy-Item (Join-Path $frontend 'index.html') $htmlDir -Force
Copy-Item (Join-Path $frontend 'favicon.ico') $htmlDir -Force -ErrorAction SilentlyContinue
Copy-Item (Join-Path $frontend 'assets') $htmlDir -Recurse -Force

Write-Host "[i] 启动 nginx ($nginxDir)"
Start-Process (Join-Path $nginxDir 'nginx.exe') -WorkingDirectory $nginxDir
Write-Host '[i] 访问 http://localhost:90 （端口以 conf/nginx.conf 的 listen 为准）'
