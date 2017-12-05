package com.example.patry.kalkulator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LogicService extends Service {
    public LogicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
