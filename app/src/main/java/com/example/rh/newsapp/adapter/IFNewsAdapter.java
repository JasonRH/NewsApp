package com.example.rh.newsapp.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.model.NewsDetail;
import com.example.rh.newsapp.utils.ImageLoaderUtil;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/14
 */
public class IFNewsAdapter extends BaseMultiItemQuickAdapter<NewsDetail.ItemBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public IFNewsAdapter(List<NewsDetail.ItemBean> data) {
        super(data);
        addItemType(NewsDetail.ItemBean.TYPE_DOC_TITLEIMG, R.layout.item_detail_doc);
        addItemType(NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG, R.layout.item_detail_doc_slideimg);
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG, R.layout.item_detail_advert);
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG, R.layout.item_detail_advert_slideimg);
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG, R.layout.item_detail_advert_longimage);
        addItemType(NewsDetail.ItemBean.TYPE_SLIDE, R.layout.item_detail_slide);
        addItemType(NewsDetail.ItemBean.TYPE_PHVIDEO, R.layout.item_detail_phvideo);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, NewsDetail.ItemBean bean) {
        switch (viewHolder.getItemViewType()) {
            case NewsDetail.ItemBean.TYPE_DOC_TITLEIMG:
                viewHolder.setText(R.id.item_detail_doc_titleimg_title, bean.getTitle());
                viewHolder.setText(R.id.item_detail_doc_titleimg_source, bean.getSource());
                viewHolder.setText(R.id.item_detail_doc_titleimg_commnetsize, String.format(mContext.getResources().getString(R.string.news_commentsize), bean.getCommentsall()));
                ImageLoaderUtil.LoadImage(mContext, bean.getThumbnail(), (ImageView) viewHolder.getView(R.id.item_detail_doc_titleimg_logo));
                //Glide.with(MyApplication.getContext()).load(bean.getThumbnail()).into((ImageView) viewHolder.getView(R.id.iv_logo));
                viewHolder.addOnClickListener(R.id.item_detail_doc_titleimg_close);
                break;
            case NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG:
                viewHolder.setText(R.id.item_detail_doc_slideimg_title, bean.getTitle());
                viewHolder.setText(R.id.item_detail_doc_slideimg_source, bean.getSource());
                viewHolder.setText(R.id.item_detail_doc_slideimg_commnetsize, String.format(mContext.getResources().getString(R.string.news_commentsize), bean.getCommentsall()));
                try {
                    ImageLoaderUtil.LoadImage(mContext, bean.getStyle().getImages().get(0), (ImageView) viewHolder.getView(R.id.item_detail_doc_slideimg_1));
                    ImageLoaderUtil.LoadImage(mContext, bean.getStyle().getImages().get(1), (ImageView) viewHolder.getView(R.id.item_detail_doc_slideimg_2));
                    ImageLoaderUtil.LoadImage(mContext, bean.getStyle().getImages().get(2), (ImageView) viewHolder.getView(R.id.item_detail_doc_slideimg_3));
                    //Glide.with(MyApplication.getContext()).load(bean.getStyle().getImages().get(0)).into((ImageView) viewHolder.getView(R.id.iv_1));
                    //Glide.with(MyApplication.getContext()).load(bean.getStyle().getImages().get(1)).into((ImageView) viewHolder.getView(R.id.iv_2));
                    //Glide.with(MyApplication.getContext()).load(bean.getStyle().getImages().get(2)).into((ImageView) viewHolder.getView(R.id.iv_3));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewHolder.addOnClickListener(R.id.item_detail_doc_slideimg_close);
                break;
            case NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG:
                viewHolder.setText(R.id.item_detail_advert_titleimg_title, bean.getTitle());
                ImageLoaderUtil.LoadImage(mContext, bean.getThumbnail(), (ImageView) viewHolder.getView(R.id.item_detail_advert_titleimg_logo));
                //Glide.with(MyApplication.getContext()).load(bean.getThumbnail()).into((ImageView) viewHolder.getView(R.id.iv_logo));

                viewHolder.addOnClickListener(R.id.item_detail_advert_titleimg_close);
                break;
            case NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG:
                viewHolder.setText(R.id.item_detail_advert_slideimg_title, bean.getTitle());
                try {
                    ImageLoaderUtil.LoadImage(mContext, bean.getStyle().getImages().get(0), (ImageView) viewHolder.getView(R.id.item_detail_advert_slideimg_1));
                    ImageLoaderUtil.LoadImage(mContext, bean.getStyle().getImages().get(1), (ImageView) viewHolder.getView(R.id.item_detail_advert_slideimg_2));
                    ImageLoaderUtil.LoadImage(mContext, bean.getStyle().getImages().get(2), (ImageView) viewHolder.getView(R.id.item_detail_advert_slideimg_3));
                    //Glide.with(MyApplication.getContext()).load(bean.getStyle().getImages().get(0)).into((ImageView) viewHolder.getView(R.id.iv_1));
                    //Glide.with(MyApplication.getContext()).load(bean.getStyle().getImages().get(1)).into((ImageView) viewHolder.getView(R.id.iv_2));
                    //Glide.with(MyApplication.getContext()).load(bean.getStyle().getImages().get(2)).into((ImageView) viewHolder.getView(R.id.iv_3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewHolder.addOnClickListener(R.id.item_detail_advert_slideimg_close);
                break;
            case NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG:
                viewHolder.setText(R.id.item_detail_advert_longimg_title, bean.getTitle());
                ImageLoaderUtil.LoadImage(mContext, bean.getThumbnail(), (ImageView) viewHolder.getView(R.id.item_detail_advert_longimg_logo));
                //Glide.with(MyApplication.getContext()).load(bean.getThumbnail()).into((ImageView) viewHolder.getView(R.id.iv_logo));

                viewHolder.addOnClickListener(R.id.item_detail_advert_longimg_close);
                break;
            case NewsDetail.ItemBean.TYPE_SLIDE:
                viewHolder.setText(R.id.item_detail_slide_title, bean.getTitle());
                viewHolder.setText(R.id.item_detail_slide_source, bean.getSource());
                viewHolder.setText(R.id.item_detail_slide_commnetsize,
                        String.format(mContext.getResources().getString(R.string.news_commentsize), bean.getCommentsall()));
                ImageLoaderUtil.LoadImage(mContext, bean.getThumbnail(), (ImageView) viewHolder.getView(R.id.item_detail_slide_logo));
                //Glide.with(MyApplication.getContext()).load(bean.getThumbnail()).into((ImageView) viewHolder.getView(R.id.iv_logo));

                viewHolder.addOnClickListener(R.id.item_detail_slide_close);
                break;
            case NewsDetail.ItemBean.TYPE_PHVIDEO:
                viewHolder.setText(R.id.item_detail_phvideo_title, bean.getTitle());
                viewHolder.setText(R.id.item_detail_phvideo_source, bean.getSource());
                viewHolder.setText(R.id.item_detail_phvideo_commnetsize,
                        String.format(mContext.getResources().getString(R.string.news_commentsize), bean.getCommentsall()));
                viewHolder.addOnClickListener(R.id.item_detail_phvideo_close);
                ImageLoaderUtil.LoadImage(mContext, bean.getThumbnail(), (ImageView) viewHolder.getView(R.id.item_detail_phvideo_logo));
                //Glide.with(MyApplication.getContext()).load(bean.getThumbnail()).into((ImageView) viewHolder.getView(R.id.iv_logo));

                break;

            default:
                break;
        }
    }
}
