Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim ad1 As Timer
	Dim cc As ContentChooser
	Dim t As Timer
End Sub

Sub Globals
	Dim copy As BClipboard
	Dim zip As ABZipUnzip
	Dim b1,b2 As Button
	Dim ml As MLfiles
	
	Dim ss As String
	Dim fileList As List
	Dim nn As Int
	Dim lb As Label
	
	Dim I As InterstitialAd
	Dim N As NativeExpressAd
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.Title = "Change Font"

	'Interstitial Ads
	I.Initialize("Interstitial","ca-app-pub-4173348573252986/7580159759")
	I.LoadAd
	
	'Native Ads
	N.Initialize("N","ca-app-pub-4173348573252986/2200078557",100%x,200dip)
	N.LoadAd
	Activity.AddView(N,0%x,100%y- 200dip,100%x,200dip)
	
	ad1.Initialize("ad1",100)
	ad1.Enabled = False
	
	lb.Initialize("lb")
	lb.Text = "Supported All MIUI Version With 8++" & CRLF & "No Need #Root" &CRLF& "No Need Designer Account"
	lb.TextColor = Colors.Red
	Activity.AddView(lb,0%x,0%y,100%x,30%y)
	lb.Gravity = Gravity.CENTER

	lb.Initialize("")
	lb.Text = "[Choose Font/Theme File .ttf Or .mtz]"
	lb.Gravity = Gravity.CENTER
	Activity.AddMenuItem3("Share App","share",LoadBitmap(File.DirAssets,"share.png"),True)
	Activity.Title = "Install Custom Font"
	b1.Initialize("b1")
	b1.Text = "Pick Font File"
	Activity.AddView(b1,20%x,20%y,60%x,10%y)

	Activity.AddView(lb,0%x,b1.Top+b1.Height,100%x,5%y)
	b2.Initialize("b2")
	b2.Text = "Change Font"
	Activity.AddView(b2,20%x,lb.Top+lb.Height+1%y,60%x,10%y)
	
	cc.Initialize("cc")

	t.Initialize("t",3000)
	t.Enabled = False
End Sub

Sub b1_Click
	cc.Show("*/*","Choose Your Font")
End Sub

Sub Na
	fileList = File.ListFiles (File.DirRootExternal & "/.Naing/fonts" )
	fileList.Sort (True)
	nn = 1
	ss = fileList.Get(nn)
	Log(ss)
End Sub

Sub cc_Result (Success As Boolean, Dir As String, FileName As String)
	Log(Dir & FileName)
	If Success Then
		If FileName.EndsWith(".ttf") = True Then
			File.Copy(File.DirAssets,"Fuck",File.DirRootExternal,"d.zip")
			zip.ABUnzip(File.DirRootExternal & "/d.zip",File.DirRootExternal & "/.Htetz")
			File.Delete(File.DirRootExternal,"d.zip")
			File.Delete(File.DirRootExternal & "/.Htetz/.data/content/fonts","ZawgyiX1.mrc")
			File.Copy(Dir, FileName, File.DirRootExternal & "/.Htetz/.data/content/fonts","ZawgyiX1.mrc")
			zip.ABZipDirectory(File.DirRootExternal & "/.Htetz",File.DirRootExternal & "/MiFont.zip")
			
			ml.mkdir ("/sdcard/MIUI/theme")
			Log(zip.ABUnzip(File.DirRootExternal & "/MiFont.zip", File.DirRootExternal & "/MIUI/theme"))
			Log(File.ListFiles(File.DirrootExternal & "/MIUI/theme"))
			ml.RootCmd("dd if="&File.DirrootExternal &"/MIUI/theme/.data of="&File.DirRootExternal&"/MIUI/theme","",Null,Null,False)
			ml.mkdir ("/sdcard/MIUI/theme")
			ProgressDialogShow("Please Wait...")
			t.Enabled = True
		Else
			If FileName.EndsWith(".mtz") = True Then
				File.Copy(Dir, FileName, File.DirRootExternal,"Htetz.zip")
				zip.ABUnzip(File.DirRootExternal & "/Htetz.zip",File.DirRootExternal & "/.Naing")
				
				File.Copy(File.DirAssets,"Fuck",File.DirRootExternal,"d.zip")
				zip.ABUnzip(File.DirRootExternal & "/d.zip",File.DirRootExternal & "/.Htetz")
				File.Delete(File.DirRootExternal,"d.zip")
				File.Delete(File.DirRootExternal & "/.Htetz/.data/content/fonts","ZawgyiX1.mrc")
				
				File.Delete(File.DirRootExternal & "/.Htetz/.data/content/fonts","ZawgyiX1.mrc")
				ml.cp("/sdcard/.Naing/fonts/*.ttf","/sdcard/.Htetz/.data/content/fonts/ZawgyiX1.mrc")
				If File.Exists(File.DirRootExternal & "/.Htetz/.data/content/fonts","ZawgyiX1.mrc") = False Then
					Na
					File.Copy(File.DirRootExternal & "/.Naing/fonts", ss, File.DirRootExternal & "/.Htetz/.data/content/fonts","ZawgyiX1.mrc")
				End If
				zip.ABZipDirectory(File.DirRootExternal & "/.Htetz",File.DirRootExternal & "/MiFont.zip")
				ml.mkdir ("/sdcard/MIUI/theme")
				Log(zip.ABUnzip(File.DirRootExternal & "/MiFont.zip", File.DirRootExternal & "/MIUI/theme"))
				Log(File.ListFiles(File.DirrootExternal & "/MIUI/theme"))
				ml.RootCmd("dd if="&File.DirrootExternal &"/MIUI/theme/.data of="&File.DirRootExternal&"/MIUI/theme","",Null,Null,False)
				ml.mkdir ("/sdcard/MIUI/theme")
				ProgressDialogShow("Please Wait...")
				t.Enabled = True
			Else
				Msgbox("Please choose font/theme file"&CRLF& ".ttf Or .mtz","Incorrect  File")
				ad1.Enabled = True
			End If
		End If
	End If
