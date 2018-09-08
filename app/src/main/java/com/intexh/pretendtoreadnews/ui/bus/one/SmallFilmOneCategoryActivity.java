package com.intexh.pretendtoreadnews.ui.bus.one;

import android.content.res.Resources;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmCategory;
import com.intexh.pretendtoreadnews.ui.bus.base.SmallFilmCategoryActivity;

public class SmallFilmOneCategoryActivity extends SmallFilmCategoryActivity {


    @Override
    protected void loadLocationCategory() {
        mStartActivity = SmallFilmListOneActivity.class;
        // 加载本地数据
        Resources res = getResources();
        mHost = AppConstants.videoOneHost+"/html/";
        String[] film_category = res.getStringArray(R.array.small_one_category);
        String[] film_category_url = res.getStringArray(R.array.small_one_url);
        // 遍历分类内容
        for (int i = 0 ;i<film_category.length;i++)
        {
            SmallFilmCategory temp = new SmallFilmCategory();
            temp.setName(film_category[i]);
            temp.setUrl(mHost + film_category_url[i]);
            filmCategories.add(temp);
        }
        if (filmCategories == null||filmCategories.size()==0)
        {
            finish();
        }
    }
}
