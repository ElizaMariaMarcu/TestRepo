package ro.pub.cs.systems.eim.practicaltest01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends Activity {

	private EditText text1, text2;
	private Button b1, b2, nav;
	private ClickList listener = new ClickList();
	private SomeEventBroadcastReceiver bcast = new SomeEventBroadcastReceiver();
	private IntentFilter intentFilter ;
	public class SomeEventBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.d("mamamam", intent.getStringExtra(Constants.DATA1) + ", " + intent.getStringExtra(Constants.DATA1)  );
			
		}
		}
	
    public class ClickList implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.show1:
				EditText t1 = (EditText)findViewById(R.id.editText1);
				int nr1 = Integer.parseInt(t1.getText().toString()) + 1;
				t1.setText(nr1+"");
				break;
			case R.id.show2:
				EditText t2 = (EditText)findViewById(R.id.editText2);
				int nr2 = Integer.parseInt(t2.getText().toString()) + 1;
				t2.setText(nr2+"");
				break;
			case R.id.button1:
				Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
				intent.putExtra(Constants.key1, ((EditText)findViewById(R.id.editText1)).getText().toString() );
				intent.putExtra(Constants.key2, ((EditText)findViewById(R.id.editText2)).getText().toString() );
				startActivityForResult(intent, 2016);
				break;
			}
			
			int n1 =  Integer.parseInt(((EditText)findViewById(R.id.editText1)).getText().toString());
			int n2 = Integer.parseInt(((EditText)findViewById(R.id.editText2)).getText().toString());
	
			if (n1 + n2 > Constants.prag) {
				Intent intent = new Intent(getApplicationContext(), StartedService.class);
				intent.putExtra(Constants.key1, n1);
				intent.putExtra(Constants.key2, n2);
				int sum = n1 + n2;
				Log.d("dg", "suma este " + sum );	
				startService(intent);
			}
		}
    	
    }
    
    @SuppressLint("ShowToast")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Toast.makeText(this, "Result retuned" + resultCode, Toast.LENGTH_LONG ).show();
    	
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_main);
		text1 = (EditText)findViewById(R.id.editText1);
		text2 = (EditText)findViewById(R.id.editText2);
		b1 = (Button)findViewById(R.id.show1);
		b2 = (Button)findViewById(R.id.show2);
		nav = (Button)findViewById(R.id.button1);
		text1.setText(0+"");
		text2.setText(0+"");
		b1.setOnClickListener(listener);
		b2.setOnClickListener(listener);
		nav.setOnClickListener(listener);
		intentFilter = new IntentFilter();
		for(int i = 0; i< Constants.actions.length; i++)
		intentFilter.addAction(Constants.actions[i]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_main, menu);
		return true;
	}
	
	@Override
   protected void onSaveInstanceState(Bundle saveInstanceState) {
	   saveInstanceState.putString(Constants.key1, text1.getText().toString());
	   saveInstanceState.putString(Constants.key2, text2.getText().toString());
   }
	
	   protected void onRestoreInstanceState(Bundle saveInstanceState) {
		   if (saveInstanceState != null && saveInstanceState.containsKey(Constants.key1)) {
			   text1.setText(saveInstanceState.get(Constants.key1).toString());
		   }
		   if (saveInstanceState != null && saveInstanceState.containsKey(Constants.key2)) {
			   text2.setText(saveInstanceState.get(Constants.key2).toString());
		   }
		   
	   }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		Intent intent = new Intent(getApplicationContext(), StartedService.class);
		stopService(intent);
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(bcast, intentFilter);
	}
	
 @Override
protected void onPause() {
	// TODO Auto-generated method stub
	 unregisterReceiver(bcast);
	super.onPause();
}
}
