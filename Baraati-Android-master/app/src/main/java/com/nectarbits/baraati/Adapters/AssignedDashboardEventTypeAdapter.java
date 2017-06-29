package com.nectarbits.baraati.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Interface.OnUserCheckListSelect;
import com.nectarbits.baraati.Models.UserEvent.Event;
import com.nectarbits.baraati.Models.UserEvent.EventType;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 20/8/16.
 */
public class AssignedDashboardEventTypeAdapter extends ExpandableRecyclerAdapter<AssignedDashboardEventTypeAdapter.ParentViewHolder_P,AssignedDashboardEventTypeAdapter.ChildViewHolder_C> {

    private Context mContext;
    private List<Event> mArrayList;
    private OnUserCheckListSelect onUserCheckListSelect;
    private LayoutInflater mInflator;
    public AssignedDashboardEventTypeAdapter(Context context, @NonNull List<? extends ParentListItem> arrayList, OnUserCheckListSelect onUserCheckListSelect){
        super(arrayList);
        mContext = context;
        mArrayList =(List)arrayList;
        this.onUserCheckListSelect = onUserCheckListSelect;
        mInflator = LayoutInflater.from(context);
    }



    @Override
    public ParentViewHolder_P onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View recipeView = mInflator.inflate(R.layout.layout_dash_event_type_item_title, parentViewGroup, false);
        return new ParentViewHolder_P(recipeView);
    }

    @Override
    public ChildViewHolder_C onCreateChildViewHolder(ViewGroup childViewGroup) {
        View ingredientView = mInflator.inflate(R.layout.layout_dash_event_type_item_child, childViewGroup, false);
        return new ChildViewHolder_C(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(ParentViewHolder_P parentViewHolder, int position, ParentListItem parentListItem) {
        Event recipe = (Event) parentListItem;
        parentViewHolder.bind(recipe);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder_C childViewHolderC, int position, Object childListItem) {
        EventType recipe = (EventType) childListItem;

        childViewHolderC.bind(recipe);
    }



    public class ParentViewHolder_P extends com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder {

        private TextViewTitle textViewMainCategory;

        private TableRow tblRow;
        private ImageView btnRighArrow;
        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 90f;
        private ImageView btnPlus;
        private ImageView btnDelete;
        private CircularImageView img;

        public ParentViewHolder_P(View itemView) {
            super(itemView);

            textViewMainCategory = (TextViewTitle)itemView.findViewById(R.id.textViewMainCategory);

            tblRow = (TableRow)itemView.findViewById(R.id.tblRow);
            btnRighArrow = (ImageView)itemView.findViewById(R.id.btnRighArrow);
            btnPlus=(ImageView) itemView.findViewById(R.id.btnPlus);
            btnDelete=(ImageView) itemView.findViewById(R.id.btnDelete);
            img=(CircularImageView) itemView.findViewById(R.id.img);
            btnPlus.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);

            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Event event=(Event)v.getTag();
                    onUserCheckListSelect.OnUserEventAddSelect(event);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Event event=(Event)v.getTag();
                    onUserCheckListSelect.OnUserEventDeleteSelect(event);
                }
            });

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

        public void bind(Event event) {

            btnDelete.setTag(event);
            btnPlus.setTag(event);
            textViewMainCategory.setText(event.getEvent());
            if(!TextUtils.isEmpty(event.getEventicon())){
                Picasso.with(mContext)
                        .load(event.getEventicon())
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

        private TextViewTitle txtEventTitle;
        private TableRow tblRow;
        private ImageView btnCart,btnCalender,btnDelete;
        private CardView cvEventType;
        private CircularImageView img;
        public ChildViewHolder_C(View itemView) {
            super(itemView);

            btnCart = (ImageView)itemView.findViewById(R.id.btnCart);
            btnCalender = (ImageView)itemView.findViewById(R.id.btnCalender);
            btnDelete = (ImageView)itemView.findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.GONE);
            cvEventType=(CardView)itemView.findViewById(R.id.cvEventtype);
            txtEventTitle = (TextViewTitle)itemView.findViewById(R.id.txtEventTitle);
            tblRow = (TableRow)itemView.findViewById(R.id.tblRow);
            img = (CircularImageView) itemView.findViewById(R.id.img);
            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // mContext.startActivity(new Intent(mContext, VendorListActivity.class));
                    EventType eventType=(EventType) v.getTag();
                    onUserCheckListSelect.OnUserEventTypeCartSelect(eventType);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // mContext.startActivity(new Intent(mContext, VendorListActivity.class));
                    EventType eventType=(EventType) v.getTag();
                    onUserCheckListSelect.OnUserEventTypeDeleteSelect(eventType);
                }
            });

            btnCalender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // mContext.startActivity(new Intent(mContext, VendorListActivity.class));
                    EventType eventType=(EventType) v.getTag();
                    onUserCheckListSelect.OnUserEventTypeCalenderSelect(eventType);
                }
            });

            tblRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    EventType eventType=(EventType) view.getTag();
                    if(eventType.getIsDone().equalsIgnoreCase("0")){
                        onUserCheckListSelect.onCompleteEvent(false,eventType);
                    }else{
                        onUserCheckListSelect.onCompleteEvent(true,eventType);
                    }
                    return false;
                }
            });



            /*textViewMainCategory.setOnClickListener(new View.OnClickListener() {
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
                        //onUserCheckListSelect.OnEventSelect(eventType,false);

                    }else{
                        ((CheckedTextView) v).setSelected(true);
                        //onUserCheckListSelect.OnEventSelect(eventType,true);

                    }
                }
            });*/

        }

        public void bind(EventType eventType) {

       /*     if(eventType.getSelect()){
                txtEventTitle.setSelected(true);
            }else{
                txtEventTitle.setSelected(false);
            }*/
                btnCart.setTag(eventType);
                btnCalender.setTag(eventType);
                btnDelete.setTag(eventType);

                tblRow.setTag(eventType);
                txtEventTitle.setText("" + eventType.getEventType());
                txtEventTitle.setTag(eventType);

            if(eventType.getIsDone().equalsIgnoreCase("1")){
                txtEventTitle.setPaintFlags(txtEventTitle.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                txtEventTitle.setPaintFlags(txtEventTitle.getPaintFlags());
            }

            if(!eventType.getStartDate().toString().equalsIgnoreCase("0000-00-00") && !eventType.getEndDate().toString().equalsIgnoreCase("0000-00-00"))
            {
                btnCalender.setBackgroundResource(R.drawable.selector_calender_red_gray);
            }else{
                btnCalender.setBackgroundResource(R.drawable.selector_calender_gray_red);
            }
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Log.e("TAG", "isLast: "+eventType.getLast());
            if(!eventType.getLast()) {
                layoutParams.setMargins((int) mContext.getResources().getDimension(R.dimen._7sdp), 0, (int) mContext.getResources().getDimension(R.dimen._7sdp), (int) mContext.getResources().getDimension(R.dimen.dim_05));
                cvEventType.setLayoutParams(layoutParams);
            }else {
                layoutParams.setMargins((int) mContext.getResources().getDimension(R.dimen._7sdp), 0, (int) mContext.getResources().getDimension(R.dimen._7sdp), (int) mContext.getResources().getDimension(R.dimen._4sdp));
                cvEventType.setLayoutParams(layoutParams);
            }
            if(!TextUtils.isEmpty(eventType.getEventtypeicon())){
                Picasso.with(mContext)
                        .load(eventType.getEventtypeicon())
                        .into(img);
            }

        }
    }
}
