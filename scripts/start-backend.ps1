# 启动后端：先读取仓库根目录的 .env（若存在）注入环境变量，再运行打包好的 jar
# 用法：pwsh ./scripts/start-backend.ps1

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$envFile = Join-Path $root '.env'
$jar = Join-Path $root 'qk-start/target/qk-start-1.0-SNAPSHOT.jar'

if (Test-Path $envFile) {
    Write-Host "[i] 读取环境变量文件: $envFile"
    Get-Content $envFile | ForEach-Object {
        $line = $_.Trim()
        if ($line -and -not $line.StartsWith('#') -and $line.Contains('=')) {
            $kv = $line.Split('=', 2)
            [Environment]::SetEnvironmentVariable($kv[0].Trim(), $kv[1].Trim())
        }
    }
}
else {
    Write-Host '[i] 未找到 .env，将使用 application.yml 的默认值与系统环境变量'
    Write-Host '    可复制 .env.example 为 .env 并填写数据库/OSS 等配置'
}

if (-not (Test-Path $jar)) {
    Write-Host "[!] 未找到 $jar，请先执行: mvn -DskipTests package"
    exit 1
}

Write-Host "[i] 启动后端: $jar"
java -jar $jar
