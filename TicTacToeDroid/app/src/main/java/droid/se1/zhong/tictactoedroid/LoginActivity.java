package droid.se1.zhong.tictactoedroid;

import android.support.v4.app.Fragment;


public class LoginActivity extends SingleFragmentActivity  {
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
