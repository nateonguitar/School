import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		boolean supportES2 = (info.reqGlEsVersion >= 0x20000);
		
		if(supportES2){
			MainRenderer mainRenderer = new MainRenderer();
			
			MainSurfaceView mainSurfaceView = new MainSurfaceView(this);
			mainSurfaceView.setEGLContextClientVersion(2);
			mainSurfaceView.setRenderer(mainRenderer);
			this.setContentView(mainSurfaceView);
		}
		else{
			Log.e("OpenGLEs 2", "Your device doesn't support ES2. (" + info.reqGlEsVersion + ")");
		}
	}
}
