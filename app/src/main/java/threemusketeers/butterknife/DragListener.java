package threemusketeers.butterknife;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;


class DragListener implements View.OnDragListener {


    private boolean isDropped = false;
    private Listener listener;

    static Bitmap p1, p2, p3, p4;


    DragListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {

            case DragEvent.ACTION_DRAG_LOCATION:
                Point touchPosition = getTouchPositionFromDragEvent(v, event);
                Log.d("Moving: ", "X = " + touchPosition.x + " Y = " + touchPosition.y);
                //WriteBtn(touchPosition.x, touchPosition.y);

                MainActivity.xyData.add(touchPosition.x + "," + touchPosition.y + " ");
                break;

            case DragEvent.ACTION_DROP:
                MainActivity.xyData.add(" ||| ");
                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) event.getLocalState();
                int viewId = v.getId();
                final int flItem = R.id.frame_layout_item;
                final int tvEmptyListTop = R.id.tvEmptyListTop;
                final int tvEmptyListBottom = R.id.tvEmptyListBottom;
                final int rvTop = R.id.rvTop;
                final int rvBottom = R.id.rvBottom;

                switch (viewId) {
                    case flItem:
                    case tvEmptyListTop:
                    case tvEmptyListBottom:
                    case rvTop:
                    case rvBottom:

                        RecyclerView target;
                        switch (viewId) {
                            case tvEmptyListTop:
                            case rvTop:
                                target = (RecyclerView) v.getRootView().findViewById(rvTop);
                                break;
                            case tvEmptyListBottom:
                            case rvBottom:
                                target = (RecyclerView) v.getRootView().findViewById(rvBottom);
                                break;
                            default:
                                target = (RecyclerView) v.getParent();
                                positionTarget = (int) v.getTag();
                        }

                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView) viewSource.getParent();

                            ListAdapter adapterSource = (ListAdapter) source.getAdapter();
                            int positionSource = (int) viewSource.getTag();
                            int sourceId = source.getId();

                            //String list = adapterSource.getList().get(positionSource);
                            //List<String> listSource = adapterSource.getList();

                            Bitmap list = adapterSource.getList().get(positionSource);
                            List<Bitmap> listSource = adapterSource.getList();

                            listSource.remove(positionSource);
                            adapterSource.updateList(listSource);
                            adapterSource.notifyDataSetChanged();

                            ListAdapter adapterTarget = (ListAdapter) target.getAdapter();
                            //List<String> customListTarget = adapterTarget.getList();

                            List<Bitmap> customListTarget = adapterTarget.getList();


                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, list);
                            } else {
                                customListTarget.add(list);
                            }

                            adapterTarget.updateList(customListTarget);
                            adapterTarget.notifyDataSetChanged();

                            if (sourceId == rvBottom && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListBottom(true);
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false);
                            }
                            if (sourceId == rvTop && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListTop(true);
                            }
                            if (viewId == tvEmptyListTop) {
                                listener.setEmptyListTop(false);
                            }

                            if ((sourceId == rvBottom || sourceId == rvTop) && adapterTarget.getItemCount() == 4) {
                                //Toast.makeText(MainActivity.ctx, "Image Comparison Started!!!", Toast.LENGTH_SHORT).show();

                                p1 = customListTarget.get(0);
                                p2 = customListTarget.get(1);
                                p3 = customListTarget.get(2);
                                p4 = customListTarget.get(3);

                                //MainActivity.test.setImageBitmap(customListTarget.get(0));

                            }

                        }
                        break;
                }
                break;
        }

        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }
        return true;
    }


    private static Point getTouchPositionFromDragEvent(View item, DragEvent event) {
        Rect rItem = new Rect();
        item.getGlobalVisibleRect(rItem);
        return new Point(rItem.left + Math.round(event.getX()), rItem.top + Math.round(event.getY()));
    }
}
