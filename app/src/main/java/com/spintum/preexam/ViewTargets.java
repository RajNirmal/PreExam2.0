package com.spintum.preexam;

import android.support.v7.widget.Toolbar;
import android.app.ActionBar;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import java.lang.reflect.Field;
import android.view.View;
import android.support.v7.widget.Toolbar;
/**
 * Created by Nirmal on 7/21/2016.
 */
public class ViewTargets {

    public static ViewTarget navigationButtonViewTarget(ActionBar toolbar) throws MissingViewException {
        try {
            Field field = Toolbar.class.getDeclaredField("mNavButtonView");
            field.setAccessible(true);
            View navigationView = (View) field.get(toolbar);
            return new ViewTarget(navigationView);
        } catch (NoSuchFieldException e) {
            throw new MissingViewException(e);
        } catch (IllegalAccessException e) {
            throw new MissingViewException(e);
        }
    }

    public static class MissingViewException extends Exception {

        public MissingViewException(Throwable throwable) {
            super(throwable);
        }
    }
}
