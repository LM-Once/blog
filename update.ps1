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
    $url = "http://172.17.239.65:8081/service/rest/v1/components?repository=olib-binaries"
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
        if ( $item.group="/olib" -and $item.name -like "olib/olib-py3-*.msi" ) {
            #Write-Host $item.name
			$itemList = $item.name -split "-"
            if ( $itemList[2] -gt $latestVersion ) {
                $latestName = $item.name
                $latestVersion = $itemList[2]
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

            if ( $item.group="/olib" -and $item.name -like "olib/olib-py3-*.msi" ) {
                #Write-Host $item.assets
				#Write-Host $item.assets[0].downloadUrl
				$itemList = $item.name -split "-"
                if ( $itemList[2] -gt $latestVersion ) {
                    $latestName = $item.name
                    $latestVersion = $itemList[2]
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

###########################################################################
#
# Entry point.
#
###########################################################################

# Upgrade olib to the latest

$executorName, $executorVersion, $executorUrl = getLatestExecutor
Write-Host "The latest olib name is $executorName, version is $executorVersion, url is $executorUrl"
$localVersion = $null
if ( Test-Path "C:/OLib/olib-py37/olib.exe" ) {
    $output = & C:\OLib\olib-py37\olib.exe -v
    $outputList = $output -split " "
    $localVersion = $outputList[1]
    Write-Host "Local olib version is $localVersion"
}
if ( -not $localVersion -or ( $localVersion -ne $executorVersion ) ) {
    Write-Host "Installing the latest $executorName from $executorUrl ..."
    (Start-Process "msiexec.exe" -ArgumentList "/i $executorUrl /qn" -NoNewWindow -Wait -PassThru).ExitCode
}