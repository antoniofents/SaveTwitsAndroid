package com.example.afentanes.twitsaver;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.afentanes.twitsaver.layout.SaveTwitDialogFragment;

/**
 * Created by afentanes on 10/27/17.
 */

public class TwitSaverUtil {

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context.getPackageName(), TwitsReaderService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);

        //builder.setPeriodic(5*1000);
        builder.setMinimumLatency(5*1000);
        builder.setPersisted(true);
        builder.setOverrideDeadline(5*1000);

        builder.setOverrideDeadline(5*1000);

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        int resultCode = jobScheduler.schedule(builder.build());


        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("a", "Job scheduled!");
        } else {
            Log.d("a", "Job not scheduled");
        }
    }

    public static SaveTwitDialogFragment getDialogFragment(int message, int accept, int cancel){
        Bundle bundle = new Bundle();
        bundle.putInt("message", message);
        bundle.putInt("accept", accept);
        bundle.putInt("cancel", cancel);
        SaveTwitDialogFragment saveDialog = new SaveTwitDialogFragment();
        saveDialog.setArguments(bundle);
        return saveDialog;

    }

}
