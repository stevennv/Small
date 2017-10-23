package steven.small.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import steven.small.model.UserInfo;

/**
 * Created by TruongNV on 10/8/2017.
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils mIntent = null;
    private static final String SHARE_NAME = "SMALL";
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        mIntent = this;
        gson = new Gson();
    }

    public static SharedPreferencesUtils getIntent(Context context) {
        if (mIntent == null)
            mIntent = new SharedPreferencesUtils(context);
        return mIntent;
    }

    public void saveHightScoreGame1(int score) {
        editor.putInt(Common.HIGH_SCORE_GAME_1, score);
        editor.commit();
    }

    public int getHightScoreGame1() {
        int score = preferences.getInt(Common.HIGH_SCORE_GAME_1, 0);
        return score;
    }

    public void saveHightScoreGame2(int score) {
        editor.putInt(Common.HIGH_SCORE_GAME_2, score);
        editor.commit();
    }

    public int getHightScoreGame2() {
        int score = preferences.getInt(Common.HIGH_SCORE_GAME_2, 0);
        return score;
    }

    public void saveUserInfo(UserInfo userInfo) {
        String json = gson.toJson(userInfo);
        editor.putString(Common.USER_INFO, json);
        editor.commit();
    }

    public UserInfo getUserInfo() {
        String json = preferences.getString(Common.USER_INFO, "");
        UserInfo userInfo = gson.fromJson(json, UserInfo.class);
        return userInfo;
    }

    public void saveRoomRival(String room) {
        editor.putString(Common.ROOM_RIVAL, room);
        editor.commit();
    }

    public String getRoomRival() {
        String room = preferences.getString(Common.ROOM_RIVAL, "");
        return room;
    }
    public void saveKeyRival(String room) {
        editor.putString(Common.KEY_RIVAL, room);
        editor.commit();
    }

    public String getKeyRival() {
        String room = preferences.getString(Common.KEY_RIVAL, "");
        return room;
    }
}
