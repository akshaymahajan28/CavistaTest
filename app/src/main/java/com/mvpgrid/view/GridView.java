package com.mvpgrid.view;

import org.json.JSONObject;

public interface GridView extends BaseView {

    void hideLoader();

    void setGridResponse(JSONObject object);
}
