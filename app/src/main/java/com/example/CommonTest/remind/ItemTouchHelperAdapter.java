package com.example.CommonTest.remind;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);



    void removeItem(int position);
}
