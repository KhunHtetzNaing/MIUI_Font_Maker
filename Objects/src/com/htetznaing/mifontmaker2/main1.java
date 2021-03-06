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

public class main1 extends Activity implements B4AActivity{
	public static main1 mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.htetznaing.mifontmaker2", "com.htetznaing.mifontmaker2.main1");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main1).");
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
		activityBA = new BA(this, layout, processBA, "com.htetznaing.mifontmaker2", "com.htetznaing.mifontmaker2.main1");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.htetznaing.mifontmaker2.main1", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main1) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main1) Resume **");
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
		return main1.class;
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
        BA.LogInfo("** Activity (main1) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main1) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _ad = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edt = null;
public anywheresoftware.b4a.objects.EditTextWrapper _eda = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edd = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edv = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edu = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edde = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b1 = null;
public com.AB.ABZipUnzip.ABZipUnzip _zip = null;
public static String _st = "";
public static String _sa = "";
public static String _sd = "";
public static String _sv = "";
public static String _su = "";
public static String _sdd = "";
public static String _rd = "";
public static String _rp = "";
public static String _d = "";
public b4a.util.BClipboard _copy = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper _b = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper.InterstitialAdWrapper _i = null;
public com.htetznaing.mifontmaker2.main _main = null;
public com.htetznaing.mifontmaker2.main2 _main2 = null;
public com.htetznaing.mifontmaker2.changfont _changfont = null;
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
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 27;BA.debugLine="Activity.Title = \"Mi Font Maker\"";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("Mi Font Maker"));
 //BA.debugLineNum = 28;BA.debugLine="Activity.AddMenuItem3(\"Share App\",\"share\",LoadBit";
mostCurrent._activity.AddMenuItem3(BA.ObjectToCharSequence("Share App"),"share",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"share.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
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
 //BA.debugLineNum = 40;BA.debugLine="I.Initialize(\"Interstitial\",\"ca-app-pub-417334857";
mostCurrent._i.Initialize(mostCurrent.activityBA,"Interstitial","ca-app-pub-4173348573252986/7580159759");
 //BA.debugLineNum = 41;BA.debugLine="I.LoadAd";
mostCurrent._i.LoadAd();
 //BA.debugLineNum = 43;BA.debugLine="ad.Initialize(\"ad\",100)";
_ad.Initialize(processBA,"ad",(long) (100));
 //BA.debugLineNum = 44;BA.debugLine="ad.Enabled = False";
_ad.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 46;BA.debugLine="If File.Exists(File.DirRootExternal & \"/MIUI Font";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","d.txt")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 47;BA.debugLine="File.MakeDir(File.DirRootExternal,\"MIUI Font Mak";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"MIUI Font Maker/Output");
 //BA.debugLineNum = 48;BA.debugLine="File.Copy(File.DirAssets,\"HtetzNaing.mtz\",File.D";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"HtetzNaing.mtz",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","HtetzNaing.zip");
 //BA.debugLineNum = 49;BA.debugLine="File.Copy(File.DirAssets,\"d.txt\",File.DirRootExt";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"d.txt",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","d.txt");
 };
 //BA.debugLineNum = 52;BA.debugLine="zip.ABUnzip(File.DirRootExternal & \"/MIUI Font Ma";
mostCurrent._zip.ABUnzip(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/HtetzNaing.zip",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project");
 //BA.debugLineNum = 53;BA.debugLine="File.Delete(File.DirRootExternal &  \"/MIUI Font M";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","HtetzNaing.zip");
 //BA.debugLineNum = 55;BA.debugLine="edt.Initialize(\"edt\")";
mostCurrent._edt.Initialize(mostCurrent.activityBA,"edt");
 //BA.debugLineNum = 56;BA.debugLine="edt.Hint = \"Title\"";
mostCurrent._edt.setHint("Title");
 //BA.debugLineNum = 58;BA.debugLine="eda.Initialize(\"eda\")";
mostCurrent._eda.Initialize(mostCurrent.activityBA,"eda");
 //BA.debugLineNum = 59;BA.debugLine="eda.Hint = \"Author\"";
