NECモバイルバックエンド基盤: FCM 受信サンプル
=============================================

NECモバイルバックエンド基盤を使用して FCM (Firebase Cloud Messaging) Push を受信する
サンプルプログラムです。

事前準備
========

Push通知ガイドの
[サーバーキーの取得手順](https://nec-baas.github.io/baas-manual/latest/developer/ja/push/fcm/apikey.html)
を参照し、FCM サーバーキーの取得を行ってください。
また、BaaSサーバ側にサーバーキーを登録してください。

次に [Pushを受信するための実装](https://nec-baas.github.io/baas-manual/latest/developer/ja/push/fcm/impl_fcm.html)
を参照し、Firebase プロジェクトを作成してください。

ついで、プロジェクト内に Android アプリを追加してください。
この際、アプリケーションのパッケージ名は "com.nec.baas.samples.fcm_client" (本アプリ)
としてください。これと異なるパッケージ名を使用しても構いませんが、
その場合は app/build.gradle 内の android.defaultConfig.applicationId の値を変更する必要があります。

アプリを追加したら、google-services.json をダウンロードし、"app" ディレクトリ内に配置してください。

app/src/main/java/com/nec/baas/samples/fcm_client ディレクトリにある
Consts.java.template ファイルを Consts.java にコピーし、
モバイルバックエンド基盤のテナントID/アプリID/アプリキー/エンドポイントURI を設定してください。

実行
====

Android Studio で本プロジェクトを開き、ビルドを行い実機上で実行してください。

モバイルバックエンド基盤デベロッパーコンソールから Push を送信してください。
本アプリで Push を受信すると、通知領域に通知が表示されます。
