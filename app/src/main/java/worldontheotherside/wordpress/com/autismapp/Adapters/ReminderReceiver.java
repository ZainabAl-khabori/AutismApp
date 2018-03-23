package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import worldontheotherside.wordpress.com.autismapp.API.Child;
import worldontheotherside.wordpress.com.autismapp.API.Event;
import worldontheotherside.wordpress.com.autismapp.Activities.EditEventActivity;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/22/2018.
 */

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        Intent nextIntent = new Intent(context, EditEventActivity.class);
        nextIntent.putExtra(Constants.DAY_DATE, intent.getStringExtra(Constants.DAY_DATE));
        nextIntent.putExtra(Constants.NEW_EVENT, intent.getBooleanExtra(Constants.NEW_EVENT, false));
        nextIntent.putExtra(Constants.EVENT, intent.getStringExtra(Constants.EVENT));

        final Event event = new Gson().fromJson(intent.getStringExtra(Constants.EVENT), Event.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final PendingIntent pendingIntent = PendingIntent.getActivity(context, Constants.OPEN_EVENT_REQ, nextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "EventAlarm");
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        DBManip.getData(AppAPI.CHILDREN, user.getEmail(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Child child = new Child(dataSnapshot);

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        String message = Constants.EVENT_REMINDER + event.getTime();

                        builder.setAutoCancel(true)
                                .setSound(Uri.parse(event.getRingtone()))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setContentTitle(message)
                                .setContentText(event.getTitle())
                                .setLargeIcon(bitmap)
                                .setSmallIcon(R.drawable.ic_dp)
                                .setOnlyAlertOnce(true)
                                .setContentIntent(pendingIntent)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(event.getDescription()));

                        notificationManager.notify(event.getId(), builder.build());
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.get().load(child.getPhoto()).into(target);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
