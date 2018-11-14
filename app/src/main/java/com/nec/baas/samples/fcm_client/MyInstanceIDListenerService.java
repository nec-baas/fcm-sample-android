package com.nec.baas.samples.fcm_client;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.nec.baas.core.NbErrorInfo;
import com.nec.baas.push.NbFcmPushInstallation;
import com.nec.baas.push.NbFcmPushInstallationCallback;

import java.util.HashSet;
import java.util.Set;

public class MyInstanceIDListenerService extends FirebaseInstanceIdService {
    private static final String TAG = MyInstanceIDListenerService.class.getSimpleName();
    private static final String CHANNEL = "demo";

    @Override
    public void onTokenRefresh() {
        // Token を受け取る
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        registerInstallation(refreshedToken);
    }

    // BaaS サーバへインスタレーションを登録する
    public static synchronized void registerInstallation(String refreshedToken) {
        NbFcmPushInstallation installation = NbFcmPushInstallation.getCurrentInstallation();

        // Token をインスタレーションに保存
        installation.saveRegistrationToken(refreshedToken);

        // チャネル設定
        Set<String> channels = new HashSet<>();
        channels.add(CHANNEL);
        installation.setChannels(channels);

        // 送信可能ユーザ・グループ設定
        // ここでは誰からのPush通知も受け付ける
        Set<String> allowedSenders = new HashSet<>();
        allowedSenders.add("g:anonymous");
        installation.setAllowedSenders(allowedSenders);

        // インスタレーションを BaaS サーバへ登録する
        installation.save(new NbFcmPushInstallationCallback() {
            @Override
            public void onSuccess(NbFcmPushInstallation pushInstallation) {
                Log.i(TAG, "save succeeded.");
                //showInstallationInfo(pushInstallation);
            }

            @Override
            public void onFailure(int status, NbErrorInfo nbErrorInfo) {
                Log.e(TAG, nbErrorInfo.getReason());
            }
        });
    }
}
