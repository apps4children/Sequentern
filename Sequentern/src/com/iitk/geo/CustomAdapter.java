package com.iitk.geo;
import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class CustomAdapter extends BaseAdapter 
{
	private ArrayList<ImageView> mButtons = null;
	private int crop;
    public CustomAdapter(ArrayList<ImageView> b,int cropSize) 
    {
        mButtons = b;
        crop=cropSize;
    }
	public int getCount() 
	{
		return mButtons.size();
	}
	public Object getItem(int position) 
	{
		return (Object) mButtons.get(position);
	}
	public long getItemId(int position) 
	{
		//in our case position and id are synonymous
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView imageView;
        if (convertView == null) 
        {
        	imageView = mButtons.get(position);
            imageView.setLayoutParams(new GridView.LayoutParams(crop,crop));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
        } 
        else 
        {
        	imageView = (ImageView) convertView;
        }
        return imageView;
	}

}
