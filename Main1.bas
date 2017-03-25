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
	Dim ad As Timer
End Sub

Sub Globals
	Dim edt,eda,edd,edv,edu,edde As EditText
	Dim b1 As Button
	Dim zip As ABZipUnzip

	Dim st,sa,sd,sv,su,sdd As String
	Dim rd,rp As String
	Dim d As String
	Dim copy As BClipboard
	
	Dim B As AdView
	Dim I As InterstitialAd
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.Title = "Mi Font Maker"
	Activity.AddMenuItem3("Share App","share",LoadBitmap(File.DirAssets,"share.png"),True)
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
	
	I.Initialize("Interstitial","ca-app-pub-4173348573252986/7580159759")
	I.LoadAd
	
	ad.Initialize("ad",100)
	ad.Enabled = False
	
	If File.Exists(File.DirRootExternal & "/MIUI Font Maker","d.txt") = False Then
		File.MakeDir(File.DirRootExternal,"MIUI Font Maker/Output")
		File.Copy(File.DirAssets,"HtetzNaing.mtz",File.DirRootExternal & "/MIUI Font Maker","HtetzNaing.zip")
		File.Copy(File.DirAssets,"d.txt",File.DirRootExternal & "/MIUI Font Maker","d.txt")
	End If

	zip.ABUnzip(File.DirRootExternal & "/MIUI Font Maker/HtetzNaing.zip",File.DirRootExternal & "/MIUI Font Maker/Project")
	File.Delete(File.DirRootExternal &  "/MIUI Font Maker","HtetzNaing.zip")
	
	edt.Initialize("edt")
	edt.Hint = "Title"

	eda.Initialize("eda")
	eda.Hint = "Author"

	edd.Initialize("edd")
	edd.Hint = "Designer"

	edv.Initialize("edv")
	edv.Hint = "Version"
	edv.InputType = edv.INPUT_TYPE_DECIMAL_NUMBERS

	edu.Initialize("edu")
	edu.Hint = "UI Version"
	edu.InputType = edu.INPUT_TYPE_DECIMAL_NUMBERS

	edde.Initialize("edde")
	edde.Hint = "Description"

	Activity.AddView(edt,10%x,1%y,80%x,10%y)
	Activity.AddView(eda,10%x,(edt.Height+edt.Top)+1%y,80%x,10%y)
	Activity.AddView(edd,10%x,(eda.Height+eda.Top)+1%y,80%x,10%y)
	Activity.AddView(edv,10%x,(edd.Height+edd.Top)+1%y,80%x,10%y)
	Activity.AddView(edu,10%x,(edv.Height+edv.Top)+1%y,80%x,10%y)
	Activity.AddView(edde,10%x,(edu.Height+edu.Top)+1%y,80%x,10%y)
	
	b1.Initialize("b1")
	b1.Text = " Next"
	Activity.AddView(b1,20%x,(edde.Top+edde.Height)+5%y,60%x,10%y)
End Sub

Sub b1_Click
	'Title
	If edt.Text = "" Then
		st = "MIUI Font"
	Else
		st = edt.Text
	End If
	
	'Author
	If eda.Text = "" Then
		sa = "Khun Htetz Naing"
	Else
		sa = eda.Text
	End If
	
	'Designer
	If edd.Text = "" Then
		sd = "Khun Htetz Naing"
	Else
		sd = edd.Text
	End If
	
	'Version
	If edv.Text = "" Then
		sv = "1.0"
	Else
		sv = edv.Text
	End If
	
	'UI Version
	If edu.Text = "" Then
		su = "8"
	Else
		su = edu.Text
	End If
	
	'Description
	If edde.Text = "" Then
		sdd = "This font was packed with MIUI Font Maker app. Download free at here : http://bit.ly/2lgBtaj"
	Else
		sdd = edde.Text
	End If
	
	rd = "<?xml version=@1.0@ encoding=@utf-8@ standalone=@no@?>"&CRLF&"<MIUI-Theme>"&CRLF&"<title>"&st&"</title>"&CRLF&"<designer>"&sd&"</designer>"&CRLF&"<author>"&sa&"</author>"& CRLF &"<version>"&sv&"</version>"& CRLF &"<uiVersion>"&su&"</uiVersion>"& CRLF &"<description>"&sdd&"</description>"& CRLF & "</MIUI-Theme>"
	d = File.ReadString(File.DirRootExternal & "/MIUI Font Maker","d.txt")
	rp = rd.Replace("@",d)
	File.Delete(File.DirRootExternal & "/MIUI Font Maker/Project","description.xml")
	File.WriteString(File.DirRootExternal & "/MIUI Font Maker/Project","description.xml",rp)
	If File.Exists(File.DirRootExternal & "/MIUI Font Maker","name.txt") = True Then
		File.Delete(File.DirRootExternal & "/MIUI Font Maker","name.txt")
	End If
	File.WriteString(File.DirRootExternal & "/MIUI Font Maker","name.txt",st)
	Log(rp)
	StartActivity(Main2)
	ad.Enabled = True
End Sub

Sub ad_Tick
	If I.Ready Then I.Show Else I.LoadAd
	ad.Enabled = False
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