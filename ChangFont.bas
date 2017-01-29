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
Dim ad As Timer
End Sub

Sub Globals
Dim b1,b2 As Button
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
	
	Activity.AddMenuItem3("Share App","share",LoadBitmap(File.DirAssets,"share.png"),True)
b1.Initialize("b1")
b1.Text = "Import Font"

b2.Initialize("b2")
b2.Text = "Change Font"

Activity.AddView(b1,20%x,20%y,60%x,10%y)
Activity.AddView(b2,20%x,(b1.Top+b1.Height)+1%y,60%x,10%y)
End Sub

Sub b1_Click
	Dim i As Intent
	i.Initialize(i.ACTION_VIEW,"com.android.thememanager")
	StartActivity(i)
End Sub

Sub b2_Click
	Try
		Dim i As Intent
		i.Initialize("", "")
		i.SetComponent("com.android.settings/.Settings$FontSettingsActivity")
		StartActivity(i)
	Catch
		Dim i As Intent
		i.Initialize(i.ACTION_VIEW,"com.android.thememanager")
		StartActivity(i)
	End Try
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

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