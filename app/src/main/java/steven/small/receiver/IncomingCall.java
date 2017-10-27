package steven.small.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import steven.small.activity.PhoneActivity;

/**
 * Created by Admin on 10/26/2017.
 */

public class IncomingCall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("123", "onReceive: "+intent.getAction());
        intent = new Intent(context, PhoneActivity.class);
        context.startActivity(intent);
    }
}