mostCurrent._eda.setHint("Author");
 //BA.debugLineNum = 61;BA.debugLine="edd.Initialize(\"edd\")";
mostCurrent._edd.Initialize(mostCurrent.activityBA,"edd");
 //BA.debugLineNum = 62;BA.debugLine="edd.Hint = \"Designer\"";
mostCurrent._edd.setHint("Designer");
 //BA.debugLineNum = 64;BA.debugLine="edv.Initialize(\"edv\")";
mostCurrent._edv.Initialize(mostCurrent.activityBA,"edv");
 //BA.debugLineNum = 65;BA.debugLine="edv.Hint = \"Version\"";
mostCurrent._edv.setHint("Version");
 //BA.debugLineNum = 66;BA.debugLine="edv.InputType = edv.INPUT_TYPE_DECIMAL_NUMBERS";
mostCurrent._edv.setInputType(mostCurrent._edv.INPUT_TYPE_DECIMAL_NUMBERS);
 //BA.debugLineNum = 68;BA.debugLine="edu.Initialize(\"edu\")";
mostCurrent._edu.Initialize(mostCurrent.activityBA,"edu");
 //BA.debugLineNum = 69;BA.debugLine="edu.Hint = \"UI Version\"";
mostCurrent._edu.setHint("UI Version");
 //BA.debugLineNum = 70;BA.debugLine="edu.InputType = edu.INPUT_TYPE_DECIMAL_NUMBERS";
mostCurrent._edu.setInputType(mostCurrent._edu.INPUT_TYPE_DECIMAL_NUMBERS);
 //BA.debugLineNum = 72;BA.debugLine="edde.Initialize(\"edde\")";
mostCurrent._edde.Initialize(mostCurrent.activityBA,"edde");
 //BA.debugLineNum = 73;BA.debugLine="edde.Hint = \"Description\"";
mostCurrent._edde.setHint("Description");
 //BA.debugLineNum = 75;BA.debugLine="Activity.AddView(edt,10%x,1%y,80%x,10%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._edt.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 76;BA.debugLine="Activity.AddView(eda,10%x,(edt.Height+edt.Top)+1%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._eda.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),(int) ((mostCurrent._edt.getHeight()+mostCurrent._edt.getTop())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 77;BA.debugLine="Activity.AddView(edd,10%x,(eda.Height+eda.Top)+1%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._edd.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),(int) ((mostCurrent._eda.getHeight()+mostCurrent._eda.getTop())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 78;BA.debugLine="Activity.AddView(edv,10%x,(edd.Height+edd.Top)+1%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._edv.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),(int) ((mostCurrent._edd.getHeight()+mostCurrent._edd.getTop())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 79;BA.debugLine="Activity.AddView(edu,10%x,(edv.Height+edv.Top)+1%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._edu.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),(int) ((mostCurrent._edv.getHeight()+mostCurrent._edv.getTop())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 80;BA.debugLine="Activity.AddView(edde,10%x,(edu.Height+edu.Top)+1";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._edde.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),(int) ((mostCurrent._edu.getHeight()+mostCurrent._edu.getTop())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 82;BA.debugLine="b1.Initialize(\"b1\")";
mostCurrent._b1.Initialize(mostCurrent.activityBA,"b1");
 //BA.debugLineNum = 83;BA.debugLine="b1.Text = \" Next\"";
mostCurrent._b1.setText(BA.ObjectToCharSequence(" Next"));
 //BA.debugLineNum = 84;BA.debugLine="Activity.AddView(b1,20%x,(edde.Top+edde.Height)+5";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._edde.getTop()+mostCurrent._edde.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _ad_tick() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub ad_Tick";
 //BA.debugLineNum = 145;BA.debugLine="If I.Ready Then I.Show Else I.LoadAd";
if (mostCurrent._i.getReady()) { 
mostCurrent._i.Show();}
else {
mostCurrent._i.LoadAd();};
 //BA.debugLineNum = 146;BA.debugLine="ad.Enabled = False";
