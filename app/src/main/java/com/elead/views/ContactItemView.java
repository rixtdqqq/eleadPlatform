package com.elead.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;


public class ContactItemView extends LinearLayout{

    private TextView unreadMsgView;
    private Drawable arrowImg;
    private ImageView arrowIV;

    public ContactItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ContactItemView(Context context) {
        super(context);
        init(context, null);
        setClickable(true);
        setBackgroundResource(R.drawable.white_bg_selector);
    }
    
    private void init(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ContactItemView);
        String name = ta.getString(R.styleable.ContactItemView_contactItemName);
        String signName = ta.getString(R.styleable.ContactItemView_contactItemSignatureName);
        Drawable image = ta.getDrawable(R.styleable.ContactItemView_contactItemImage);
        ta.recycle();
        
        LayoutInflater.from(context).inflate(R.layout.contact_contact_item, this);
        CircularImageView avatar = (CircularImageView) findViewById(R.id.avatar);
        TextView nameView = (TextView) findViewById(R.id.name);
        arrowIV=(ImageView)findViewById(R.id.right_id);
        if (signName!=null){
            TextView signature = (TextView) findViewById(R.id.signature);
            signature.setVisibility(View.VISIBLE);
            signature.setText(signName);
        }
        if(image != null){
            avatar.setImageDrawable(image);
        }
        nameView.setText(name);
        arrowIV.setImageDrawable(getResources().getDrawable(R.drawable.right_arrow));
    }
    
    public void setUnreadCount(int unreadCount){
        unreadMsgView.setText(String.valueOf(unreadCount));
    }
    
    public void showUnreadMsgView(){
        unreadMsgView.setVisibility(View.VISIBLE);
    }
    public void hideUnreadMsgView(){
        unreadMsgView.setVisibility(View.INVISIBLE);
    }


    public void setArrow(Drawable arrowImg ){
       // this.arrowImg=arrowImg;
        Log.i("TAggg2","yyyy=="+arrowImg);
        if(arrowImg!=null){
            arrowIV.setImageDrawable(arrowImg);
        }
    }


}
