package com.mail929.android.baggoscorekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by mail929 on 6/19/15.
 */
public class SettingsActivity extends PreferenceActivity
{
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        prefs = this.getSharedPreferences(getString(R.string.prefkey), Context.MODE_PRIVATE);
        editor = prefs.edit();

        PreferenceScreen ps = getPreferenceManager().createPreferenceScreen(this);

        PreferenceCategory gen = new PreferenceCategory(this);
        gen.setTitle("General");
        ps.addPreference(gen);

        final CheckBoxPreference scoring = new CheckBoxPreference(this);
        scoring.setTitle("Default Scoring Style");
        scoring.setChecked(prefs.getBoolean("scoringStyle", false));
        if(scoring.isChecked())
        {
            scoring.setSummary("Highest After is Default");
        }
        else
        {
            scoring.setSummary("First to is Default");
        }
        scoring.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                if((boolean) newValue)
                {
                    scoring.setSummary("Highest After is Default");
                }
                else
                {
                    scoring.setSummary("First to is Default");
                }

                editor.putBoolean("scoringStyle", (boolean) newValue);
                editor.commit();
                return true;
            }
        });
        gen.addPreference(scoring);

        PreferenceCategory about = new PreferenceCategory(this);
        about.setTitle("About");
        ps.addPreference(about);

        Preference version = new Preference(this);
        version.setTitle("App Version");
        try
        {
            version.setSummary(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        about.addPreference(version);

        Preference me = new Preference(this);
        me.setTitle("2015 Liam Fruzyna");
        me.setSummary("mail929.com");
        me.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            @Override
            public boolean onPreferenceClick(Preference preference)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mail929.com"));
                startActivity(browserIntent);
                return true;
            }
        });
        about.addPreference(me);

        setPreferenceScreen(ps);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        bar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        bar.setTranslationZ(8);
        root.addView(bar, 0);
        bar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs)
    {
        // Allow super to try and create a view first
        final View result = super.onCreateView(name, context, attrs);
        if (result != null)
        {
            return result;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            // If we're running pre-L, we need to 'inject' our tint aware Views in place of the
            // standard framework versions
            switch (name) {
                case "EditText":
                    return new AppCompatEditText(this, attrs);
                case "Spinner":
                    return new AppCompatSpinner(this, attrs);
                case "CheckBox":
                    return new AppCompatCheckBox(this, attrs);
                case "RadioButton":
                    return new AppCompatRadioButton(this, attrs);
                case "CheckedTextView":
                    return new AppCompatCheckedTextView(this, attrs);
            }
        }

        return null;
    }
}