_ad.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static String  _b_adscreendismissed() throws Exception{
 //BA.debugLineNum = 177;BA.debugLine="Sub B_AdScreenDismissed";
 //BA.debugLineNum = 178;BA.debugLine="Log(\"B Dismissed\")";
anywheresoftware.b4a.keywords.Common.Log("B Dismissed");
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _b_failedtoreceivead(String _errorcode) throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Sub B_FailedToReceiveAd (ErrorCode As String)";
 //BA.debugLineNum = 171;BA.debugLine="Log(\"B failed: \" & ErrorCode)";
anywheresoftware.b4a.keywords.Common.Log("B failed: "+_errorcode);
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _b_receivead() throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Sub B_ReceiveAd";
 //BA.debugLineNum = 174;BA.debugLine="Log(\"B received\")";
anywheresoftware.b4a.keywords.Common.Log("B received");
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _b1_click() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub b1_Click";
 //BA.debugLineNum = 89;BA.debugLine="If edt.Text = \"\" Then";
if ((mostCurrent._edt.getText()).equals("")) { 
 //BA.debugLineNum = 90;BA.debugLine="st = \"MIUI Font\"";
mostCurrent._st = "MIUI Font";
 }else {
 //BA.debugLineNum = 92;BA.debugLine="st = edt.Text";
mostCurrent._st = mostCurrent._edt.getText();
 };
 //BA.debugLineNum = 96;BA.debugLine="If eda.Text = \"\" Then";
if ((mostCurrent._eda.getText()).equals("")) { 
 //BA.debugLineNum = 97;BA.debugLine="sa = \"Khun Htetz Naing\"";
mostCurrent._sa = "Khun Htetz Naing";
 }else {
 //BA.debugLineNum = 99;BA.debugLine="sa = eda.Text";
mostCurrent._sa = mostCurrent._eda.getText();
 };
 //BA.debugLineNum = 103;BA.debugLine="If edd.Text = \"\" Then";
if ((mostCurrent._edd.getText()).equals("")) { 
 //BA.debugLineNum = 104;BA.debugLine="sd = \"Khun Htetz Naing\"";
mostCurrent._sd = "Khun Htetz Naing";
 }else {
 //BA.debugLineNum = 106;BA.debugLine="sd = edd.Text";
mostCurrent._sd = mostCurrent._edd.getText();
 };
 //BA.debugLineNum = 110;BA.debugLine="If edv.Text = \"\" Then";
if ((mostCurrent._edv.getText()).equals("")) { 
 //BA.debugLineNum = 111;BA.debugLine="sv = \"1.0\"";
mostCurrent._sv = "1.0";
 }else {
 //BA.debugLineNum = 113;BA.debugLine="sv = edv.Text";
mostCurrent._sv = mostCurrent._edv.getText();
 };
 //BA.debugLineNum = 117;BA.debugLine="If edu.Text = \"\" Then";
if ((mostCurrent._edu.getText()).equals("")) { 
 //BA.debugLineNum = 118;BA.debugLine="su = \"8\"";
mostCurrent._su = "8";
 }else {
 //BA.debugLineNum = 120;BA.debugLine="su = edu.Text";
mostCurrent._su = mostCurrent._edu.getText();
 };
 //BA.debugLineNum = 124;BA.debugLine="If edde.Text = \"\" Then";
if ((mostCurrent._edde.getText()).equals("")) { 
 //BA.debugLineNum = 125;BA.debugLine="sdd = \"This font was packed with MIUI Font Maker";
mostCurrent._sdd = "This font was packed with MIUI Font Maker app. Download free at here : http://bit.ly/2lgBtaj";
 }else {
 //BA.debugLineNum = 127;BA.debugLine="sdd = edde.Text";
mostCurrent._sdd = mostCurrent._edde.getText();
 };
 //BA.debugLineNum = 130;BA.debugLine="rd = \"<?xml version=@1.0@ encoding=@utf-8@ standa";
mostCurrent._rd = "<?xml version=@1.0@ encoding=@utf-8@ standalone=@no@?>"+anywheresoftware.b4a.keywords.Common.CRLF+"<MIUI-Theme>"+anywheresoftware.b4a.keywords.Common.CRLF+"<title>"+mostCurrent._st+"</title>"+anywheresoftware.b4a.keywords.Common.CRLF+"<designer>"+mostCurrent._sd+"</designer>"+anywheresoftware.b4a.keywords.Common.CRLF+"<author>"+mostCurrent._sa+"</author>"+anywheresoftware.b4a.keywords.Common.CRLF+"<version>"+mostCurrent._sv+"</version>"+anywheresoftware.b4a.keywords.Common.CRLF+"<uiVersion>"+mostCurrent._su+"</uiVersion>"+anywheresoftware.b4a.keywords.Common.CRLF+"<description>"+mostCurrent._sdd+"</description>"+anywheresoftware.b4a.keywords.Common.CRLF+"</MIUI-Theme>";
 //BA.debugLineNum = 131;BA.debugLine="d = File.ReadString(File.DirRootExternal & \"/MIUI";
mostCurrent._d = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","d.txt");
 //BA.debugLineNum = 132;BA.debugLine="rp = rd.Replace(\"@\",d)";
mostCurrent._rp = mostCurrent._rd.replace("@",mostCurrent._d);
 //BA.debugLineNum = 133;BA.debugLine="File.Delete(File.DirRootExternal & \"/MIUI Font Ma";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project","description.xml");
 //BA.debugLineNum = 134;BA.debugLine="File.WriteString(File.DirRootExternal & \"/MIUI Fo";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker/Project","description.xml",mostCurrent._rp);
 //BA.debugLineNum = 135;BA.debugLine="If File.Exists(File.DirRootExternal & \"/MIUI Font";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","name.txt")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 136;BA.debugLine="File.Delete(File.DirRootExternal & \"/MIUI Font M";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","name.txt");
 };
 //BA.debugLineNum = 138;BA.debugLine="File.WriteString(File.DirRootExternal & \"/MIUI Fo";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/MIUI Font Maker","name.txt",mostCurrent._st);
 //BA.debugLineNum = 139;BA.debugLine="Log(rp)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._rp);
 //BA.debugLineNum = 140;BA.debugLine="StartActivity(Main2)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._main2.getObject()));
 //BA.debugLineNum = 141;BA.debugLine="ad.Enabled = True";
