package threemusketeers.butterknife;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    public SpacesItemDecoration() {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //BaseCard.CARD_TYPE viewType = (BaseCard.CARD_TYPE) view.getTag();
        ((GridLayoutManager.LayoutParams) view.getLayoutParams()).leftMargin = 0;
        ((GridLayoutManager.LayoutParams) view.getLayoutParams()).rightMargin = 0;
        ((GridLayoutManager.LayoutParams) view.getLayoutParams()).topMargin = 0;
        ((GridLayoutManager.LayoutParams) view.getLayoutParams()).bottomMargin = 0;

    }
}