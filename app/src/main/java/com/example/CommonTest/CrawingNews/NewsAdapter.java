package com.example.CommonTest.CrawingNews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.CommonTest.R;

import java.util.List;



public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewsBean.ResultBean.ListBean> list;
    private int layoutId;
    private ViewHolder viewHolder = null;

    public NewsAdapter(Context mContext, List<NewsBean.ResultBean.ListBean> list, int layoutId) {
        this.mContext = mContext;
        this.list = list;
        this.layoutId = layoutId;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(layoutId, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.txt_title);
            viewHolder.txtSummary = (TextView) view.findViewById(R.id.txt_summary);
            viewHolder.tv_media = (TextView) view.findViewById(R.id.txt_media);
            viewHolder.tv_data = (TextView) view.findViewById(R.id.txt_data);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (list.get(position).getImgurl() ==null||list.get(position).getImgurl().equals("")) {
            //如果没有图片，则将imageview控件隐藏
            viewHolder.imageView.setVisibility(View.GONE);
        } else {

            Glide
                    .with(mContext)
                    .load(list.get(position).getImgurl())
                    .into(viewHolder.imageView);

        }
        viewHolder.txtTitle.setText(list.get(position).getTitle());
        viewHolder.txtSummary.setText(list.get(position).getIntro());
        viewHolder.tv_media.setText(list.get(position).getMedia());
        viewHolder.tv_data.setText(list.get(position).getDatetime());

        return view;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView txtTitle, txtSummary,tv_media,tv_data;
    }

}
