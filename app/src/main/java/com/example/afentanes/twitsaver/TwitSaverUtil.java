package com.example.afentanes.twitsaver;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.example.afentanes.twitsaver.TwitsReaderService;

/**
 * Created by afentanes on 10/27/17.
 */

public class TwitSaverUtil {

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context.getPackageName(), TwitsReaderService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
       // builder.setPeriodic(5*1000);
        builder.setMinimumLatency(5*1000);
        builder.setOverrideDeadline(5*1000);

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        int resultCode = jobScheduler.schedule(builder.build());


        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("a", "Job scheduled!");
        } else {
            Log.d("a", "Job not scheduled");
        }



    }
}
