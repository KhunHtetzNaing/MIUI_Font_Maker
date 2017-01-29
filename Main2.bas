Type=Activity
Version=6.5
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
	Dim ad As Timer
End Sub

Sub Globals
Dim iv As ImageView
Dim b1,b2,b3 As Button
Dim zip As ABZipUnzip
Dim st As String
Dim lb As Label
	Dim copy As BClipboard
	Dim Banner As AdView
	Dim Interstitial As InterstitialAd
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Banner.Initialize("Banner","ca-app-pub-4173348573252986/2989266950")
	Banner.LoadAd
	Activity.AddView(Banner,0%x,100%y - 50dip,100%x,50dip)
	
	Interstitial.Initialize("Interstitial","ca-app-pub-4173348573252986/8896199759")
	Interstitial.LoadAd
	
	ad.Initialize("ad",60000)
	ad.Enabled = True
	
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
	Dim i As Intent
	i.Initialize(i.ACTION_VIEW,"file:///" & File.DirRootExternal & "/MIUI Font Maker/Project/preview/preview_fonts_0.png")
	i.SetType("image/*")
	StartActivity(i)
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
		Msgbox(st&".mtz file created successfully in"&CRLF&"sdcard/MIUI Font Maker/Output/","Completed!")
	Else
		Msgbox("error","")
	End If
	File.Delete(File.DirRootExternal & "/MIUI Font Maker","name.txt")
	File.Delete(File.DirRootExternal & "/MIUI Font Maker/Project","description.xml")
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

Sub cf_Result (Success As Boolean, Dir As String, FileName As String)
	Log(Dir & FileName)
	If Success Then
		If FileName.EndsWith(".ttf") = False Then
			Msgbox("Your font is not TrueType Font(.ttf)"&CRLF&"Firstly!! Please convert your font to .ttf","Error")
			Else
			File.Delete(File.DirRootExternal & "/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf")
			File.Copy(Dir, FileName, File.DirRootExternal & "/MIUI Font Maker/Project/fonts", "Roboto-Regular.ttf")
		End If
	End If
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub change_Click
	StartActivity(ChangFont)
End Sub

Sub share_Click
	Dim ShareIt As Intent
	copy.clrText
	copy.setText("You can change easily any font you like into MIUI font (.mtz)"&CRLF&"[Features/] "&CRLF&"Font Name!"&CRLF&"	Author Name!"&CRLF&"Designer Name!"&CRLF&"Version!"&CRLF&"MIUI Version!"&CRLF&"Description!"&CRLF&"Preview Image!"&CRLF&"You can change outsite font in MIUI 8 Without rooting And Designer account :)"&CRLF&"Note: You can convert TrueType Font(.ttf) To .MTZ only!!!"&CRLF&"( Other font extension Not working, eg: otf,woff,ofm,eot)"&CRLF&"Download Free at here : https://goo.gl/ikReWC")
	ShareIt.Initialize (ShareIt.ACTION_SEND,"")
	ShareIt.SetType ("text/plain")
	ShareIt.PutExtra ("android.intent.extra.TEXT",copy.getText)
	ShareIt.PutExtra ("android.intent.extra.SUBJECT","Get Free!!")
	ShareIt.WrapAsIntentChooser("Share App Via...")
	StartActivity (ShareIt)
End Sub

Sub Interstitial_AdClosed
	Interstitial.LoadAd
End Sub

Sub ad_Tick
	If Interstitial.Ready Then Interstitial.Show
End Sub