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
import android.widget.ImageView;
import android.widget.TableRow;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Interface.OnEventTypeSelect;
import com.nectarbits.baraati.Models.EventType.EventList;
import com.nectarbits.baraati.Models.EventType.EventType;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 20/8/16.
 */
public class EventTypeAdapter extends ExpandableRecyclerAdapter<EventTypeAdapter.ParentViewHolder_P,EventTypeAdapter.ChildViewHolder_C> {

    private Context mContext;
    private List<EventList> mArrayList;
    private OnEventTypeSelect onEventTypeSelect;
    private LayoutInflater mInflator;
    public static String endofcat="";
    public EventTypeAdapter(Context context, @NonNull List<? extends ParentListItem> arrayList, OnEventTypeSelect onEventTypeSelect){
        super(arrayList);
        mContext = context;
        mArrayList =(List)arrayList;
        this.onEventTypeSelect = onEventTypeSelect;
        mInflator = LayoutInflater.from(context);
        endofcat="";
    }



    @Override
    public ParentViewHolder_P onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View recipeView = mInflator.inflate(R.layout.layout_event_type_item_title, parentViewGroup, false);
        return new ParentViewHolder_P(recipeView);
    }

    @Override
    public ChildViewHolder_C onCreateChildViewHolder(ViewGroup childViewGroup) {
        View ingredientView = mInflator.inflate(R.layout.layout_event_type_item_child, childViewGroup, false);
        return new ChildViewHolder_C(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(ParentViewHolder_P parentViewHolder, int position, ParentListItem parentListItem) {
        EventList recipe = (EventList) parentListItem;
        parentViewHolder.bind(recipe,position);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder_C childViewHolderC, int position, Object childListItem) {
        EventType recipe = (EventType) childListItem;
        if(recipe.getSelect()) {
            childViewHolderC.bind(recipe);
        }

    }



    public class ParentViewHolder_P extends com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder {

        private TextViewTitle textViewMainCategory;
        private TextViewDescription textViewDescription_subcategory,txtdesc;
        private TableRow tblRow;
        private ImageView btnRighArrow;
        private CircularImageView img;
        private View viewDivider;
        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 90f;

        public ParentViewHolder_P(View itemView) {
            super(itemView);

            textViewMainCategory = (TextViewTitle)itemView.findViewById(R.id.textViewMainCategory);
            tblRow = (TableRow)itemView.findViewById(R.id.tblRow);
            viewDivider = (View) itemView.findViewById(R.id.vDivider);
            btnRighArrow = (ImageView)itemView.findViewById(R.id.btnRighArrow);
            textViewDescription_subcategory = (TextViewDescription) itemView.findViewById(R.id.txtSucategory);
            txtdesc = (TextViewDescription) itemView.findViewById(R.id.txtDesc);
            img = (CircularImageView) itemView.findViewById(R.id.img);


            tblRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "onClick: "+isExpanded());
                    EventList position=(EventList)v.getTag();
                    onEventTypeSelect.OnEventSelectParent(position);
                   /* if (isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                    }*/
                }
            });
            btnRighArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                    }
                }
            });


        }

        public void bind(EventList ingredient,int position) {
            tblRow.setTag(ingredient);

            textViewMainCategory.setText(ingredient.getEvent());
            textViewDescription_subcategory.setText(ingredient.getSubcategory());
            txtdesc.setText(ingredient.getEventlabel());

            if(!TextUtils.isEmpty(ingredient.getEventicon())){
                Picasso.with(mContext)
                        .load(ingredient.getEventicon())
                        .into(img);
            }
           /* if(endofcat.length()<=0){
                endofcat=""+ingredient.getEventId();
            }

            Log.e(EventTypeAdapter.class.getSimpleName(), "bind: "+endofcat+" "+ingredient.getEventId());
            if(!endofcat.equalsIgnoreCase(ingredient.getEventId())){
                viewDivider.setVisibility(View.VISIBLE);
            }else{
                viewDivider.setVisibility(View.GONE);
            }*/

            for (int i = 0; i < mArrayList.size(); i++) {
                    if(i!=0) {
                        if (mArrayList.get(i).equals(ingredient)) {
                            if (!mArrayList.get(i-1).getSubcategory().equalsIgnoreCase(
                                    ingredient.getSubcategory())) {
                                viewDivider.setVisibility(View.VISIBLE);
                            } else {
                                viewDivider.setVisibility(View.GONE);
                            }
                        }
                    }
            }

            Log.e(EventTypeAdapter.class.getSimpleName(), "Position: "+mArrayList.size() );

            if(position!=0) {

                /*Log.e(EventTypeAdapter.class.getSimpleName(), "bind:"+mArrayList.get(position).getSubcategory()+" "+mArrayList.get(position-1).getSubcategory());
                if (!mArrayList.get(position).getSubcategory().equalsIgnoreCase(
                        mArrayList.get((position - 1)).getSubcategory())) {
                    viewDivider.setVisibility(View.VISIBLE);
                } else {
                    viewDivider.setVisibility(View.GONE);
                }*/
            }else{
                viewDivider.setVisibility(View.GONE);
            }

           /* if(endofcat.length()<=0){
                endofcat=""+ingredient.getEventId();
            }else{*/
              //  endofcat=""+ingredient.getEventId();
           /* }*/
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

        private TextViewTitle textViewMainCategory;
        private TableRow tblRow;
        private CheckBox checkBox;
        private CircularImageView img;
        public ChildViewHolder_C(View itemView) {
            super(itemView);

            textViewMainCategory = (TextViewTitle)itemView.findViewById(R.id.checkboxTextview);
            tblRow = (TableRow)itemView.findViewById(R.id.tblRow);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            img = (CircularImageView) itemView.findViewById(R.id.img);
           /* textViewMainCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventType eventType=(EventType)v.getTag();

                    for (int i = 0; i < mArrayList.size(); i++) {
                        List<EventType> eventLists=mArrayList.get(i).getEventTypes();
                        for (int j = 0; j < eventLists.size(); j++) {
                            if(eventType.equals(eventLists.get(j))){
                                if(eventType.getSelect()){
                                    eventType.setSelect(false);
                                }else{
                                    eventType.setSelect(true);
                                }

                                mArrayList.get(i).getEventTypes().set(j,eventType);
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
            });*/
/*
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    EventType eventType=(EventType)buttonView.getTag();
                    for (int i = 0; i < mArrayList.size(); i++) {
                        List<EventType> eventLists=mArrayList.get(i).getEventTypes();
                        for (int j = 0; j < eventLists.size(); j++) {
                            if(eventLists.get(j)!=null) {
                                if (eventType.equals(eventLists.get(j))) {
                                    if (eventType.getSelect()) {
                                        eventType.setSelect(false);
                                    } else {
                                        eventType.setSelect(true);
                                    }

                                    mArrayList.get(i).getEventTypes().set(j, eventType);
                                    break;
                                }
                            }
                        }
                    }
                   *//* if(((CheckedTextView) v).isSelected()){
                        ((CheckedTextView) v).setSelected(false);*//*
                    onEventTypeSelect.OnEventSelect(eventType,isChecked);
*//*
                    }else{
                        ((CheckedTextView) v).setSelected(true);
                        onEventTypeSelect.OnEventSelect(eventType,true);

                    }*//*
                }
            });*/

        }

        public void bind(EventType ingredient) {

            /*if(ingredient.getSelect()){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }*/
            if(!TextUtils.isEmpty(ingredient.getEventtypeicon())){
                Picasso.with(mContext)
                        .load(ingredient.getEventtypeicon())
                        .into(img);
            }
            textViewMainCategory.setText(ingredient.getEventType());

            //checkBox.setTag(ingredient);
        }
    }
}
