package edu.csumb.cst438.router;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

/**
 * Created by Pearce on 11/22/16.
 */

public class FriendsSwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<FriendsSwipeRecyclerViewAdapter.FriendsSimpleViewHolder> {
    private Context mContext;
    private String rowString;
    private String[] theList;
    private ArrayList<User> theArrayList;

    public FriendsSwipeRecyclerViewAdapter(Context context, String[] objects) {
        this.mContext = context;
        this.theList = objects;
    }

    public FriendsSwipeRecyclerViewAdapter(Context context, ArrayList<User> objects) {
        this.mContext = context;
        this.theArrayList = objects;
    }

    @Override
    public FriendsSimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_swiper, parent, false);
        return new FriendsSimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FriendsSimpleViewHolder viewHolder, final int position) {
        if(theList != null){
            rowString = theList[position];
        } else {
            rowString = theArrayList.get(position).username;
        }
        viewHolder.rowText.setText(rowString);

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.friendsBottom_view));

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                // functionality for when the buttons are hidden
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                // during any movement of the top view
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {
                // during the movement of the top view from closed state
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                // functionality for when the bottom view is completely visible
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                // during the movement of the top view from opened state
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                // called when mid swipe and user ends touch input
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " onClick : " + rowString , Toast.LENGTH_SHORT).show();
                //TODO: implement desired functionality when a row is clicked
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                theArrayList.remove(position);
                // TODO delete friend from the DB
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, theArrayList.size());
                mItemManger.closeAllItems();
            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        if(theList != null) {
            return theList.length;
        } else {
            return theArrayList.size();
        }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.friendsSwiper;
    }

    public static class FriendsSimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        Button deleteButton;
        TextView rowText;

        public FriendsSimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.friendsSwiper);
            deleteButton = (Button) itemView.findViewById(R.id.friendsDelete);
            rowText = (TextView) itemView.findViewById(R.id.friendsSwiperRow);
        }
    }
}
