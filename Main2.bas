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
	Dim cc As ContentChooser
	Dim cf As ContentChooser
End Sub

Sub Globals
	Dim ss As String
	Dim fileList As List
	Dim n As Int
Dim iv As ImageView
Dim b1,b2,b3 As Button
Dim zip As ABZipUnzip
Dim st As String
Dim lb As Label
	Dim copy As BClipboard
	Dim B As AdView
	Dim ml As MLfiles
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.Title = "Create Mi Font"
	B.Initialize2("B","ca-app-pub-4173348573252986/4626693352",B.SIZE_SMART_BANNER)
	Dim height As Int
	If GetDeviceLayoutValues.ApproximateScreenSize < 6 Then
		If 100%x > 100%y Then height = 32dip Else height = 50dip
	Else
		height = 90dip
	End If
	Activity.AddView(B, 0dip, 100%y - height, 100%x, height)
	B.LoadAd
	Log(B)
	
	Activity.AddMenuItem3("Change Font","change",LoadBitmap(File.DirAssets,"change.png"),True)
	Activity.AddMenuItem3("Share App","share",LoadBitmap(File.DirAssets,"share.png"),True)
	
	cc.Initialize("cc")
	cf.Initialize("cf")
iv.Initialize("iv")
	iv.Bitmap = LoadBitmap(File.DirRootExternal & "/MIUI Font Maker/Project/preview","preview_fonts_0.png")
	iv.Gravity = Gravity.FILL
	Activity.AddView(iv,10%x,0%y,80%x,50%y)
	
	b1.Initialize("b1")
	b1.Text = "Change Preview Photo"
	
	b2.Initialize("b2")
	b2.Text = "Pick Font"
	
	b3.Initialize("b3")
	b3.Text = "Create MIUI Font"
	
	lb.Initialize("lb")
	lb.Text = "[Please!! Choose Font File eg: MyFont.ttf ]"
	lb.Gravity = Gravity.CENTER
	Activity.AddView(b1,20%x,(iv.Top+iv.Height)+3%y,60%x,10%y)
	Activity.AddView(lb,0%x,(b1.Top+b1.Height),100%x,5%y)
	Activity.AddView(b2,20%x,(lb.Top+lb.Height),60%x,10%y)
	Activity.AddView(b3,20%x,(b2.Top+b2.Height),60%x,10%y)
End Sub

Sub iv_Click
	Dim ii As Intent
	ii.Initialize(ii.ACTION_VIEW,"file:///" & File.DirRootExternal & "/MIUI Font Maker/Project/preview/preview_fonts_0.png")
	ii.SetType("image/*")
	StartActivity(ii)
End Sub

Sub b1_Click
	cc.Show("image/*","Choose image")
End Sub

Sub b2_Click
	cf.Show("*/*","Choose Font")
End Sub

Sub b3_Click
	Dim sd As String = File.DirRootExternal & "/"
	st = File.ReadString(File.DirRootExternal & "/MIUI Font Maker","name.txt")
	Msgbox("MIUI font file will create a new file name with " &st&".mtz in" &CRLF& "sdcard/MIUI Font Maker/Output/","Attention!")
	
	zip.ABZipDirectory(sd & "MIUI Font Maker/Project",sd & "MIUI Font Maker/Output/"&st&".mtz")
	If File.Exists(File.DirRootExternal & "/MIUI Font Maker/Output",st&".mtz") = True Then
		Dim aa As Int
		aa = Msgbox2(st&".mtz file created successfully in"&CRLF&"sdcard/MIUI Font Maker/Output/","Completed!","Install","Done","Share",Null)
		If aa = DialogResponse.POSITIVE Then
			InstallMiFont
		End If
		If aa = DialogResponse.NEGATIVE Then
				Dim mshare As MESShareLibrary
			mshare.sharebinary("file://"&File.DirRootExternal & "/MIUI Font Maker/Output/"&st&".mtz","*/*","Share Your Created Font","MIUI Custom Font Maker")
		End If
			Else
		Msgbox("error","")
	End If
End Sub

