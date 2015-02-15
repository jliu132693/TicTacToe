package droid.se1.zhong.tictactoedroid;

import android.support.v4.app.Fragment;

/**
 * Created by seales on 8/20/14.
 */
public class ScoresActivity extends SingleFragmentActivity  {
    @Override
    protected Fragment createFragment() {
        return new ScoresFragment();
    }
}