_ad.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim edt,eda,edd,edv,edu,edde As EditText";
mostCurrent._edt = new anywheresoftware.b4a.objects.EditTextWrapper();
mostCurrent._eda = new anywheresoftware.b4a.objects.EditTextWrapper();
mostCurrent._edd = new anywheresoftware.b4a.objects.EditTextWrapper();
mostCurrent._edv = new anywheresoftware.b4a.objects.EditTextWrapper();
mostCurrent._edu = new anywheresoftware.b4a.objects.EditTextWrapper();
mostCurrent._edde = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim b1 As Button";
mostCurrent._b1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim zip As ABZipUnzip";
mostCurrent._zip = new com.AB.ABZipUnzip.ABZipUnzip();
 //BA.debugLineNum = 17;BA.debugLine="Dim st,sa,sd,sv,su,sdd As String";
mostCurrent._st = "";
mostCurrent._sa = "";
mostCurrent._sd = "";
mostCurrent._sv = "";
mostCurrent._su = "";
mostCurrent._sdd = "";
 //BA.debugLineNum = 18;BA.debugLine="Dim rd,rp As String";
mostCurrent._rd = "";
mostCurrent._rp = "";
 //BA.debugLineNum = 19;BA.debugLine="Dim d As String";
mostCurrent._d = "";
 //BA.debugLineNum = 20;BA.debugLine="Dim copy As BClipboard";
mostCurrent._copy = new b4a.util.BClipboard();
 //BA.debugLineNum = 22;BA.debugLine="Dim B As AdView";