Sub cc_Result (Success As Boolean, Dir As String, FileName As String)
	If Success Then	
		File.Delete(File.DirRootExternal & "/MIUI Font Maker/Project/preview","preview_fonts_0.png")
		File.Copy(Dir, FileName, File.DirRootExternal & "/MIUI Font Maker/Project/preview", "preview_fonts_0.png")
		iv.RemoveView
		iv.Bitmap = LoadBitmap(File.DirRootExternal & "/MIUI Font Maker/Project/preview","preview_fonts_0.png")
		Activity.AddView(iv,10%x,0%y,80%x,50%y)
	End If
End Sub

Sub Na
	Dim ss As String
	Dim fileList As List
	Dim n As Int
	fileList = File.ListFiles (File.DirRootExternal & "/.Naing/fonts" )
	fileList.Sort (True)
	n = 1
	ss = fileList.Get(n)
	Log(ss)
End Sub

Sub cf_Result (Success As Boolean, Dir As String, FileName As String)
	Log(Dir & FileName)
	If Success Then
		If FileName.EndsWith(".ttf") = True Then
			File.Delete(File.DirRootExternal & "/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf")
			File.Copy(Dir, FileName, File.DirRootExternal & "/MIUI Font Maker/Project/fonts", "Roboto-Regular.ttf")
			Else
			File.Delete(File.DirRootExternal & "/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf")
		File.Copy(Dir, FileName, File.DirRootExternal,"Htetz.zip")
			zip.ABUnzip(File.DirRootExternal & "/Htetz.zip",File.DirRootExternal & "/.Naing")
			Na
			File.Copy(File.DirRootExternal & "/.Naing/fonts", ss,File.DirRootExternal & "/MIUI Font Maker/Project/fonts", "Roboto-Regular.ttf")
		End If
		Else
		Msgbox("Your font is not TrueType Font(.ttf)"&CRLF&"Firstly!! Please convert your font to .ttf","Error")
	End If
	Log(ml.rm("/sdcard/MiFont.zip"))
	Log(ml.rm("/sdcard/Htetz.zip"))
	Log(ml.rmrf("/sdcard/.Htetz"))
	Log(ml.rmrf("/sdcard/.Naing"))
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub change_Click
	StartActivity(ChangFont)
End Sub

Sub InstallMiFont
	File.Copy(File.DirAssets,"Fuck",File.DirRootExternal,"d.zip")
	zip.ABUnzip(File.DirRootExternal & "/d.zip",File.DirRootExternal & "/.Htetz")
	File.Delete(File.DirRootExternal,"d.zip")
	File.Delete(File.DirRootExternal & "/.Htetz/.data/content/fonts","ZawgyiX1.mrc")
	File.Copy(File.DirRootExternal & "/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf",File.DirRootExternal & "/.Htetz/.data/content/fonts","ZawgyiX1.mrc")
	zip.ABZipDirectory(File.DirRootExternal & "/.Htetz",File.DirRootExternal & "/MiFont.zip")
			
	ml.mkdir ("/sdcard/MIUI/theme")
	Log(zip.ABUnzip(File.DirRootExternal & "/MiFont.zip", File.DirRootExternal & "/MIUI/theme"))
	Log(File.ListFiles(File.DirrootExternal & "/MIUI/theme"))
	ml.RootCmd("dd if="&File.DirrootExternal &"/MIUI/theme/.data of="&File.DirRootExternal&"/MIUI/theme","",Null,Null,False)
	ml.mkdir ("/sdcard/MIUI/theme")
	File.Delete(File.DirRootExternal,"MiFont.zip")
	If File.Exists(File.DirRootExternal & "/MIUI/theme/.data/content/fonts","ZawgyiX1.mrc") = True Then
		Dim ii As Intent
		ii.Initialize(ii.Action_Main,"")
		ii.SetComponent("com.android.settings/com.android.settings.Settings$FontSettingsActivity")
		Try
			StartActivity(ii)
		Catch
			Msgbox("Your Phone Is Not Xiaomi.","Error")
		End Try
	Else
		Msgbox("Error","ERROR")
	End If
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

'Banner Ads
Sub B_FailedToReceiveAd (ErrorCode As String)
	Log("B failed: " & ErrorCode)
End Sub
Sub B_ReceiveAd
	Log("B received")
End Sub

Sub B_AdScreenDismissed
	Log("B Dismissed")
End Sub