package com.islamassem.utils.recyclerview;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * EndlessRecyclerViewScroll will fetch new data when we reach the bottom by scrolling down the RecyclerView (Infinite_Scrolling)
 **/

public abstract class EndlessRecyclerViewScroll extends RecyclerView.OnScrollListener {

    private View bottomBar = null;

    public boolean loading = true;             // True if we are still waiting for the last set of data to load
    public boolean loadedAllPages = false;             // True if we loaded all pages and nothing needed to be loaded
    private int current_page = 0;               // The current offset index of data you have loaded
    private int total_page = 0;               // The total number (max) of pages to be loaded
    private int previousTotal = 0;              // The total number of items in the RecyclerView after the last load
    private int orientation;

    public EndlessRecyclerViewScroll() {
        this(RecyclerView.VERTICAL);
    }

    public EndlessRecyclerViewScroll(int orientation) {
        this.orientation = orientation;
    }

    public EndlessRecyclerViewScroll(int orientation, View bottomBar) {
        this.bottomBar = bottomBar;
        this.orientation = orientation;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public void onScrollHorizontal(RecyclerView recyclerView, int dx, int dy) {
        //visibleItemCount = recyclerView.getChildCount();
        // The total number of items in the RecyclerView
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        // The minimum amount of items to have below your current scroll position before loading more
        int visibleThreshold = 1;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager)
            visibleThreshold = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
        // The current position of first visible item in the total visible items
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


        // If it's still loading, and the RecyclerView count has changed
        if (loading && (totalItemCount > previousTotal)) {
            previousTotal = totalItemCount;
            loading = false;
        }


        if (totalItemCount > 0 && !loading && lastVisibleItem >= totalItemCount - visibleThreshold) {
            // End has been reached

//            if(lastVisibleItem<15)
//                return;

            recyclerView.scrollToPosition(lastVisibleItem);
            if (!loadedAllPages) {
                onLoadMore(current_page);
                loading = true;
                current_page++;
            }
        }


    }

    public void onScrollVertica(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {

            //visibleItemCount = recyclerView.getChildCount();
            // The total number of items in the RecyclerView
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            // The minimum amount of items to have below your current scroll position before loading more
            int visibleThreshold = 1;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager)
                visibleThreshold = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
            // The current position of first visible item in the total visible items
            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();


            // If it's still loading, and the RecyclerView count has changed
            if (loading && (totalItemCount > previousTotal)) {
                previousTotal = totalItemCount;
                loading = false;
            }
            if (totalItemCount > 0 && !loading && lastVisibleItem >= totalItemCount - visibleThreshold) {
                // End has been reached

                loading = true;
                current_page++;

                recyclerView.scrollToPosition(lastVisibleItem);

                if (!loadedAllPages)
                    onLoadMore(current_page);
            }

            if (bottomBar != null) {
                // Animate the loading view to 0% opacity. After the animation ends,
                // set its visibility to GONE as an optimization step (it won't participate in layout passes, etc.)
                bottomBar.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                bottomBar.setVisibility(View.GONE);
                            }
                        });
            }


        } else if (dy < 0) {

            if (bottomBar != null) {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                bottomBar.setAlpha(0f);
                bottomBar.setVisibility(View.VISIBLE);

                // Animate the content view to 100% opacity, and clear any animation listener set on the view.
                bottomBar.animate()
                        .alpha(1f)
                        .setDuration(200)
                        .setListener(null);
            }

        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (orientation == RecyclerView.HORIZONTAL)
            onScrollHorizontal(recyclerView, dx, dy);
        else if (orientation == RecyclerView.VERTICAL)
            onScrollVertica(recyclerView, dx, dy);


    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
        loadedAllPages = total_page != 0 && total_page == current_page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
        loadedAllPages = total_page != 0 && total_page == current_page;
    }

    public abstract void onLoadMore(int current_page);

    public void onLoadMore() {
        onLoadMore(current_page);
    }

    public void reset() {
        loading = false;
        current_page = 0;
        previousTotal = 0;
        loadedAllPages = false;
    }
}

