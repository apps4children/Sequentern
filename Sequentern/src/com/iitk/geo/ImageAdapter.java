package com.iitk.geo;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
public class ImageAdapter extends PagerAdapter 
{
	Context context;
    private int[] GalImages = new int[] { R.drawable.shape1,R.drawable.shape2,R.drawable.shape3,R.drawable.shape4,R.drawable.shape5,
    		                              R.drawable.shape6,R.drawable.shape7,R.drawable.shape8,R.drawable.shape9,R.drawable.shape10
    		                              ,R.drawable.shape11,R.drawable.shape12};                          
    		                              
    ImageAdapter(Context context){
    	this.context=context;
    }
    public int getCount() {
      return GalImages.length;
    }
    public boolean isViewFromObject(View view, Object object) 
    {
      return view == ((ImageView) object);
    }
    public Object instantiateItem(ViewGroup container, int position) {
      ImageView imageView = new ImageView(context);
      int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
      imageView.setPadding(padding, padding, padding, padding);
      imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      imageView.setImageResource(GalImages[position]);
      ((ViewPager) container).addView(imageView, 0);
      return imageView;
      
      
    }
    public void destroyItem(ViewGroup container, int position, Object object) {
      ((ViewPager) container).removeView((ImageView) object);
    }
  }
