package com.example.pat.aapkatrade.general;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by abcd on 10/12/2016.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int childcount;

    public SpacesItemDecoration(int space, int childcount) {
        this.space = space;
        this.childcount=childcount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {



if(childcount==2)
{  outRect.bottom = space;
    outRect.top=space;
    if (parent.getChildLayoutPosition(view)%2 == 0) {
    outRect.right = space;
    outRect.left=0;
} else {
    outRect.right = 0;
    outRect.left=space;
}


}
        else{
    outRect.bottom = space;
    outRect.right=space;
    outRect.top=space;
    outRect.left=space;

}
        // Add top margin only for the first item to avoid double space between items

    }
}