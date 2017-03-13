package com.fachati.hp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public  class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean loading = true;

    private LinearLayoutManager linearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            loading=true;

            if (loading) {
                if (dy > 0){
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Application.get().bus().send(new Events.ScrollDirection(0));
                    }

                }else{
                    Application.get().bus().send(new Events.ScrollDirection(1));
                }
        }
    }

}