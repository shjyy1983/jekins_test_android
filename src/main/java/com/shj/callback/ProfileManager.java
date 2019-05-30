package com.shj.callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ProfileManager {
    private static ProfileManager instance;

    private ProfileManager() {}

    public static ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    void getProfileInfo(final String userId, final String token, final ProfileManagerCallback callback) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap();
                map.put("name", "name123");
                map.put("userId", userId);
                map.put("token", token);
                callback.completeGetData(map);
            }
        }, 2000);
    }
}

interface ProfileManagerCallback {
    void completeGetData(Map<String, Object> map);
}

