/*
 * Copyright (C) 2024 The 2by2 Project
 * SPDX-License-Identifier: Apache-2.0
 */

package jp.project2by2.settings;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

@SearchIndexable
public class CustomSettings extends DashboardFragment {

    private static final String TAG = "2byTricks";

    public static final String CATEGORY_CUSTOM_SETTINGS = "com.android.settings.category.ia.custom_settings";

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.custom_settings;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.CUSTOM_SETTINGS;
    }

    @Override
    public String getCategoryKey() {
        return CATEGORY_CUSTOM_SETTINGS;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.custom_settings);
}
