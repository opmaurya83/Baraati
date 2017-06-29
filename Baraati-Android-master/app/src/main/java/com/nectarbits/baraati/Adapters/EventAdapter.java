package com.nectarbits.baraati.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Interface.OnEventSelect;
import com.nectarbits.baraati.Models.Event.DetailResult;

import com.nectarbits.baraati.Models.Event.EventList;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 20/8/16.
 */
public class EventAdapter extends ExpandableRecyclerAdapter<EventAdapter.ParentViewHolder_P,EventAdapter.ChildViewHolder_C> {

    private static final String TAG = EventAdapter.class.getSimpleName();
    private Context mContext;
    private List<DetailResult> mArrayList;
    private OnEventSelect onEventTypeSelect;
    private LayoutInflater mInflator;
    public EventAdapter(Context context, @NonNull List<? extends ParentListItem> arrayList, OnEventSelect onEventTypeSelect){
        super(arrayList);
        mContext = context;
        mArrayList =(List)arrayList;
        this.onEventTypeSelect = onEventTypeSelect;
        mInflator = LayoutInflater.from(context);
    }



    @Override
    public ParentViewHolder_P onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View recipeView = mInflator.inflate(R.layout.layout_event_item_title, parentViewGroup, false);
        return new ParentViewHolder_P(recipeView);
    }

    @Override
    public ChildViewHolder_C onCreateChildViewHolder(ViewGroup childViewGroup) {
        View ingredientView = mInflator.inflate(R.layout.layout_event_item_child, childViewGroup, false);
        return new ChildViewHolder_C(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(ParentViewHolder_P parentViewHolder, int position, ParentListItem parentListItem) {
        DetailResult recipe = (DetailResult) parentListItem;
        parentViewHolder.bind(recipe);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder_C childViewHolderC, int position, Object childListItem) {
        EventList recipe = (EventList) childListItem;
        childViewHolderC.bind(recipe);
    }



    public class ParentViewHolder_P extends com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder {

        private TextViewTitle textViewMainCategory;
        private ImageView btnRighArrow;
        private TableRow tblRow;
        private TextViewDescription txtDesc;
        private CircularImageView img;
        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 90f;

        public ParentViewHolder_P(View itemView) {
            super(itemView);

            textViewMainCategory = (TextViewTitle)itemView.findViewById(R.id.textViewMainCategory);
            txtDesc = (TextViewDescription)itemView.findViewById(R.id.txtDesc);
            img = (CircularImageView) itemView.findViewById(R.id.img);
            tblRow = (TableRow)itemView.findViewById(R.id.tblRow);
            btnRighArrow = (ImageView)itemView.findViewById(R.id.btnRighArrow);
            img = (CircularImageView)itemView.findViewById(R.id.img);
            tblRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "onClick: "+isExpanded());
                    if (isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                    }
                }
            });
        }

        public void bind(DetailResult ingredient) {
            textViewMainCategory.setText(ingredient.getSubcategory());
            txtDesc.setText(ingredient.getSubcategorylabel());
            if(!TextUtils.isEmpty(ingredient.getSubcategoryicon())){
                Picasso.with(mContext)
                        .load(ingredient.getSubcategoryicon())
                        .into(img);
            }
        }


        @Override
        public boolean shouldItemViewClickToggleExpansion() {
            return true;
        }


        @SuppressLint("NewApi")
        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (expanded) {
                    btnRighArrow.setRotation(ROTATED_POSITION);
                } else {
                    btnRighArrow.setRotation(INITIAL_POSITION);
                }
            }
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                RotateAnimation rotateAnimation;
                if (expanded) { // rotate clockwise
                    rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                } else { // rotate counterclockwise
                    rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                }

                rotateAnimation.setDuration(200);
                rotateAnimation.setFillAfter(true);
                btnRighArrow.startAnimation(rotateAnimation);
            }
        }
    }

    public class ChildViewHolder_C extends com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder {

        private TextView textViewMainCategory;
        private TableRow tblRow;
        private CheckBox checkBox;
        private TextViewDescription txtDesc;
        private CircularImageView img;
        public ChildViewHolder_C(View itemView) {
            super(itemView);

            textViewMainCategory = (TextViewTitle)itemView.findViewById(R.id.checkboxTextview);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            tblRow = (TableRow)itemView.findViewById(R.id.tblRow);
            txtDesc = (TextViewDescription)itemView.findViewById(R.id.txtDesc);
            img = (CircularImageView)itemView.findViewById(R.id.img);
           /* textViewMainCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventList eventType=(EventList)v.getTag(R.id.tag1);
                    for (int i = 0; i < mArrayList.size(); i++) {
                        List<EventList> eventLists=mArrayList.get(i).getEventList();
                        for (int j = 0; j < eventLists.size(); j++) {
                            if(eventType.equals(eventLists.get(j))){
                                if(eventType.getSelected()){
                                    eventType.setSelected(false);
                                }else{
                                    eventType.setSelected(true);
                                }

                                mArrayList.get(i).getEventList().set(j,eventType);
                                break;
                            }
                        }
                    }
                    if(((CheckedTextView) v).isSelected()){
                        ((CheckedTextView) v).setSelected(false);
                        onEventTypeSelect.OnEventSelect(eventType,false);

                    }else{
                        ((CheckedTextView) v).setSelected(true);
                        onEventTypeSelect.OnEventSelect(eventType,true);

                    }
                }
            });
*/
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    EventList eventType=(EventList)buttonView.getTag(R.id.tag1);
                    for (int i = 0; i < mArrayList.size(); i++) {
                        List<EventList> eventLists=mArrayList.get(i).getEventList();
                        for (int j = 0; j < eventLists.size(); j++) {
                            if(eventType!=null) {
                                if (eventLists.get(j) != null) {
                                    /*if (eventType.getEventId().equalsIgnoreCase(eventLists.get(j).getEventId())) {*/
                                    if (eventType.equals(eventLists.get(j))) {
                                       /* if (eventType.getSelected()) {
                                            eventType.setSelected(false);
                                        } else {

                                        }*/
                                      //  eventType.setSelected(true);
                                        Log.e(TAG, "onCheckedChanged: "+eventType.getEventId()+" "+isChecked );
                                        mArrayList.get(i).getEventList().get(j).setSelected(isChecked);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                   /* if(((CheckedTextView) v).isSelected()){
                        ((CheckedTextView) v).setSelected(false);*/
                        onEventTypeSelect.OnEventSelect(eventType,isChecked);
/*
                    }else{
                        ((CheckedTextView) v).setSelected(true);
                        onEventTypeSelect.OnEventSelect(eventType,true);

                    }*/
                }
            });

        }

        public void bind(EventList ingredient) {

            Log.e("TAG", "bind: "+ingredient.getSelected()+" id"+ingredient.getEventId());
            checkBox.setTag(R.id.tag1,ingredient);
            if(ingredient.getSelected())
            {
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }
            if(!TextUtils.isEmpty(ingredient.getEventicon())){
                Picasso.with(mContext)
                        .load(ingredient.getEventicon())
                        .into(img);
            }
            txtDesc.setText(ingredient.getEventlabel());
            textViewMainCategory.setText(ingredient.getEvent());
            textViewMainCategory.setTag(R.id.tag1,ingredient);

        }
    }
}
