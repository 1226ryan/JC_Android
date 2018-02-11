package com.example.cnwlc.project_memo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "중요한 메모가 있어요", Toast.LENGTH_LONG).show();

        /**
         PendingIntent는 일회용 인텐트 같은 개념입니다.
         FLAG_UPDATE_CURRENT - > 만일 이미 생성된 PendingIntent가 존재 한다면, 해당 Intent의 내용을 변경함.
         FLAG_CANCEL_CURRENT - .이전에 생성한 PendingIntent를 취소하고 새롭게 하나 만든다.
         FLAG_NO_CREATE -> 현재 생성된 PendingIntent를 반환합니다.
         FLAG_ONE_SHOT - >이 플래그를 사용해 생성된 PendingIntent는 단 한번밖에 사용할 수 없습니다
         */
        NotificationManager notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent2 = new Intent(context, AlarmMain.class);
        PendingIntent pender = PendingIntent.getActivity(context, 0, intent2, 0);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(pender)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("메모 좀")
                .setContentText("봅시다 거 참..")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setTicker("알람이 울렸어요");

        Notification notification = builder.build();
        notifier.notify(1, notification);
    }
}
