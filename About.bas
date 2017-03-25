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
	Dim t As Timer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim su As StringUtils
	Dim p As PhoneIntents
	Dim lstOne As ListView
	Dim abg As BitmapDrawable
	Dim b As AdView
	Dim i As InterstitialAd
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	Activity.Title = "About"
	abg.Initialize(LoadBitmap(File.DirAssets,"bg.jpg"))
	Activity.Background = abg
	
	Dim imvLogo As ImageView
	imvLogo.Initialize ("imv")
	imvLogo.Bitmap = LoadBitmap(File.DirAssets , "icon.png")
	imvLogo.Gravity = Gravity.FILL
	Activity.AddView ( imvLogo , 50%x - 50dip  , 20dip ,  100dip  ,  100dip )
	
	Dim lblName As  Label
	Dim bg As ColorDrawable
	bg.Initialize (Colors.DarkGray , 10dip)
	lblName.Initialize ("lbname")
	lblName.Background = bg
	lblName.Gravity = Gravity.CENTER
	lblName.Text = "MIUI Custom Font Maker"
	lblName.TextSize = 13
	lblName.TextColor = Colors.White
	Activity.AddView (lblName , 100%x / 2 - 90dip , 130dip , 180dip , 50dip)
	lblName.Height = su.MeasureMultilineTextHeight (lblName, lblName.Text ) + 5dip
	
	
	Dim c As ColorDrawable
	c.Initialize (Colors.White , 10dip )
	lstOne.Initialize ("lstOnes")
	lstOne.Background = c
	lstOne.SingleLineLayout .Label.TextSize = 12
	lstOne.SingleLineLayout .Label .TextColor = Colors.DarkGray
	lstOne.SingleLineLayout .Label .Gravity = Gravity.CENTER
	lstOne.SingleLineLayout .ItemHeight = 40dip
	lstOne.AddSingleLine2 ("App Name : MIUI Custom Font Maker",1)
	lstOne.AddSingleLine2 ("Current Version : 1.2",2)
	lstOne.AddSingleLine2 ("Developed By : Khun Htetz Naing",3)
	lstOne.AddSingleLine2 ("Powered By : Myanmar Android App",4)
	lstOne.AddSingleLine2 ("Website : www.MyanmarAndroidApp.com    ",5)
	lstOne.AddSingleLine2 ("Facebook : www.facebook.com/MmFreeAndroidApps ", 6)
	Activity.AddView ( lstOne, 30dip , 170dip , 100%x -  60dip, 240dip)
	
	Dim lblCredit As Label
	lblCredit.Initialize ("lblCredit")
	lblCredit.TextColor = Colors.Black
	lblCredit.TextSize = 13
	lblCredit.Gravity = Gravity.CENTER
	lblCredit.Text = "If you have any problem? Please contant me!"
	Activity.AddView (lblCredit, 10dip,(lstOne.Top+lstOne.Height)+2%y, 100%x - 20dip, 50dip)
	lblCredit.Height = su.MeasureMultilineTextHeight (lblCredit, lblCredit.Text )
		
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
	I.Show
	
	t.Initialize("t",100)
	t.Enabled = False
End Sub

Sub share_Click
	Dim copy As BClipboard
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

Sub t_Tick
	If	i.Ready Then i.Show Else i.LoadAd
	t.Enabled = False
End Sub

Sub imv_Click
	t.Enabled = True
End Sub

Sub lbname_Click
	t.Enabled = True
End Sub

Sub lblCredit_Click
	Try
		Dim Facebook As Intent
		Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
		StartActivity(Facebook)
	Catch
		Dim Facebook As Intent
		Facebook.Initialize(Facebook.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
		StartActivity(Facebook)
	End Try
End Sub

Sub Activity_Resume
     
End Sub

Sub Activity_Pause (UserClosed As Boolean)
     
End Sub

Sub lstOnes_ItemClick (Position As Int, Value As Object)
	t.Enabled = True
	Select Value
		Case 1
			StartActivity(p.OpenBrowser("http://www.MyanmarAndroidApp.com/search?q=MIUI+Custom+Font+Installer"))
		Case 3
			Try
				Dim Facebook As Intent
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://profile/100006126339714")
				StartActivity(Facebook)
			Catch
				Dim Facebook As Intent
				Facebook.Initialize(Facebook.ACTION_VIEW, "https://m.facebook.com/MgHtetzNaing")
				StartActivity(Facebook)
			End Try
		Case 4
			Try
				Dim Facebook As Intent
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
			Catch
				Dim Facebook As Intent
				Facebook.Initialize(Facebook.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
				StartActivity(Facebook)
			End Try
		Case 5
			StartActivity(p.OpenBrowser("http://www.MyanmarAndroidApp.com"))
		Case 6
			Try
				Dim Facebook As Intent
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
			Catch
				Dim Facebook As Intent
				Facebook.Initialize(Facebook.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
				StartActivity(Facebook)
			End Try
	End Select
	t.Enabled = True
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