mostCurrent._b = new anywheresoftware.b4a.admobwrapper.AdViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim I As InterstitialAd";
mostCurrent._i = new anywheresoftware.b4a.admobwrapper.AdViewWrapper.InterstitialAdWrapper();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _i_adclosed() throws Exception{
 //BA.debugLineNum = 183;BA.debugLine="Sub I_AdClosed";
 //BA.debugLineNum = 184;BA.debugLine="I.LoadAd";
mostCurrent._i.LoadAd();
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _i_adopened() throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub I_adopened";
 //BA.debugLineNum = 197;BA.debugLine="Log(\"I Opened\")";
anywheresoftware.b4a.keywords.Common.Log("I Opened");
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _i_failedtoreceivead(String _errorcode) throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub I_FailedToReceiveAd (ErrorCode As String)";
 //BA.debugLineNum = 192;BA.debugLine="Log(\"I not Received - \" &\"Error Code: \"&ErrorCode";
anywheresoftware.b4a.keywords.Common.Log("I not Received - "+"Error Code: "+_errorcode);
 //BA.debugLineNum = 193;BA.debugLine="I.LoadAd";
mostCurrent._i.LoadAd();
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _i_receivead() throws Exception{
 //BA.debugLineNum = 187;BA.debugLine="Sub I_ReceiveAd";
 //BA.debugLineNum = 188;BA.debugLine="Log(\"I Received\")";
anywheresoftware.b4a.keywords.Common.Log("I Received");
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim ad As Timer";
_ad = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _share_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _shareit = null;
 //BA.debugLineNum = 157;BA.debugLine="Sub share_Click";
 //BA.debugLineNum = 158;BA.debugLine="Dim ShareIt As Intent";
_shareit = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 159;BA.debugLine="copy.clrText";
mostCurrent._copy.clrText(mostCurrent.activityBA);
 //BA.debugLineNum = 160;BA.debugLine="copy.setText(\"You can change easily any font you";
mostCurrent._copy.setText(mostCurrent.activityBA,"You can change easily any font you like into MIUI font (.mtz)"+anywheresoftware.b4a.keywords.Common.CRLF+"[Features/] "+anywheresoftware.b4a.keywords.Common.CRLF+"Font Name!"+anywheresoftware.b4a.keywords.Common.CRLF+"	Author Name!"+anywheresoftware.b4a.keywords.Common.CRLF+"Designer Name!"+anywheresoftware.b4a.keywords.Common.CRLF+"Version!"+anywheresoftware.b4a.keywords.Common.CRLF+"MIUI Version!"+anywheresoftware.b4a.keywords.Common.CRLF+"Description!"+anywheresoftware.b4a.keywords.Common.CRLF+"Preview Image!"+anywheresoftware.b4a.keywords.Common.CRLF+"You can change outsite font in MIUI 8 Without rooting And Designer account :)"+anywheresoftware.b4a.keywords.Common.CRLF+"Note: You can convert TrueType Font(.ttf) To .MTZ only!!!"+anywheresoftware.b4a.keywords.Common.CRLF+"( Other font extension Not working, eg: otf,woff,ofm,eot)"+anywheresoftware.b4a.keywords.Common.CRLF+"Download Free at here : http://bit.ly/2nhLHbh");
 //BA.debugLineNum = 161;BA.debugLine="ShareIt.Initialize (ShareIt.ACTION_SEND,\"\")";
_shareit.Initialize(_shareit.ACTION_SEND,"");
 //BA.debugLineNum = 162;BA.debugLine="ShareIt.SetType (\"text/plain\")";
_shareit.SetType("text/plain");
 //BA.debugLineNum = 163;BA.debugLine="ShareIt.PutExtra (\"android.intent.extra.TEXT\",cop";
_shareit.PutExtra("android.intent.extra.TEXT",(Object)(mostCurrent._copy.getText(mostCurrent.activityBA)));
 //BA.debugLineNum = 164;BA.debugLine="ShareIt.PutExtra (\"android.intent.extra.SUBJECT\",";
_shareit.PutExtra("android.intent.extra.SUBJECT",(Object)("Get Free!!"));
 //BA.debugLineNum = 165;BA.debugLine="ShareIt.WrapAsIntentChooser(\"Share App Via...\")";
_shareit.WrapAsIntentChooser("Share App Via...");
 //BA.debugLineNum = 166;BA.debugLine="StartActivity (ShareIt)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_shareit.getObject()));
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
}
