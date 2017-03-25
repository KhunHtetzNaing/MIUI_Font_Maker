package com.htetznaing.mifontmaker2;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main2 extends Activity implements B4AActivity{
	public static main2 mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.htetznaing.mifontmaker2", "com.htetznaing.mifontmaker2.main2");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main2).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.htetznaing.mifontmaker2", "com.htetznaing.mifontmaker2.main2");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.htetznaing.mifontmaker2.main2", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main2) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main2) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main2.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main2) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main2) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cf = null;
public static String _ss = "";
public anywheresoftware.b4a.objects.collections.List _filelist = null;
public static int _n = 0;
public anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b3 = null;
public com.AB.ABZipUnzip.ABZipUnzip _zip = null;
public static String _st = "";
public anywheresoftware.b4a.objects.LabelWrapper _lb = null;
public b4a.util.BClipboard _copy = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper _b = null;
public MLfiles.Fileslib.MLfiles _ml = null;
public com.htetznaing.mifontmaker2.main _main = null;
public com.htetznaing.mifontmaker2.changfont _changfont = null;
public com.htetznaing.mifontmaker2.main1 _main1 = null;
public com.htetznaing.mifontmaker2.about _about = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _height = 0;
 //BA.debugLineNum = 27;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 28;BA.debugLine="Activity.Title = \"Create Mi Font\"";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("Create Mi Font"));
 //BA.debugLineNum = 29;BA.debugLine="B.Initialize2(\"B\",\"ca-app-pub-4173348573252986/46";
mostCurrent._b.Initialize2(mostCurrent.activityBA,"B","ca-app-pub-4173348573252986/4626693352",mostCurrent._b.SIZE_SMART_BANNER);
 //BA.debugLineNum = 30;BA.debugLine="Dim height As Int";
_height = 0;
 //BA.debugLineNum = 31;BA.debugLine="If GetDeviceLayoutValues.ApproximateScreenSize <";
if (anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).getApproximateScreenSize()<6) { 
 //BA.debugLineNum = 32;BA.debugLine="If 100%x > 100%y Then height = 32dip Else height";
if (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)>anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)) { 
_height = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32));}
else {
_height = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50));};
 }else {
 //BA.debugLineNum = 34;BA.debugLine="height = 90dip";
_height = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90));
 };
 //BA.debugLineNum = 36;BA.debugLine="Activity.AddView(B, 0dip, 100%y - height, 100%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-_height),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),_height);
 //BA.debugLineNum = 37;BA.debugLine="B.LoadAd";
mostCurrent._b.LoadAd();
 //BA.debugLineNum = 38;BA.debugLine="Log(B)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._b));
 //BA.debugLineNum = 40;BA.debugLine="Activity.AddMenuItem3(\"Change Font\",\"change\",Load";
