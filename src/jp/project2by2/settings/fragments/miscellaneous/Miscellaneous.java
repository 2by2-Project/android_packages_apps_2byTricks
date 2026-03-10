/*
 * Copyright (C) 2019-2024 The Evolution X Project
 * SPDX-License-Identifier: Apache-2.0
 */

package jp.project2by2.settings.fragments.miscellaneous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;

import jp.project2by2.settings.preferences.KeyboxDataPreference;

import java.util.List;

@SearchIndexable
public class Miscellaneous extends SettingsPreferenceFragment {

    private static final String TAG = "Miscellaneous";
    private static final String KEY_KEYBOX_DATA_SETTING = "keybox_data_setting";

    private KeyboxDataPreference mKeyboxDataPreference;
    private ActivityResultLauncher<Intent> mKeyboxFilePickerLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.custom_settings_miscellaneous);

        mKeyboxDataPreference = findPreference(KEY_KEYBOX_DATA_SETTING);

        mKeyboxFilePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() != Activity.RESULT_OK || result.getData() == null) {
                        return;
                    }
                    final Uri uri = result.getData().getData();
                    if (uri == null || mKeyboxDataPreference == null) {
                        return;
                    }
                    try {
                        final int takeFlags = result.getData().getFlags()
                                & Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        requireContext().getContentResolver()
                                .takePersistableUriPermission(uri, takeFlags);
                    } catch (SecurityException e) {
                        Log.w(TAG, "Failed to persist uri permission for keybox file", e);
                    }
                    mKeyboxDataPreference.handleFileSelected(uri);
                });

        if (mKeyboxDataPreference != null) {
            mKeyboxDataPreference.setFilePickerLauncher(mKeyboxFilePickerLauncher);
        }
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.CUSTOM_SETTINGS;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider(R.xml.custom_settings_miscellaneous) {

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                return keys;
            }
        };
}
