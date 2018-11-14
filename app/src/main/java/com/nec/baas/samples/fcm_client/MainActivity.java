package com.nec.baas.samples.fcm_client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.iid.FirebaseInstanceId;
import com.nec.baas.core.NbAndroidServiceBuilder;
import com.nec.baas.core.NbOperationMode;
import com.nec.baas.core.NbService;
import com.nec.baas.core.NbSetting;
import com.nec.baas.push.NbFcmPushInstallation;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private NbService fService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NbSetting.setOperationMode(NbOperationMode.DEBUG);

        // NbService の生成
        fService = new NbAndroidServiceBuilder(this)
                .tenantId(Consts.TENANT_ID)
                .appId(Consts.APP_ID)
                .appKey(Consts.APP_KEY)
                .endPointUri(Consts.ENDPOINT_URI)
                .build();

        // Installation に Token が設定されておらず、かつ Registration Token が
        // 存在している場合は、Installation に保存する。
        NbFcmPushInstallation installation = NbFcmPushInstallation.getCurrentInstallation();
        if (!installation.isRegistered()) {
            String token = FirebaseInstanceId.getInstance().getToken();
            if (token != null) {
                MyInstanceIDListenerService.registerInstallation(token);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
