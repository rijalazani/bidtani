package tukangdagang.id.co.tukangdagang_koperasi.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, AndroidService.class);
        context.startService(myIntent);

    }
}