mostCurrent._activity.AddMenuItem3(BA.ObjectToCharSequence("Change Font"),"change",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"change.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 41;BA.debugLine="Activity.AddMenuItem3(\"Share App\",\"share\",LoadBit";
mostCurrent._activity.AddMenuItem3(BA.ObjectToCharSequence("Share App"),"share",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"share.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 43;BA.debugLine="cc.Initialize(\"cc\")";
_cc.Initialize("cc");
 //BA.debugLineNum = 44;BA.debugLine="cf.Initialize(\"cf\")";
_cf.Initialize("cf");
 //BA.debugLineNum = 45;BA.debugLine="iv.Initialize(\"iv\")";
mostCurrent._iv.Initialize(mostCurrent.activityBA,"iv");
 //BA.debugLineNum = 46;BA.debugLine="iv.Bitmap = LoadBitmap(File.DirRootExternal & \"/M";
mostCurrent._iv.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/preview","preview_fonts_0.png").getObject()));
 //BA.debugLineNum = 47;BA.debugLine="iv.Gravity = Gravity.FILL";
mostCurrent._iv.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 48;BA.debugLine="Activity.AddView(iv,10%x,0%y,80%x,50%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._iv.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 50;BA.debugLine="b1.Initialize(\"b1\")";
mostCurrent._b1.Initialize(mostCurrent.activityBA,"b1");
 //BA.debugLineNum = 51;BA.debugLine="b1.Text = \"Change Preview Photo\"";
mostCurrent._b1.setText(BA.ObjectToCharSequence("Change Preview Photo"));
 //BA.debugLineNum = 53;BA.debugLine="b2.Initialize(\"b2\")";
mostCurrent._b2.Initialize(mostCurrent.activityBA,"b2");
 //BA.debugLineNum = 54;BA.debugLine="b2.Text = \"Pick Font\"";
mostCurrent._b2.setText(BA.ObjectToCharSequence("Pick Font"));
 //BA.debugLineNum = 56;BA.debugLine="b3.Initialize(\"b3\")";
mostCurrent._b3.Initialize(mostCurrent.activityBA,"b3");
 //BA.debugLineNum = 57;BA.debugLine="b3.Text = \"Create MIUI Font\"";
mostCurrent._b3.setText(BA.ObjectToCharSequence("Create MIUI Font"));
 //BA.debugLineNum = 59;BA.debugLine="lb.Initialize(\"lb\")";
mostCurrent._lb.Initialize(mostCurrent.activityBA,"lb");
 //BA.debugLineNum = 60;BA.debugLine="lb.Text = \"[Please!! Choose Font File eg: MyFont.";
mostCurrent._lb.setText(BA.ObjectToCharSequence("[Please!! Choose Font File eg: MyFont.ttf ]"));
 //BA.debugLineNum = 61;BA.debugLine="lb.Gravity = Gravity.CENTER";
mostCurrent._lb.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 62;BA.debugLine="Activity.AddView(b1,20%x,(iv.Top+iv.Height)+3%y,6";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._iv.getTop()+mostCurrent._iv.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 63;BA.debugLine="Activity.AddView(lb,0%x,(b1.Top+b1.Height),100%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._lb.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._b1.getTop()+mostCurrent._b1.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 64;BA.debugLine="Activity.AddView(b2,20%x,(lb.Top+lb.Height),60%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._lb.getTop()+mostCurrent._lb.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 65;BA.debugLine="Activity.AddView(b3,20%x,(b2.Top+b2.Height),60%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._b2.getTop()+mostCurrent._b2.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 151;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 147;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _b_adscreendismissed() throws Exception{
 //BA.debugLineNum = 207;BA.debugLine="Sub B_AdScreenDismissed";
 //BA.debugLineNum = 208;BA.debugLine="Log(\"B Dismissed\")";
anywheresoftware.b4a.keywords.Common.Log("B Dismissed");
 //BA.debugLineNum = 209;BA.debugLine="End Sub";
return "";
}
public static String  _b_failedtoreceivead(String _errorcode) throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub B_FailedToReceiveAd (ErrorCode As String)";
 //BA.debugLineNum = 201;BA.debugLine="Log(\"B failed: \" & ErrorCode)";
anywheresoftware.b4a.keywords.Common.Log("B failed: "+_errorcode);
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _b_receivead() throws Exception{
 //BA.debugLineNum = 203;BA.debugLine="Sub B_ReceiveAd";
 //BA.debugLineNum = 204;BA.debugLine="Log(\"B received\")";
anywheresoftware.b4a.keywords.Common.Log("B received");
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public static String  _b1_click() throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub b1_Click";
 //BA.debugLineNum = 76;BA.debugLine="cc.Show(\"image/*\",\"Choose image\")";
_cc.Show(processBA,"image/*","Choose image");
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _b2_click() throws Exception{
 //BA.debugLineNum = 79;BA.debugLine="Sub b2_Click";
 //BA.debugLineNum = 80;BA.debugLine="cf.Show(\"*/*\",\"Choose Font\")";
_cf.Show(processBA,"*/*","Choose Font");
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _b3_click() throws Exception{
String _sd = "";
int _aa = 0;
com.madelephantstudios.MESShareLibrary.MESShareLibrary _mshare = null;
 //BA.debugLineNum = 83;BA.debugLine="Sub b3_Click";
 //BA.debugLineNum = 84;BA.debugLine="Dim sd As String = File.DirRootExternal & \"/\"";
_sd = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/";
 //BA.debugLineNum = 85;BA.debugLine="st = File.ReadString(File.DirRootExternal & \"/MIU";
mostCurrent._st = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","name.txt");
 //BA.debugLineNum = 86;BA.debugLine="Msgbox(\"MIUI font file will create a new file nam";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("MIUI font file will create a new file name with "+mostCurrent._st+".mtz in"+anywheresoftware.b4a.keywords.Common.CRLF+"sdcard/MIUI Font Maker/Output/"),BA.ObjectToCharSequence("Attention!"),mostCurrent.activityBA);
 //BA.debugLineNum = 88;BA.debugLine="zip.ABZipDirectory(sd & \"MIUI Font Maker/Project\"";
mostCurrent._zip.ABZipDirectory(_sd+"MIUI Font Maker/Project",_sd+"MIUI Font Maker/Output/"+mostCurrent._st+".mtz");
 //BA.debugLineNum = 89;BA.debugLine="If File.Exists(File.DirRootExternal & \"/MIUI Font";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Output",mostCurrent._st+".mtz")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 90;BA.debugLine="Dim aa As Int";
_aa = 0;
 //BA.debugLineNum = 91;BA.debugLine="aa = Msgbox2(st&\".mtz file created successfully";
_aa = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(mostCurrent._st+".mtz file created successfully in"+anywheresoftware.b4a.keywords.Common.CRLF+"sdcard/MIUI Font Maker/Output/"),BA.ObjectToCharSequence("Completed!"),"Install","Done","Share",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 92;BA.debugLine="If aa = DialogResponse.POSITIVE Then";
if (_aa==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 93;BA.debugLine="InstallMiFont";
_installmifont();
 };
 //BA.debugLineNum = 95;BA.debugLine="If aa = DialogResponse.NEGATIVE Then";
if (_aa==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
 //BA.debugLineNum = 96;BA.debugLine="Dim mshare As MESShareLibrary";
_mshare = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 97;BA.debugLine="mshare.sharebinary(\"file://\"&File.DirRootExtern";
_mshare.sharebinary(mostCurrent.activityBA,"file://"+anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Output/"+mostCurrent._st+".mtz","*/*","Share Your Created Font","MIUI Custom Font Maker");
 };
 }else {
 //BA.debugLineNum = 100;BA.debugLine="Msgbox(\"error\",\"\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("error"),BA.ObjectToCharSequence(""),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Sub cc_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 105;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 106;BA.debugLine="File.Delete(File.DirRootExternal & \"/MIUI Font M";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/preview","preview_fonts_0.png");
 //BA.debugLineNum = 107;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/preview","preview_fonts_0.png");
 //BA.debugLineNum = 108;BA.debugLine="iv.RemoveView";
mostCurrent._iv.RemoveView();
 //BA.debugLineNum = 109;BA.debugLine="iv.Bitmap = LoadBitmap(File.DirRootExternal & \"/";
mostCurrent._iv.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/preview","preview_fonts_0.png").getObject()));
 //BA.debugLineNum = 110;BA.debugLine="Activity.AddView(iv,10%x,0%y,80%x,50%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._iv.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _cf_result(boolean _success,String _dir,String _filename) throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Sub cf_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 126;BA.debugLine="Log(Dir & FileName)";
anywheresoftware.b4a.keywords.Common.Log(_dir+_filename);
 //BA.debugLineNum = 127;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 128;BA.debugLine="If FileName.EndsWith(\".ttf\") = True Then";
if (_filename.endsWith(".ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 129;BA.debugLine="File.Delete(File.DirRootExternal & \"/MIUI Font";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf");
 //BA.debugLineNum = 130;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf");
 }else {
 //BA.debugLineNum = 132;BA.debugLine="File.Delete(File.DirRootExternal & \"/MIUI Font";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf");
 //BA.debugLineNum = 133;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal,\"H";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"Htetz.zip");
 //BA.debugLineNum = 134;BA.debugLine="zip.ABUnzip(File.DirRootExternal & \"/Htetz.zip\"";
mostCurrent._zip.ABUnzip(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Htetz.zip",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.Naing");
 //BA.debugLineNum = 135;BA.debugLine="Na";
_na();
 //BA.debugLineNum = 136;BA.debugLine="File.Copy(File.DirRootExternal & \"/.Naing/fonts";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.Naing/fonts",mostCurrent._ss,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf");
 };
 }else {
 //BA.debugLineNum = 139;BA.debugLine="Msgbox(\"Your font is not TrueType Font(.ttf)\"&CR";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Your font is not TrueType Font(.ttf)"+anywheresoftware.b4a.keywords.Common.CRLF+"Firstly!! Please convert your font to .ttf"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 141;BA.debugLine="Log(ml.rm(\"/sdcard/MiFont.zip\"))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._ml.rm("/sdcard/MiFont.zip")));
 //BA.debugLineNum = 142;BA.debugLine="Log(ml.rm(\"/sdcard/Htetz.zip\"))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._ml.rm("/sdcard/Htetz.zip")));
 //BA.debugLineNum = 143;BA.debugLine="Log(ml.rmrf(\"/sdcard/.Htetz\"))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._ml.rmrf("/sdcard/.Htetz")));
 //BA.debugLineNum = 144;BA.debugLine="Log(ml.rmrf(\"/sdcard/.Naing\"))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._ml.rmrf("/sdcard/.Naing")));
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _change_click() throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Sub change_Click";
 //BA.debugLineNum = 156;BA.debugLine="StartActivity(ChangFont)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._changfont.getObject()));
 //BA.debugLineNum = 157;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Dim ss As String";
mostCurrent._ss = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim fileList As List";
mostCurrent._filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Dim n As Int";
_n = 0;
 //BA.debugLineNum = 17;BA.debugLine="Dim iv As ImageView";
mostCurrent._iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim b1,b2,b3 As Button";
mostCurrent._b1 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b2 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim zip As ABZipUnzip";
mostCurrent._zip = new com.AB.ABZipUnzip.ABZipUnzip();
 //BA.debugLineNum = 20;BA.debugLine="Dim st As String";
mostCurrent._st = "";
 //BA.debugLineNum = 21;BA.debugLine="Dim lb As Label";
mostCurrent._lb = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim copy As BClipboard";
mostCurrent._copy = new b4a.util.BClipboard();
 //BA.debugLineNum = 23;BA.debugLine="Dim B As AdView";
mostCurrent._b = new anywheresoftware.b4a.admobwrapper.AdViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim ml As MLfiles";
mostCurrent._ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _installmifont() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _ii = null;
 //BA.debugLineNum = 159;BA.debugLine="Sub InstallMiFont";
 //BA.debugLineNum = 160;BA.debugLine="File.Copy(File.DirAssets,\"Fuck\",File.DirRootExter";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Fuck",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"d.zip");
 //BA.debugLineNum = 161;BA.debugLine="zip.ABUnzip(File.DirRootExternal & \"/d.zip\",File.";
mostCurrent._zip.ABUnzip(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/d.zip",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.Htetz");
 //BA.debugLineNum = 162;BA.debugLine="File.Delete(File.DirRootExternal,\"d.zip\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"d.zip");
 //BA.debugLineNum = 163;BA.debugLine="File.Delete(File.DirRootExternal & \"/.Htetz/.data";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.Htetz/.data/content/fonts","ZawgyiX1.mrc");
 //BA.debugLineNum = 164;BA.debugLine="File.Copy(File.DirRootExternal & \"/MIUI Font Make";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/fonts","Roboto-Regular.ttf",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.Htetz/.data/content/fonts","ZawgyiX1.mrc");
 //BA.debugLineNum = 165;BA.debugLine="zip.ABZipDirectory(File.DirRootExternal & \"/.Htet";
mostCurrent._zip.ABZipDirectory(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.Htetz",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MiFont.zip");
 //BA.debugLineNum = 167;BA.debugLine="ml.mkdir (\"/sdcard/MIUI/theme\")";
mostCurrent._ml.mkdir("/sdcard/MIUI/theme");
 //BA.debugLineNum = 168;BA.debugLine="Log(zip.ABUnzip(File.DirRootExternal & \"/MiFont.z";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._zip.ABUnzip(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MiFont.zip",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI/theme")));
 //BA.debugLineNum = 169;BA.debugLine="Log(File.ListFiles(File.DirrootExternal & \"/MIUI/";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.File.ListFiles(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI/theme")));
 //BA.debugLineNum = 170;BA.debugLine="ml.RootCmd(\"dd if=\"&File.DirrootExternal &\"/MIUI/";
mostCurrent._ml.RootCmd("dd if="+anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI/theme/.data of="+anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI/theme","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 171;BA.debugLine="ml.mkdir (\"/sdcard/MIUI/theme\")";
mostCurrent._ml.mkdir("/sdcard/MIUI/theme");
 //BA.debugLineNum = 172;BA.debugLine="File.Delete(File.DirRootExternal,\"MiFont.zip\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"MiFont.zip");
 //BA.debugLineNum = 173;BA.debugLine="If File.Exists(File.DirRootExternal & \"/MIUI/them";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI/theme/.data/content/fonts","ZawgyiX1.mrc")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 174;BA.debugLine="Dim ii As Intent";
_ii = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 175;BA.debugLine="ii.Initialize(ii.Action_Main,\"\")";
_ii.Initialize(_ii.ACTION_MAIN,"");
 //BA.debugLineNum = 176;BA.debugLine="ii.SetComponent(\"com.android.settings/com.androi";
_ii.SetComponent("com.android.settings/com.android.settings.Settings$FontSettingsActivity");
 //BA.debugLineNum = 177;BA.debugLine="Try";
try { //BA.debugLineNum = 178;BA.debugLine="StartActivity(ii)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_ii.getObject()));
 } 
       catch (Exception e20) {
			processBA.setLastException(e20); //BA.debugLineNum = 180;BA.debugLine="Msgbox(\"Your Phone Is Not Xiaomi.\",\"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Your Phone Is Not Xiaomi."),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 };
 }else {
 //BA.debugLineNum = 183;BA.debugLine="Msgbox(\"Error\",\"ERROR\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error"),BA.ObjectToCharSequence("ERROR"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _iv_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _ii = null;
 //BA.debugLineNum = 68;BA.debugLine="Sub iv_Click";
 //BA.debugLineNum = 69;BA.debugLine="Dim ii As Intent";
_ii = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 70;BA.debugLine="ii.Initialize(ii.ACTION_VIEW,\"file:///\" & File.Di";
_ii.Initialize(_ii.ACTION_VIEW,"file:///"+anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project/preview/preview_fonts_0.png");
 //BA.debugLineNum = 71;BA.debugLine="ii.SetType(\"image/*\")";
_ii.SetType("image/*");
 //BA.debugLineNum = 72;BA.debugLine="StartActivity(ii)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_ii.getObject()));
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _na() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub Na";
 //BA.debugLineNum = 115;BA.debugLine="Dim ss As String";
mostCurrent._ss = "";
 //BA.debugLineNum = 116;BA.debugLine="Dim fileList As List";
mostCurrent._filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 117;BA.debugLine="Dim n As Int";
_n = 0;
 //BA.debugLineNum = 118;BA.debugLine="fileList = File.ListFiles (File.DirRootExternal &";
mostCurrent._filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.Naing/fonts");
 //BA.debugLineNum = 119;BA.debugLine="fileList.Sort (True)";
mostCurrent._filelist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 120;BA.debugLine="n = 1";
_n = (int) (1);
 //BA.debugLineNum = 121;BA.debugLine="ss = fileList.Get(n)";
mostCurrent._ss = BA.ObjectToString(mostCurrent._filelist.Get(_n));
 //BA.debugLineNum = 122;BA.debugLine="Log(ss)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._ss);
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim cc As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 10;BA.debugLine="Dim cf As ContentChooser";
_cf = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _share_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _shareit = null;
 //BA.debugLineNum = 187;BA.debugLine="Sub share_Click";
 //BA.debugLineNum = 188;BA.debugLine="Dim ShareIt As Intent";
_shareit = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 189;BA.debugLine="copy.clrText";
mostCurrent._copy.clrText(mostCurrent.activityBA);
 //BA.debugLineNum = 190;BA.debugLine="copy.setText(\"You can change easily any font you";
mostCurrent._copy.setText(mostCurrent.activityBA,"You can change easily any font you like into MIUI font (.mtz)"+anywheresoftware.b4a.keywords.Common.CRLF+"[Features/] "+anywheresoftware.b4a.keywords.Common.CRLF+"Font Name!"+anywheresoftware.b4a.keywords.Common.CRLF+"	Author Name!"+anywheresoftware.b4a.keywords.Common.CRLF+"Designer Name!"+anywheresoftware.b4a.keywords.Common.CRLF+"Version!"+anywheresoftware.b4a.keywords.Common.CRLF+"MIUI Version!"+anywheresoftware.b4a.keywords.Common.CRLF+"Description!"+anywheresoftware.b4a.keywords.Common.CRLF+"Preview Image!"+anywheresoftware.b4a.keywords.Common.CRLF+"You can change outsite font in MIUI 8 Without rooting And Designer account :)"+anywheresoftware.b4a.keywords.Common.CRLF+"Note: You can convert TrueType Font(.ttf) To .MTZ only!!!"+anywheresoftware.b4a.keywords.Common.CRLF+"( Other font extension Not working, eg: otf,woff,ofm,eot)"+anywheresoftware.b4a.keywords.Common.CRLF+"Download Free at here : http://bit.ly/2nhLHbh");
 //BA.debugLineNum = 191;BA.debugLine="ShareIt.Initialize (ShareIt.ACTION_SEND,\"\")";
_shareit.Initialize(_shareit.ACTION_SEND,"");
 //BA.debugLineNum = 192;BA.debugLine="ShareIt.SetType (\"text/plain\")";
_shareit.SetType("text/plain");
 //BA.debugLineNum = 193;BA.debugLine="ShareIt.PutExtra (\"android.intent.extra.TEXT\",cop";
_shareit.PutExtra("android.intent.extra.TEXT",(Object)(mostCurrent._copy.getText(mostCurrent.activityBA)));
 //BA.debugLineNum = 194;BA.debugLine="ShareIt.PutExtra (\"android.intent.extra.SUBJECT\",";
_shareit.PutExtra("android.intent.extra.SUBJECT",(Object)("Get Free!!"));
 //BA.debugLineNum = 195;BA.debugLine="ShareIt.WrapAsIntentChooser(\"Share App Via...\")";
_shareit.WrapAsIntentChooser("Share App Via...");
 //BA.debugLineNum = 196;BA.debugLine="StartActivity (ShareIt)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_shareit.getObject()));
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
}