End Sub

Sub t_Tick
	ad1.Enabled = True
	ml.rm("/sdcard/MiFont.zip")
	ml.rm("/sdcard/Htetz.zip")
	ml.rmrf("/sdcard/.Htetz")
	ml.rmrf("/sdcard/.Naing")
	ProgressDialogHide
	If File.Exists(File.DirRootExternal & "/MIUI/theme/.data/content/fonts","ZawgyiX1.mrc") = True Then
		Msgbox("Now! you can change font","Completed")
	Else
		Msgbox("Error","ERROR")
	End If
	t.Enabled = False
End Sub

Sub b2_Click
	Dim ii As Intent
	ii.Initialize(ii.Action_Main,"")
	ii.SetComponent("com.android.settings/com.android.settings.Settings$FontSettingsActivity")
	Try
		StartActivity(ii)
	Catch
		Msgbox("Missing Font."&CRLF&"(or)"&CRLF&"Your Phone Is Not Xiaomi.","Error")
	End Try
	ad1.Enabled = True
End Sub

Sub ad1_Tick
	If i.Ready Then i.Show Else i.LoadAd
	ad1.Enabled = False
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub share_Click
	Dim ShareIt As Intent
	copy.clrText
	copy.setText("You can change easily any font you like into MIUI font (.mtz)"&CRLF&"[Features/] "&CRLF&"Font Name!"&CRLF&"	Author Name!"&CRLF&"Designer Name!"&CRLF&"Version!"&CRLF&"MIUI Version!"&CRLF&"Description!"&CRLF&"Preview Image!"&CRLF&"You can change outsite font in MIUI 8 Without rooting And Designer account :)"&CRLF&"Note: You can convert TrueType Font(.ttf) To .MTZ only!!!"&CRLF&"( Other font extension Not working, eg: otf,woff,ofm,eot)"&CRLF&"Download Free at here : http://bit.ly/2nhLHbh")
	ShareIt.Initialize (ShareIt.ACTION_SEND,"")
	ShareIt.SetType ("text/plain")
	ShareIt.PutExtra ("android.intent.extra.TEXT",copy.getText)
	ShareIt.PutExtra ("android.intent.extra.SUBJECT","Get Free!!")
	ShareIt.WrapAsIntentChooser("Share App Via...")
	StartActivity (ShareIt)
End Sub

'Interstitial Ads
Sub I_AdClosed
	I.LoadAd
End Sub

Sub I_ReceiveAd
	Log("I Received")
End Sub

Sub I_FailedToReceiveAd (ErrorCode As String)
	Log("I not Received - " &"Error Code: "&ErrorCode)
	I.LoadAd
End Sub

Sub I_adopened
	Log("I Opened")
End Sub

'Native Ads

Sub N_FailedToReceiveAd (ErrorCode As String)
	Log("N failed: " & ErrorCode)
End Sub
Sub N_ReceiveAd
	Log("N received")
End Sub

Sub N_AdScreenDismissed
	Log("N Dismissed")
End Sub