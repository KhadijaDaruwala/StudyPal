package layout.samplestudy.Syllabus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.pal.study.samplestudy.R;

public class SyllabusAdapter extends RecyclerView.Adapter<SyllabusAdapter.ItemHolder> {
    private Context mContext;

    public SyllabusAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_syllabus, parent, false);
        return new ItemHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
