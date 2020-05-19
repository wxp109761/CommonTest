package com.example.CommonTest.slide;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CommonTest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * 筛选商品属性选择的popupwindow
 */
public class FilterPopupWindow extends PopupWindow {
    private View contentView;
    private Context context;
    private View goodsNoView;

    private GridView serviceGrid;
    private ListView selectionList;
    private TextView filterReset;
    private TextView filterSure;

    private String[] serviceStr = new String[]{"仅看有货", "促销", "手机专享"};

    /**
     * 商品属性选择的popupwindow
     */
    public FilterPopupWindow(final Activity context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.activity_slide_meun, null);
        goodsNoView = contentView.findViewById(R.id.popup_goods_noview);
        serviceGrid = (GridView) contentView.findViewById(R.id.yuguo_service);
        selectionList = (ListView) contentView.findViewById(R.id.selection_list);
        filterReset = (TextView) contentView.findViewById(R.id.filter_reset);
        filterSure = (TextView) contentView.findViewById(R.id.filter_sure);

        goodsNoView.setOnClickListener(new CancelOnClickListener());
        contentView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return true;
            }
        });


        String str = "["
                + "{\"nameId\":\"V2QASD\",\"saleVo\":["
                + "{\"value\":\"2核\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"},"
                + "{\"value\":\"4核\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"6核\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"8核\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"}"
                + "],\"name\":\"CPU\"},"
                + "{\"nameId\":\"V2QASD\",\"saleVo\":["
                + "{\"value\":\"全网通\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"移动4G\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"},"
                + "{\"value\":\"电信4G\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"联通4G\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"}"
                + "],\"name\":\"网络制式\"},"
                + "{\"nameId\":\"V2QASD\",\"saleVo\":["
                + "{\"value\":\"OPPO\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"荣耀\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"苹果\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"},"
                + "{\"value\":\"鸭梨\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"月饼\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"vivo\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"}"
                + "],\"name\":\"品牌\"},"
                + "{\"nameId\":\"V2QASD\",\"saleVo\":["
                + "{\"value\":\"音乐\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"},"
                + "{\"value\":\"拍照\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"待机长\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"}"
                + "],\"name\":\"主打\"},"
                + "{\"nameId\":\"V2QLAH\",\"saleVo\":["
                + "{\"value\":\"4.5英寸\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"5英寸\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"5.5英寸\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
                + "{\"value\":\"6英寸\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"}"
                + "],\"name\":\"尺寸\"}" + "]";
        JSONArray json = null;
        try {
            json = new JSONArray(str);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        this.setContentView(contentView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(00000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.update();

    }



    public class CancelOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

    public boolean onKeyDown(Context context, int keyCode, KeyEvent event) {
        this.context = context;
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }
        return true;
    }

    public void showFilterPopup(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

}
