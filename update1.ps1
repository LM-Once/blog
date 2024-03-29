# ------------------------------------------------------------
# Copyright (C), 2008-2018, OPPO Mobile Comm Corp., Ltd.
# Copyright (C), 2018-2019, OPPO Mobile Comm Corp., Ltd.
# ------------------------------------------------------------
# Author: Sunny Gu(nuanguang.gu@oppo.com)
# ------------------------------------------------------------

Set-StrictMode -Version 2

#Set-ExecutionPolicy -ExecutionPolicy Unrestricted -Scope LocalMachine -Force
Set-ExecutionPolicy Bypass -Scope Process -Force
#[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

function ConvertFrom-Json([object] $item){
    add-type -assembly system.web.extensions
    $ps_js=new-object system.web.script.serialization.javascriptSerializer
    #The comma operator is the array construction operator in PowerShell
    return ,$ps_js.DeserializeObject($item)
}

function getLatestExecutor() {
    $url = "c"
	$data = $null
	if ( $PSVersionTable.PSVersion -eq "2.0" ) {
		$WebRequest = [System.Net.WebRequest]::Create($url)
		$WebRequest.Method = "GET"
		$WebRequest.ContentType = "application/json"
		$ResponseStream = $WebRequest.GetResponse().GetResponseStream()
		$ReadStream = New-Object System.IO.StreamReader $ResponseStream
		$data=$ReadStream.ReadToEnd()
		$data=ConvertFrom-Json $data
	} else {
		$data = Invoke-RestMethod $url
	}
    $continuationToken=$data.continuationToken
    $latestName = $null
    $latestVersion = $null
    $latestUrl = $null

    #Write-Host $data.items
    ForEach ($item in $data.items) {
        #Write-Host $item.group
        if ( $item.group="/olib-executor" -and $item.name -like "olib-executor/olib-executor-py3-*.msi" ) {
            #Write-Host $item.name
			$itemList = $item.name -split "-"
            if ( $itemList[4] -gt $latestVersion ) {
                $latestName = $item.name
                $latestVersion = $itemList[4]
                $latestUrl = $item.assets[0].downloadUrl
            }
            #Write-Host $item.assets
            #Write-Host $item.assets.checksum.md5
        }
    }

    while ($continuationToken -ne $null ) {
        $url = "http://172.17.239.65:8081/service/rest/v1/components?repository=olib-binaries&continuationToken=$continuationToken"
		if ( $PSVersionTable.PSVersion -eq "2.0" ) {
			$WebRequest = [System.Net.WebRequest]::Create($url)
			$WebRequest.Method = "GET"
			$WebRequest.ContentType = "application/json"
			$ResponseStream = $WebRequest.GetResponse().GetResponseStream()
			$ReadStream = New-Object System.IO.StreamReader $ResponseStream
			$data=$ReadStream.ReadToEnd()
			$data=ConvertFrom-Json $data
		} else {
			$data = Invoke-RestMethod $url
		}

        #Write-Host $data.items
        ForEach ($item in $data.items) {
            #Write-Host $item.group

            if ( $item.group="/olib-executor" -and $item.name -like "olib-executor/olib-executor-py3-*.msi" ) {
                #Write-Host $item.assets
				#Write-Host $item.assets[0].downloadUrl
				$itemList = $item.name -split "-"
                if ( $itemList[4] -gt $latestVersion ) {
                    $latestName = $item.name
                    $latestVersion = $itemList[4]
                    $latestUrl = $item.assets[0].downloadUrl
                }
                #Write-Host $item.assets.checksum.md5
            }
        }
        $continuationToken=$data.continuationToken
    }
    #Write-Host $latestVersion
    return $latestName, $latestVersion, $latestUrl
}

function upgradePythonPackage($pythonPath, $packagesName) {
    Write-Host "$PythonPath -m pip list $packagesName | findstr $packagesName"
    $output = & $PythonPath -m pip list $packagesName | findstr $packagesName
    Write-Host $output
    $localVersion = $null
    ForEach( $line in $output.split("\n") ) {
        Write-Host "Line: $line"
        $lineList=$line.split(" ")
        $_packagesName=$lineList[0].replace(" ", "")
        $_packagesVersion=$lineList[-1].replace(" ", "")
        Write-Host "Name: $_packagesName, Version: $_packagesVersion"
        if ( $_packagesName -eq $packagesName ) {
            $localVersion=$_packagesVersion
            break
        }
    }

    Write-Host "###Local package $packagesName version is $localVersion ###"
    #$localVersion = & $PythonPath -m pip install -U pip --index-url http://172.17.239.65:8081/repository/pypi-olib-public/simple --trusted-host 172.17.239.65
}

###########################################################################
#
# Entry point.
#
###########################################################################

# Upgrade olib-executor to the latest

$executorName, $executorVersion, $executorUrl = getLatestExecutor
Write-Host "The latest executor name is $executorName, version is $executorVersion, url is $executorUrl"
$localVersion = $null
if ( Test-Path "C:/OLib/olib-executor/olib-executor.exe" ) {
    $output = & C:\OLib\olib-executor\olib-executor.exe -v
    $outputList = $output -split " "
    $localVersion = $outputList[1]
    Write-Host "Local olib-executor version is $localVersion"
}
if ( -not $localVersion -or ( $localVersion -ne $executorVersion ) ) {
    Write-Host "Installing the latest $executorName from $executorUrl ..."
    (Start-Process "msiexec.exe" -ArgumentList "/i $executorUrl /qn" -NoNewWindow -Wait -PassThru).ExitCode
}

# Upgrade python environment to the latest
$PythonPath="C:/OLib/olib-executor/python/python.exe"
if ( Test-Path $PythonPath ) {
    $projects=New-Object System.Collections.Generic.List[string]
    $projects.Add("pip")
    $projects.Add("olib")
    $projects.Add("olib_aw")
    $projects.Add("olib_scripts")
    $projects.Add("olib_scripts_modem")
    $projects.Add("olib_scripts_wlan")
    $projects.Add("olib_scripts_bluetooth")
    $projects.Add("olib_scripts_special")
    $projects.Add("olib_scripts_smoke")
    $global:projectName=$null
    $projects.ForEach({
        $temp_name=$args
        Write-Host "Upgrading python package $temp_name..."
        & $PythonPath -m pip install -U $temp_name --index-url http://172.17.239.65:8081/repository/pypi-olib-public/simple --trusted-host 172.17.239.65
    })
    #upgradePythonPackage $PythonPath olib
}