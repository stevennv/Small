package steven.small.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TruongNV on 10/8/2017.
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils mIntent = null;
    private static final String SHARE_NAME = "SMALL";
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        mIntent = this;
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
}
